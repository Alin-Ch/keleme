//index.js
//获取应用实例
const app = getApp()
const request = require('../../utils/request');
import {
  post
} from '../../utils/req';
const url = "http://localhost:8080/api/user/register"
Page({
  data: {
    username: "test测试",
    phone: '',
    password: '123456',
    againPassword: '123456',
    verCode: "",
    cover: "/images/huge.jpg",
    key: "",
    url: "http://localhost:8080/api/captcha?key=",
    captcha: "", //图片验证码
    checked: true, //勾选用户协议
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
  // 获取输入用户名 
  usernameInput: function (e) {
    var username = e.detail.trim()
    this.setData({
      username
    })
  },
  // 获取输入手机号
  phoneInput: function (e) {
    var phone = e.detail.trim()
    this.setData({
      phone
    })
  },
  onGetPhoneNumber: function (e) {
    this.getCodeAndToken()
  },
  // 获取输入密码 
  passwordInput: function (e) {
    var password = e.detail.trim()
    this.setData({
      password
    })
  },
  againPasswordInput: function (e) {
    var againPassword = e.detail.trim()
    this.setData({
      againPassword
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

  // 勾选用户协议
  checkChange(event) {
    this.setData({
      checked: event.detail,
      disabled: !event.detail,
    });
  },


  // 注册
  register: function () {
    var that = this;
    var user = {
      avatar: this.data.cover,
      username: this.data.username,
      phone: this.data.phone,
      password: this.data.password,
    }
    if (this.data.username == "" || this.data.phone == "" || this.data.password == "" || this.data.againPassword == "") {
      wx.showToast({
        title: '请检查表单输入',
        icon: 'none',
        duration: 1000
      })
    } else if (this.data.password !== this.data.againPassword) {
      wx.showToast({
        title: '两次输入密码不相同',
        icon: 'none',
        duration: 1000
      })
    } else if (!/^1[3456789]\d{9}$/.test(this.data.phone)) {
      wx.showToast({
        title: '手机号格式不正确',
        icon: 'none',
        duration: 1000
      });
    } else {
      // 发起 POST 请求
      post("/user/register", user)
        .then((res) => {
          console.log("注册成功~", res);
          if (res.code == 200) {
            wx.showToast({
              title: '注册成功！',
              icon: "success"
            })
            wx.navigateTo({
              url: '/pages/login/login',
            })
          } else {
            wx.showToast({
              title: res.msg,
              icon: "error"
            })
          }
        })
        .catch((err) => {
          console.log(err);
          wx.showToast({
            title: '服务器错误！',
            icon: "error"
          })
        })
    }
  },




  // 获取手机号
  getCodeAndToken() {
    var access_token = ""
    wx.request({
      url: 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx32ea97c66f2f334a&secret=6a1daf4133f7b9e7832a0cf60fa34447',
      method: "GET",
      success: function (res) {
        console.log(res);
        access_token = res.data.access_token
      }
    })

    setTimeout(() => {
      wx.request({
        url: 'https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=' + access_token,
        method: "POST",
        header: {
          'Content-Type': 'application/json'
        },
        data: {
          "code": "e31968a7f94cc5ee25fafc2aef2773f0bb8c3937b22520eb8ee345274d00c144"
        },
        success: function (res) {
          console.log(res);
        }
      });

    }, 1000)
  },
})