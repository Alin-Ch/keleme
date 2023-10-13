//index.js
//获取应用实例
const app = getApp()
import {
  post
} from '../../utils/req';
const baseUrl = "http://localhost:8080/api"
Page({
  data: {
    phone: '18888888887',
    password: '123456',
    verCode: "",
    cover: "/images/huge.jpg",
    key: "",
    url: baseUrl + "/captcha?key=",
    captcha: "",
  },


  onShow: function () {
    // 生命周期函数--监听页面显示
    // wx.hideTabBar({})
  },
  onLoad: function () {
    this.getCaptcha()

  },

  // 设置图片验证码
  getCaptcha() {
    // var key = Math.random().toString(36).substr(2);
    var key = Math.random()
    this.setData({
      key,
      captcha: this.data.url + key
    })
  },
  // 获取输入手机号 
  usernameInput: function (e) {
    var phone = e.detail.value.trim()
    this.setData({
      phone
    })
  },
  // 获取输入密码 
  passwordInput: function (e) {
    var password = e.detail.value.trim()
    this.setData({
      password
    })
  },
  // 输入验证码
  codeInput(e) {
    var verCode = e.detail.value.trim()
    this.setData({
      verCode
    })
  },
  // 点击图片更新验证码
  changeCaptcha() {
    var key = Math.random()
    this.setData({
      key,
      captcha: this.data.url + key
    })
  },
  // 选择用户头像
  onChooseAvatar(e) {
    var cover = e.detail.avatarUrl
    this.setData({
      cover,
    })
  },

  // 登录处理
  login: function () {
    var that = this;
    var user = {
      phone: this.data.phone,
      password: this.data.password,
      verCode: this.data.verCode
    }
    if (this.data.username == "" || this.data.password == "") {
      wx.showToast({
        title: '账号或密码不能为空',
        icon: 'none',
        duration: 2000
      })
    } else {
      // 发起 POST 请求
      post("/user/login?key=" + this.data.key, {
        phone: that.data.phone,
        password: that.data.password,
        verCode: that.data.verCode
      }).then((res) => {
        if (res.code == 200) {
          wx.showToast({
            title: '登录成功！',
            duration: 2000,
            complete: function () {
              wx.setStorageSync('userinfo', res.data);
              wx.reLaunch({
                url: '/pages/my/my',
              });
            }
          });
        } else {
          wx.showToast({
            title: '登录失败，请重新尝试',
            icon: "none"
          })
          this.getCaptcha()
        }
      }).catch((err) => {
        wx.showToast({
          title: '服务器错误！请联系管理员',
          icon: "error"
        })
        this.getCaptcha()
      })
    }
  },

  // 去注册
  goRegister() {
    wx.navigateTo({
      url: '/pages/register/register',
    })
  },

  // 忘记密码
  forget() {
    wx.navigateTo({
      url: '/pages/forget/forget',
    })
  },
})