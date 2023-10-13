const app = getApp();
const request = require("../../utils/request")
const url = "http://localhost:8080/api/user"
Page({

  data: {
    userid: "",
    avatar: "/images/huge.jpg",
    gender: "男",
    username: "",
    phone: "",
    password: "",
    jifen: "",
    role: "",
    token: "",
    sms: "",
    showGender: false,
    genderList: [{
        name: '男',
      },
      {
        name: '女',
      }
    ],


  },

  onLoad(options) {
    var userinfo = wx.getStorageSync('userinfo')
    if (userinfo) {
      this.setData({
        avatar: userinfo.avatar,
        gender: userinfo.gender,
        username: userinfo.username,
        phone: userinfo.phone,
        token: userinfo.token,
      })
    }
  },

  // 获取用户信息
  onChooseAvatar(e) {
    // console.log(e);
    var avatar = e.detail.avatarUrl
    this.setData({
      avatar,
    })
  },

  // 弹出层显示隐藏与选择
  showGender() {
    this.setData({
      showGender: true
    });
  },
  genderClose() {
    this.setData({
      showGender: false
    });
  },
  genderSelect(event) {
    this.setData({
      gender: event.detail.name
    })
  },
  // 输入用户信息
  nameChange(event) {
    this.setData({
      username: event.detail.trim()
    })
  },
  phoneChange(event) {
    const phone = event.detail.trim();
    this.setData({
      phone
    })
  },
  // 保存信息
  submit() {
    var newUserinfo = this.updateUserinfo()
    var phone = this.data.phone
    // 正则表达式校验电话号码格式
    const phoneRegExp = /^1[3-9]\d{9}$/;
    if (phone === '') {
      // 如果电话号码为空，则清空校验错误提示信息并更新数据
      wx.showToast({
        title: '请输入手机号',
        icon: "none"
      })
    } else if (!phoneRegExp.test(phone)) {
      // 如果电话号码格式不正确，则显示校验错误提示信息并更新数据
      wx.showToast({
        title: '请输入正确的手机号',
        icon: "none"
      })
    } else {
      // 如果电话号码格式正确，则更新数据
      wx.setStorageSync('userinfo', newUserinfo)
      // 这里要再发一次请求，更新用户信息
      wx.request({
        url: url,
        data: newUserinfo,
        method: "POST",
        header: {
          'Token': this.data.token
        },
        success: function(res){
          if (res.data.code==200) {
            wx.showToast({
              title: '修改成功！',
              icon: "success"
            })
            wx.reLaunch({
              url: '/pages/my/my',
            })
          }
        },
        fail: function(res){
          wx.showToast({
            title: '保存失败！请联系管理员',
            icon: "error"
          })
        }
      })

    }

  },
  // 更新用户信息
  updateUserinfo() {
    const {
      userid,
      avatar,
      username,
      phone,
      gender,
      password,
      jifen,
      role,
      token,
    } = this.data;
    const newUserinfo = {
      userid,
      avatar,
      username,
      phone,
      gender,
      password,
      jifen,
      role,
      token,
    }
    const userinfo = wx.getStorageSync('userinfo')
    userinfo.avatar = this.data.avatar
    userinfo.username = this.data.username
    userinfo.gender = this.data.gender
    userinfo.phone = this.data.phone
    return userinfo
  },

  onShow: function () { //可有可无
    const systemInfo = wx.getSystemInfoSync();
    const windowHeight = systemInfo.windowHeight;
    const windowWidth = systemInfo.windowWidth;
    this.setData({
      boxStyle: `width:${windowWidth}px; height:${windowHeight}px;`
    });
  },

  afterRead(event) {
    const {
      file
    } = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    wx.uploadFile({
      url: 'https://example.weixin.qq.com/upload', // 仅为示例，非真实的接口地址
      filePath: file.url,
      name: 'file',
      formData: {
        user: 'test'
      },
      success(res) {
        // 上传完成需要更新 fileList
        const {
          fileList = []
        } = this.data;
        fileList.push({
          ...file,
          url: res.data
        });
        this.setData({
          fileList
        });
      },
    });
  },

})