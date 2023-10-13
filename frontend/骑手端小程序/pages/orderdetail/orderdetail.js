const app = getApp()
const request = require('../../utils/request');
Page({
  data: {},

  onLoad(options) {
    // 1.先获取水站id
    this.getUserinfo()
    // 2.再获取订单详情
    var orderNo = options.orderNo
    this.getOrder(orderNo)
    this.getOrderDetail(orderNo)

  },

  onShow() {},

  // 获取订单
  getOrder(orderNo) {
    request.get("/order/search/" + orderNo).then((res) => {
      if (res.code == 200) {
        this.setData({
          orderInfo: res.data
        })
        this.getAddress()
      }
    }).catch((err) => {
      console.log("err--", err);
    })
  },
  // 获取收货地址
  getAddress() {
    var addressid = this.data.orderInfo.address_id
    request.get("/address/search/" + addressid).then((res) => {
      if (res.code == 200) {
        this.setData({
          address: res.data
        })
      }
    }).catch((err) => {
      console.log(err);
    })
  },

  // 获取订单详情
  getOrderDetail(orderNo) {
    request.get("/orderDetail/" + orderNo).then((res) => {
      // console.log("获取订单详情", res);
      var station_id = this.data.userinfo.station_id
      var filteredData = res.data.filter((item) => {
        return item.station_id === station_id;
      }); //这个是一个空值，因为后端没有返回station_id
      res.data.forEach((item) => {
        this.getGoodByGoodid(item.good_id)
      })
      this.setData({
        goodList: filteredData,
      })
    }).catch((err) => {
      console.log(err);
    })
  },

  // 根据商品good_id获取商品数据
  getGoodByGoodid(goodid) {
    request.get("/good/" + goodid).then((res) => {
      // console.log(res);
      if (res.code == 200) {
        var good = res.data
        var goodList = this.data.goodList
        if (good.station_id == this.data.userinfo.station_id) {
          goodList.push(good)
        }
        this.setData({
          goodList
        })
      } else {
        wx.showToast({
          title: '请重新登录',
          icon: "none",
          complete: setTimeout(() => {
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }, 1000)
        })
      }
    }).catch((err) => {
      console.log(err);
    })

  },
  // 判断是否是自己水站的商品   没有用
  check() {
    var stationid = this.data.userinfo.station_id
    var goodList = this.data.goodList
    var newGoodList = []
    goodList.forEach(item => {
      if (item.station_id == stationid) {
        newGoodList.push(item)
      }
    });

    this.setData({
      goodList: []
    })
  },

  // 开始配送
  beginSend() {
    var param = {
      orderid: this.data.orderInfo.orderid,
      statu: this.data.orderInfo.statu
    }
    wx.showModal({
      title: '开始配送',
      content: '真的要开始配送吗？',
      complete: (res) => {
        if (res.confirm) {
          request.post("/order", param).then((res) => {
            if (res.code == 200) {
              wx.showToast({
                title: '开始配送',
                icon: "success"
              })
              this.getOrder(param.orderid)
            } else {
              wx.showToast({
                title: '请求失败',
                icon: "none"
              })
            }
          }).catch((err) => {
            console.log(err);
          })
        }
      }
    })

  },
  // 我已送达
  complete() {
    var param = {
      orderid: this.data.orderInfo.orderid,
      statu: this.data.orderInfo.statu
    }
    wx.showModal({
      title: '已送达',
      content: '真的送达了吗？',
      complete: (res) => {
        if (res.confirm) {
          request.post("/order", param).then((res) => {
            if (res.code == 200) {
              wx.showToast({
                title: '已送达',
                icon: "success"
              })
              this.getOrder(param.orderid)
            } else {
              wx.showToast({
                title: '请求失败',
                icon: "none"
              })
            }
          }).catch((err) => {
            console.log(err);
          })
        }
      }
    })
  },

  // 获取 app 的全局变量userinfo
  getUserinfo() {
    const userinfo = app.globalData.userinfo;
    this.setData({
      userinfo
    })
  },


})