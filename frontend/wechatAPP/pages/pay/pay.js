import {
  get,
  post
} from '../../utils/req';
import Dialog from '@vant/weapp/dialog/dialog';
Page({
  data: {
    isActive: false, //点击反馈
    haveAddress: false, //是否选择了收货地址
    selectedAddress: {
      name: "张三",
      phone: "1684523",
      address: "广西桂林市雁山区桂林理工大学"
    },
    selectedGoods: [],
    remark: "", //订单备注
  },

  onLoad(options) {
    this.fromIndex(options)
  },
  onShow: function (options) {
    this.address()

  },

  // 从首页一键订水来的
  fromIndex(options) {
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2]; // 上一个页面对象
    if (prevPage) {
      var prevPagePath = prevPage.route; // 上一个页面路径
      // 如果是从商品详情页过来的
      if (prevPagePath === "pages/gooddetail/gooddetail") {
        var goodJson = decodeURIComponent(options.good);
        if (goodJson != "undefined") {
          var good = JSON.parse(goodJson);
          var count = options.count
          good.count = count
          this.setData({
            selectedGoods: [good],
            money: count * good.newprice,
          })
        }
        this.address()
      } else if (prevPagePath === "pages/index/index") {
        // console.log(options);
        var address = JSON.parse(decodeURIComponent(options.address));
        var good = JSON.parse(decodeURIComponent(options.good));
        var remark = decodeURIComponent(options.remark);
        this.setData({
          selectedGoods: [good],
          selectedAddress: address,
          remark,
          money: good.count * good.newprice,
          haveAddress: true,
        })
      } else {
        // 从本地存储中获取已选择的商品信息
        const selectedGoods = wx.getStorageSync('selectedGoods') || [];
        var money = 0
        for (let i = 0; i < selectedGoods.length; i++) {
          selectedGoods[i].newprice = selectedGoods[i].newprice
          money += selectedGoods[i].count * selectedGoods[i].newprice;
        }
        this.setData({
          selectedGoods,
          money: money.toFixed(2)
        })
        this.address()
      }
    }
  },

  // 收货地址
  address() {
    var address = wx.getStorageSync('selectedAddress') || ""; // 从本地缓存中获取用户选择的地址
    if (address) {
      this.setData({
        haveAddress: true,
        selectedAddress: address // 将用户选择的地址设置到当前页面的data中
      });
      // wx.removeStorageSync('selectedAddress'); // 清除本地缓存中的选择的地址数据
    }
  },

  // 选择地址
  chooseAddress() {
    wx.navigateTo({
      url: '/pages/address/address',
    })
  },
  // 输入备注
  remarkChange(event) {
    this.setData({
      remark: event.detail
    })
  },

  // 付款
  pay() {
    var orders = {
      createtime: "",
      ordernum: "",
      money: parseFloat(this.data.money),
      remark: this.data.remark,
    }
    var param = {
      address: this.data.selectedAddress,
      goodList: this.data.selectedGoods,
      orders
    }
    // 将参数保存在全局变量或缓存中
    getApp().globalData.orderParam = param;
    wx.showLoading({
      title: '加载中...',
      mask: true,
    })
    post("/order/create", param).then((res) => {
      if (res.code == 200) {
        var orderNo = res.data
        wx.hideLoading()
        wx.navigateTo({
          url: '/pages/orderdetail/orderdetail?orderNo=' + orderNo,
        })

      } else {
        console.log(res);
        wx.hideLoading()
        wx.showToast({
          title: res.msg,
          icon: "error",
          duration: 1000
        })
      }
    }).catch((err) => {
      console.log(err);
      wx.hideLoading()
    })

  },
  // 取消
  cancel() {
    wx.showToast({
      title: '取消成功',
    })
    setTimeout(() => {
      wx.navigateBack()
    }, 500)
  },
  // 生成订单号
  generateOrderNo: function () {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();
    let hour = now.getHours();
    let minute = now.getMinutes();
    let second = now.getSeconds();
    let millisecond = now.getMilliseconds();
    let randomNum = Math.floor(Math.random() * 900) + 100; // 生成一个三位随机数

    return `${year}${month}${day}${hour}${minute}${second}${millisecond}${randomNum}`;
  },



  // 点击反馈
  handleTap: function () {
    this.setData({
      isActive: true
    });

    // 延时恢复初始状态
    setTimeout(() => {
      this.setData({
        isActive: false
      });
    }, 200);
  },

})