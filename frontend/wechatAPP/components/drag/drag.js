

const URL = 'https://mp.weixin.qq.com/s/anPq-g1ngNIvl3Iv1eQSSg'
const GIF_URL = 'https://staging-statics.maiscrm.com/modules/danonenutrilon/q3-wom/float-1.gif'

Component({
  properties: {},
  data: {
    gifUrl: GIF_URL,
    buttonTop: 0,
    buttonLeft: 0,
    windowHeight: '',
    windowWidth: '',
    imgStyle: ''
  },
  lifetimes: {
    attached() {
      this.init()
    },
  },
  methods: {
    init() {
      let that = this
      wx.getSystemInfo({
        success: function (res) {
          console.log(res);
          // 屏幕宽度、高度
          console.log('height=' + res.windowHeight);
          console.log('width=' + res.windowWidth);
          // b比例
          let scale = 750 / res.windowWidth
          // 高度,宽度 单位为px
          that.setData({
            windowHeight: res.windowHeight,
            windowWidth: res.windowWidth,
            positionTop: (res.windowHeight / 2) + (330 / scale),
            positionLeft: 350 / scale,
            scale
          })
          console.log('positionTop ', that.data.positionTop, ' positionLeft ', that.data.positionLeft)
        }
      })
    },
    click() {
      wx.navigateTo({
        url: '/pages/webview/index?url=' + URL
      })
      this.triggerEvent('click', '浮窗-第二件半价')
    },

    buttonStart(e) {
      // 获取起始点
      this.setData({
        startPoint: e.touches[0],
      })
    },
    // 刷新按钮移动中
    buttonMove(e) {
      var endPoint = e.touches[e.touches.length - 1]
      var translateX = endPoint.clientX - this.data.startPoint.clientX
      var translateY = endPoint.clientY - this.data.startPoint.clientY

      let positionTop = this.data.positionTop + translateY
      let positionLeft = this.data.positionLeft + translateX

      this.setData({
        imgStyle: `top: ${positionTop}px;left: ${positionLeft}px;`,
        positionTop,
        positionLeft,
        startPoint: endPoint
      })
    },
    buttonEnd: function (e) {}
  }
})