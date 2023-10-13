const request = require('../../utils/request')
Page({
  data: {
    isLogin: false,
    userinfo: {},
  },

  onLoad(options) {

  },
  onShow() {
    this.check()
  },

  // 登录
  login(e) {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },
  // 退出登录
  logout() {
    wx.showToast({
      title: '退出登录成功！',
      duration: 1000,
    })
    wx.removeStorageSync('userinfo')
    this.setData({
      isLogin: false
    })
  },
  // 编辑个人信息
  edit() {
    console.log("edit");
  },
  // 检查是否有用户信息
  check() {
    var userinfo = wx.getStorageSync('userinfo')
    if (userinfo) {
      this.setData({
        userinfo,
        isLogin: true
      })
      this.getStationById(userinfo.station_id)
    }
  },
  // 根据水站id查找水站
  getStationById(sid) {
    request.get("/station/search/" + sid).then((res) => {
      if (res.code == 200) {
        this.setData({
          station: res.data
        })
      }
    }).catch((err) => {
      console.log(err);
    })
  },

  // 点击一排四个的小图标
  clickIcon() {
    wx.showToast({
      title: '点击~',
      icon: "none",
      duration: 200
    })
  },

})