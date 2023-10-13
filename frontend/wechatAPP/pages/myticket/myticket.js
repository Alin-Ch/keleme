// pages/myticket/myticket.js
Page({
  data: {
    active: 0,
  },

  tagChange(event) {
    var name = event.detail.name
  },

  onLoad(options) {
  },

  // 退款
  refund(e){
    var id = e.currentTarget.dataset.id
    console.log("退款",id);
  },
  // 使用水票
  useTicket(e){
    var id = e.currentTarget.dataset.id
    console.log("使用水票",id);
  },
  // 取消订单
  cancelTicket(e){
    console.log(e);
  },
  // 去支付
  goPay(e){
    console.log(e);
  },
})