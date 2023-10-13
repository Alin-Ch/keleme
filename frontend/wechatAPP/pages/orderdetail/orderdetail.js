import {
  get,
  post
} from '../../utils/req';
import Dialog from '@vant/weapp/dialog/dialog';
Page({

  data: {

  },

  onLoad(options) {
    var orderNo = options.orderNo
    this.setData({
      orderNo
    })

    this.getOrderData()

  },
  onReady() {},
  onShow() {},
  // 更新订单状态
  updateStatus() {
    var params = {
      orderid: "",
      ordernum: "",
      createtime: "",
      money: "",
      remark: "",
      statu: 1,
      order_userid: 0,
      address_id: 0,
    }
    params.orderid = this.data.orderNo
    params.ordernum = this.data.orderNo

  },
  // 从全局变量中拿到数据展示
  getOrderData() {
    var orderParam = getApp().globalData.orderParam;
    this.setData({
      address: orderParam.address,
      goodList: orderParam.goodList,
      orders: orderParam.orders
    })
    this.calMoney()
  },
  // 计算总的钱
  calMoney() {
    var goodList = this.data.goodList
    var money = 0
    for (let i = 0; i < goodList.length; i++) {
      const itemMoney = goodList[i].count * goodList[i].newprice;
      money += itemMoney
    }
    this.setData({
      money
    })
  },

  // 付款
  pay() {
    Dialog.confirm({
        title: '请付款',
        message: `￥${this.data.money}元`,
      })
      .then(() => {
        var param = {
          orderid: this.data.orderNo,
          statu: 1,
        }
        post("/order", param).then((res) => {
          if (res.code == 200) {
            wx.showToast({
              title: '付款成功',
              complete: () => {
                setTimeout(() => {
                  wx.switchTab({
                    url: '/pages/order/order',
                  });
                }, 2000); // 延迟2秒后跳转
              }
            })
          } else {
            wx.showToast({
              title: '付款失败',
              icon: "error"
            })
          }
        }).catch((err) => {
          wx.showToast({
            title: '服务器错误！',
            icon: "error"
          })
        })

      })
      .catch(() => {
        console.log("取消");
      });
  },

  // 要先获取订单
  getOrder() {
    var orderNo = this.data.orderNo
    get("/order/search/" + orderNo).then((res) => {
      this.setData({
        order: res.data
      })
    }).catch((err) => {
      console.log(err);
    })
  },

})