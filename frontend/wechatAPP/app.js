// app.js
App({
  onLaunch() {
    var userinfo = wx.getStorageSync('userinfo') || {}
    this.globalData.userinfo = userinfo;
  },
  globalData: {
    userinfo: {},
    jingweidu: {
      weidu: "",
      jingdu: ""
    },
    orderParam: {},
  }
})