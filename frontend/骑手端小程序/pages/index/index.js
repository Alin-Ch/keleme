const app = getApp()
const request = require('../../utils/request');
Page({
  data: {
    active: 0,
  },

  onLoad() {
    this.getUserinfo()
    this.getAllOrder()
  },
  onShow() {
    this.getAllOrder()
  },

  // 发请求获取所有订单
  getAllOrder() {
    request.get("/order").then((res) => {
      // console.log(res);
      // 1.先对数组简单的过滤一边，只要对应的状态
      var orderList = res.data.filter((item) => {
        return item.statu === 2 || item.statu === 3 || item.statu === 4;
      }).map((item) => {
        item.money = item.money.toFixed(2).toString();
        return item;
      }).reverse();
      // 2.对数组进行分组，不同状态分为不同的组
      var waiting = orderList.filter((order) => {
        return order.statu === 2;
      });
      var delivering = orderList.filter((order) => {
        return order.statu === 3;
      });
      var completed = orderList.filter((order) => {
        return order.statu === 4;
      });

      // 3.赋值
      this.setData({
        orderList,
        waiting,
        delivering,
        completed
      });
      wx.setStorageSync('delivering', delivering)
    }).catch((err) => {
      console.log(err);
    });

  },

  // 开始配送
  beginSend(e) {
    var order = e.currentTarget.dataset.order
    var param = {
      orderid: order.orderid,
      statu: order.statu,
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
              this.getAllOrder()
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
  // 已送达
  isSend(e) {
    var order = e.currentTarget.dataset.order
    var param = {
      orderid: order.orderid,
      statu: order.statu,
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
              this.getAllOrder()
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

  // 点击订单查看详情
  clickOrder(e) {
    var orderNo = e.currentTarget.dataset.orderno
    wx.navigateTo({
      url: '/pages/orderdetail/orderdetail?orderNo=' + orderNo,
    })
  },

  // 获取 app 的全局变量userinfo
  getUserinfo() {
    const userinfo = app.globalData.userinfo;
    this.setData({
      userinfo
    })
  },
  // tag标签页变化
  tagChange(event) {
    // wx.showToast({
    //   title: `切换到标签 ${event.detail.name}`,
    //   icon: 'none',
    // });
  },

})