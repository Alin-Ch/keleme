const request = require("../../utils/request");
const app = getApp()
import {
  get,
  post
} from '../../utils/req';
Page({

  data: {
    active: 0,
    showpop: false,
  },
  // 去购物车结算
  goCart() {
    wx.navigateTo({
      url: '/pages/cart/cart',
    })
  },

  tagChange(event) {},


  onLoad(options) {
    this.getLocation()

    setTimeout(() => {
      this.getData()
      this.getTicket()
    }, 1000)

  },
  onShow() {
    var userinfo = getApp().globalData.userinfo
    if (userinfo) {
      var cartList = wx.getStorageSync('cartList') || []
      this.setData({
        cartNum: cartList.length,
        cartList
      })
    } else {
      console.log("没有用户信息");
    }

  },



  // 加入购物车
  addCart(e) {
    var goodItem = e.currentTarget.dataset.item
    var cartList = this.data.cartList

    wx.showToast({
      title: '加入购物车成功',
      duration: 500
    })
    // 查找是否存在相同的商品ID
    var index = cartList.findIndex(item => item.goodid === goodItem.goodid);
    if (index !== -1) {
      // 如果已存在相同的商品ID，将数量相加
      cartList[index].count += goodItem.count;
    } else {
      // 将 goodItem 插入 cartList 的开头
      cartList.unshift(goodItem);
    }
    // 存入本地存储
    wx.setStorageSync('cartList', cartList);
    this.setData({
      cartList: cartList
    });
  },
  // 购买水票
  buyTicket(e) {
    var ticket = e.currentTarget.dataset.item
    console.log(ticket);
  },
  // 关闭pop
  onClose() {
    this.setData({
      showpop: false
    })
  },

  // 已选数量变化
  countChange(event) {
    var index = event.currentTarget.dataset.index;
    var count = event.detail;
    var goodsList = this.data.goodsList.slice(); // 复制原 goodsList 数组
    // 修改指定商品的数量
    goodsList[index].count = count;
    this.setData({
      goodsList: goodsList // 更新页面的 data 中的 goodsList 值
    });
  },
  ticketCount(event) {
    var index = event.currentTarget.dataset.index;
    var count = event.detail;
    var ticketList = this.data.ticketList.slice(); // 复制原 goodsList 数组
    // 修改指定商品的数量
    ticketList[index].count = count;
    this.setData({
      ticketList: ticketList // 更新页面的 data 中的 goodsList 值
    });
  },

  // 获取水票数据
  getTicket() {
    get('/ticket', )
      .then(response => {
        // 请求成功的回调函数
        if (response.code == 200) {
          var ticketList = response.data.map(function (item) {
            // 从每个对象中截取所需的字段，并返回一个新的对象
            return {
              count: 1,
              money: item.money,
              t_stationid: item.t_stationid,
              t_userid: item.t_userid,
              ticketid: item.ticketid,
              ticketname: item.ticketname,
              usecount: item.usecount,
            }
          });
          this.setData({
            ticketList: ticketList
          })
        } else if (response.code == -1) {
          wx.showToast({
            title: '请重新登录',
            icon: "none",
            duration: 1000
          })
          wx.removeStorageSync('userinfo')
          wx.navigateTo({
            url: '/pages/login/login',
          })
        }
      })
      .catch(error => {
        // 请求失败的回调函数
        console.error("req.js提示没有token", error);
      });
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
    get("/station/suitable?" + params).then((res) => {
      // console.log("获取所有商品成功~！", res);
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
            count: 1,
            cover: gooditem.cover,
            description: gooditem.description,
            goodid: gooditem.goodid,
            goodname: gooditem.goodname,
            newprice: gooditem.newprice.toFixed(2),
            oldprice: gooditem.oldprice.toFixed(2),
            selected: true,
            size: gooditem.size,
            sold: gooditem.sold,
            station_id: gooditem.station_id,
            stationsName: "",
            typeName: "",
            type_id: gooditem.type_id,
          };
        });
        this.setData({
          goodsList
        })
      }
    }).catch((err) => {
      wx.showToast({
        title: '服务器错误！',
        icon: "error",
        duration: 1000
      })
      console.error("获取商品失败~！", err);
      // 处理错误
    })
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

})