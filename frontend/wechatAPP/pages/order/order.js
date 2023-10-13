import {
  get,
  post
} from '../../utils/req';
import Dialog from '@vant/weapp/dialog/dialog';
Page({
  data: {
    active: 0,
    dialogshow: false, // 付款的dialog
    show2: false, // 确认退款的弹窗
  },

  onLoad(options) {
    this.getAllOrder()
  },
  onShow() {
    this.getAllOrder()
  },

  // 获取所有订单
  getAllOrder() {
    var userinfo = wx.getStorageSync('userinfo')
    get("/order/" + userinfo.userid).then((res) => {
      var noPayOrders = [];
      var watingOrders = [];
      var sendingOrders = [];
      var completedOrders = [];
      var cancelOrders = [];
      // 遍历数组
      res.data.forEach((item) => {
        item.money = item.money.toFixed(2)
        if (item.statu == 1) {
          noPayOrders.unshift(item)
        }
        if (item.statu == 2) {
          watingOrders.unshift(item)
        }
        if (item.statu == 3) {
          sendingOrders.unshift(item)
        }
        if (item.statu == 4) {
          completedOrders.unshift(item)
        }
        if (item.statu == 0) {
          cancelOrders.unshift(item)
        }
      })
      this.setData({
        orderList: res.data.reverse(),
        noPayOrders,
        watingOrders,
        sendingOrders,
        completedOrders,
        cancelOrders,
      })
    })
  },

  // 点击订单查看详情
  clickOrder(e) {
    var orderNo = e.currentTarget.dataset.orderno
    wx.navigateTo({
      url: '/pages/ordermy/ordermy?orderNo=' + orderNo,
    })
  },

  // 付款
  pay(e) {
    var order = e.currentTarget.dataset.order
    var param = {
      orderid: order.orderid,
      statu: order.statu
    }
    Dialog.confirm({
        title: '请扫码付款',
      })
      .then(() => {
        post("/order", param).then((res) => {
          if (res.code == 200) {
            wx.showToast({
              title: '付款成功~',
              icon: "success"
            })
            this.getAllOrder()
          } else {
            wx.showToast({
              title: res.msg,
              icon: "error"
            })
          }
        }).catch((err) => {
          wx.showToast({
            title: '服务器错误',
            icon: "error",
          })
        })
      })
      .catch(() => {
        // on cancel
      });

  },
  // 退款
  refund(e) {
    var order = e.currentTarget.dataset.order
    var param = {
      orderid: order.orderid,
      statu: order.statu
    }
    wx.showModal({
      title: '退款',
      content: '真的要退款吗？',
      complete: (res) => {
        if (res.confirm) {
          wx.showLoading({})
          post("/order/cancel", param).then((res) => {
            if (res.code == 200) {
              wx.showToast({
                title: '退款成功',
                icon: "success"
              })
              this.getAllOrder()
            } else {
              wx.showToast({
                title: res.msg,
                icon: "none"
              })
            }

            wx.hideLoading()
          }).catch((err) => {
            console.log(err);
          })
        }
        if (res.cancel) {

        }
      }
    })

  },
  // 取消订单
  cancel(e) {
    var order = e.currentTarget.dataset.order
    var order = e.currentTarget.dataset.order
    var param = {
      orderid: order.orderid,
      statu: order.statu
    }
    wx.showModal({
      title: '取消订单',
      content: '真的要取消订单吗？',
      complete: (res) => {
        if (res.confirm) {
          wx.showLoading({})
          post("/order/cancel", param).then((res) => {
            if (res.code == 200) {
              wx.showToast({
                title: '取消成功',
                icon: "success"
              })
              this.getAllOrder()
            } else {
              wx.showToast({
                title: res.msg,
                icon: "none"
              })
            }
            wx.hideLoading()
          }).catch((err) => {
            console.log(err);
          })
        }
        if (res.cancel) {}
      }
    })
  },

})