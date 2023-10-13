const app = getApp()
import {
  get
} from '../../utils/req';
Page({
  data: {
    // 搜索框内容
    inputValue: '',
    // 收货地址
    showAddress: false,
    address: "",
    addressList: [{
      name: '地址1',
    }, {
      name: '地址2',
    }, {
      name: '地址3',
    }, ],
    // 商品
    showGoods: false,
    good: "",
    goodsList: [{
      name: ''
    }, {
      name: '',
    }, {
      name: '',
    }, ],
    // 备注
    remark: "",
    price: 15,
    count: 0,
    number: 1,
    // 日历
    showCalendar: false,
    date: '', //选择的日期
    active: 0, //顶部tag
    goodList: []
  },

  onLoad() {
    this.getLocation()
    setTimeout(() => {
      this.getData()
      this.getAddress()
    }, 1000)
  },
  onShow() {
    this.getAddress()
  },

  // 获取商品数据
  getData() {
    var location = {
      jingdu: this.data.jingdu,
      weidu: this.data.weidu,
    }
    const jingdu = encodeURIComponent(location.jingdu);
    const weidu = encodeURIComponent(location.weidu);
    const params = `jingdu=${jingdu}&weidu=${weidu}`;
    // 发起 GET 请求
    get('/station/suitable?' + params).then((res) => {
      console.log("获取所有商品成功~！", res);
      if (res.code == 200) {
        const arrays = res.data; // 假设接口返回的对象
        let sidList = [];
        const values = Object.values(arrays);
        for (let i = 0; i < values.length; i++) {
          if (values[i].hasOwnProperty('station_id')) {
            sidList.push(values[i].station_id);
          }
        }
        const uniqueSidList = [...new Set(sidList)];
        // console.log("----stationid", uniqueSidList);

        var goodsList = res.data.map(function (gooditem) {
          // 从每个对象中截取所需的字段，并返回一个新的对象
          return {
            name: gooditem.goodname,
            price: gooditem.newprice,
            size: gooditem.size,
            goodid: gooditem.goodid,
          };
        });
        this.setData({
          goodList: res.data,
          goodsList: goodsList.slice(0, 3),
          goodSel: res.data[0],
          selGood: goodsList[0],
          count: goodsList[0].price,
          price: goodsList[0].price,
          sidList: uniqueSidList
        })
      } else {
        // 这里处理返回 code 是其它值的
        // console.log(res);
      }
    }).catch((err) => {
      console.error("获取所有商品失败~！", err);
      // 处理错误
    });
    this.setData({
      address: this.data.addressList[0].name,
      good: this.data.goodsList[0].name,
      count: this.data.price * this.data.number,
      date: `${this.formatDate()}`,
    })
  },
  // 获取当前用户的收货地址
  getAddress() {
    var userinfo = wx.getStorageSync('userinfo')
    var params = {
      id: userinfo.userid,
    }
    var addressList = []
    get('/address/', params).then((res) => {
      // console.log("获取收货地址成功~！", res);
      if (res.code == 200) {
        // 假设 userinfo.userid 存储在变量 userinfo 中
        var userinfo = wx.getStorageSync('userinfo');
        var filteredArray = res.data.filter(function (item) {
          return item.address_userid == userinfo.userid;
        });
        // 对 filteredArray 进行进一步处理，比如更新页面的数据或执行其他操作
        // console.log("满足条件的地址项数组: ", filteredArray);
        for (let i = 0; i < filteredArray.length; i++) {
          if (filteredArray[i].isdefault == 1) {
            this.setData({
              address: filteredArray[i].detailaddress
            })
          }
          filteredArray[i].name = filteredArray[i].detailaddress
        }
        this.setData({
          addressList: filteredArray,
          addressSel: filteredArray[0]
        })
        /*   var addArr = res.data  
        for (let i = 0; i < addArr.length; i++) {
           var item = addArr[i];
           if (userid == addArr[i].address_userid) {
             addressList.push(item)
             console.log(item);
           }
         }
         console.log("addressList---", addressList); */
      } else if (res.code == -1) {
        wx.showToast({
          title: res.msg,
          icon: "none",
          duration: 1000,
          complete: () => {
            wx.removeStorageSync('userinfo')
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }
        })
      }
    }).catch((error) => {
      console.error("index-获取收货地址错误", error);
    });
  },

  // 获取当前位置
  getLocation() {
    var that = this
    // 在需要进行定位的页面或组件中调用wx.getLocation接口
    wx.getLocation({
      type: 'wgs84', // 返回经纬度信息的坐标系类型，这里使用WGS84坐标系
      success: function (res) {
        var latitude = res.latitude; // 纬度
        var longitude = res.longitude; // 经度
        that.setData({
          weidu: latitude,
          jingdu: longitude,
        })
      },
      fail: function (res) {
        // 获取位置失败的处理逻辑
      }
    });
  },


  // 顶部的tag切换
  tagChange(event) {},
  // 搜索框内容发生变化
  inputChange(e) {
    this.setData({
      inputValue: e.detail,
    });
  },
  onSearch() { //回车触发搜索
    console.log(this.data.inputValue);
  },
  // 点击搜索
  search() {
    // console.log(this.data.inputValue);
    var params = {
      name: this.data.inputValue,
      pageNum: 1,
      pageSize: 20,
      phone: "",
    }
    var userinfo = wx.getStorageSync('userinfo')
    get('/good/search', params).then((res) => {
      console.log("搜索商品成功~！", res);
      if (res.code == 200) {
        var goodsList = res.data.list.map(function (gooditem) {
          // 从每个对象中截取所需的字段，并返回一个新的对象
          return {
            name: gooditem.goodname,
            price: gooditem.newprice,
            size: gooditem.size,
          };
        });
        var sidList = this.data.sidList
        let selectedArrays = goodsList.filter(item => sidList.includes(item.station_id));
        this.setData({
          goodList: selectedArrays,
          goodsList: goodsList.slice(0, 3),
          selGood: goodsList[0],
          count: goodsList[0].price,
          price: goodsList[0].price,
        })
      } else if (res.code == -1) {
        wx.showToast({
          title: res.msg,
          icon: "none",
          duration: 1000,
          complete: () => {
            wx.removeStorageSync('userinfo')
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }
        })
      }
    }).catch((error) => {
      console.error(error);
    });
  },
  // 点击商品卡片
  clickGood(e) {
    var good = e.currentTarget.dataset.good;
    var goodJson = JSON.stringify(good);
    var goodid = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '/pages/gooddetail/gooddetail?good=' + encodeURIComponent(goodJson),
    })
  },

  // 选择日期
  showCalendar() {
    this.setData({
      showCalendar: true
    })
  },
  closeCalendar() {
    this.setData({
      showCalendar: false
    })
  },
  formatDate(date) {
    date = date ? new Date(date) : new Date(); // 如果没有传入日期，则默认为当前日期
    return `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日`;
  },
  onConfirm(event) {
    const date = event.detail;
    this.setData({
      showCalendar: false,
      date: `${this.formatDate(date)}`,
    });
  },

  // 选择地址 新增地址
  clickAddress() {
    this.setData({
      showAddress: true
    })
  },
  addressClose() {
    this.setData({
      showAddress: false
    });
  },
  addressSelect(event) {
    this.setData({
      address: event.detail.name,
      addressSel: event.detail
    })
  },
  addAddress() {
    wx.navigateTo({
      url: '/pages/addaddress/addaddress',
    })
  },

  // 选择商品
  clickGoods() {
    this.setData({
      showGoods: true
    })
  },
  goodsClose() {
    this.setData({
      showGoods: false
    });
  },
  goodsSelect(event) {
    var count = event.detail.price * this.data.number
    var goodList = this.data.goodList
    var selectedGoodId = event.detail.goodid;
    var selectedGood = goodList.find(function (good) {
      return good.goodid === selectedGoodId;
    });

    this.setData({
      goodSel: selectedGood,
      selGood: event.detail,
      count,
      price: event.detail.price,
      good: event.detail.name,
    })
  },
  // 商品数量增加
  countChange(event) {
    this.setData({
      number: event.detail,
      count: this.data.price * event.detail
    })
  },

  // 输入备注
  remarkInput(event) {
    this.setData({
      remark: event.detail
    })
  },
  // 支付
  pay() {
    var address = this.data.addressSel
    var good = this.data.goodSel
    var count = this.data.number
    good.count = count;
    var remark = this.data.remark
    wx.navigateTo({
      url: '/pages/pay/pay?address=' + encodeURIComponent(JSON.stringify(address)) +
        '&good=' + encodeURIComponent(JSON.stringify(good)) +
        '&remark=' + encodeURIComponent(remark)
    });
  },
  // 使用红包
  useRedpacket() {
    console.log("使用红包~");
  },
  // 点击查看须知
  clickKnown() {
    wx.navigateTo({
      url: '/pages/notice/notice',
    })
  },


})