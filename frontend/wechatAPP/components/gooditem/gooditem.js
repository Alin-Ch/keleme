// components/gooditem/gooditem.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    "good": {
      type: Object,
      value: {}
    },
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    previewImage(e) {
      const src = e.currentTarget.dataset.src;
      // wx.previewImage({
      //   current: src, // 当前显示图片的链接，可选项
      //   urls: [src] // 需要预览的图片链接列表
      // });
    }
  }
})