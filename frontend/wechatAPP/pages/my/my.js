import Dialog from '@vant/weapp/dialog/dialog';
Page({
  data: {
    jifen: 100,
    redpacket: 3,
    money: 80,
    haveUserInfo: false,
    userinfo: {}
  },


  onLoad(options) {},
  onShow() {
    var userinfo = wx.getStorageSync('userinfo')
    if (userinfo) {
      this.setData({
        userinfo,
        haveUserInfo: true,
      })
    }
  },

  // 登录
  login() {
    wx.navigateTo({
      url: '/pages/login/login',
    })
  },
  // 查看头像
  previewImage(e) {
    var url = this.data.userinfo.avatar
    wx.previewImage({
      current: url, // 当前显示图片的链接
      urls: [url] // 需要预览的图片链接列表
    })
  },


  // 返利
  payBack() {
    var money = this.data.money
    Dialog.confirm({
        title: '已获得奖励',
        message: `￥${money}元`,
        confirmButtonText: '去提现',
        cancelButtonText: '确定'
      })
      .then(() => {
        // on confirm
      })
      .catch(() => {
        // on cancel
      });
  },
  // 积分
  jifen() {
    var jifen = this.data.jifen
    wx.showModal({
      title: '积分',
      content: `已获得${jifen}积分`,
      cancelText: '确定',
      confirmText: '前往商城',
      complete: (res) => {
        if (res.cancel) {}
        if (res.confirm) {
          wx.navigateTo({
            url: '/pages/shopmall/shopmall',
          })
        }
      }
    })
  },
  // 红包
  redpacket() {

  },
  // 前往设置
  goSetting() {
    wx.navigateTo({
      url: '/pages/setting/setting',
    })
  },
  // 联系客服
  contact() {

  },
  // 清除个人信息
  clearInfo() {
    // wx.clearStorageSync()  // 清除所有的本地存储
    try {
      wx.removeStorageSync("userinfo");
      wx.showToast({
        title: '已清除！',
        icon: "none"
      })
      this.setData({
        haveUserInfo: false
      })
    } catch (error) {
      console.error("清除信息失败：", error);
    }
  },
  // 抽奖
  lottery() {
    wx.navigateTo({
      url: '/pages/lottery/lottery',
    })
  },
})