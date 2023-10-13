// app.js
App({
  onLaunch() {
    const userinfo = wx.getStorageSync('userinfo');
    this.globalData.userinfo = userinfo;
  },
  globalData: {
    userinfo: null
  }
})