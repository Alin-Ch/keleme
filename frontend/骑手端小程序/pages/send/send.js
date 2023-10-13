// pages/send/send.js
Page({
  data: {
    weidu: "",
    jingdu: "",
    markers: [{
      latitude: "",
      longitude: "",
      title: "您所在的位置"
    }]
  },


  onLoad(options) {
    this.getLocation()
  },

  // 获取当前位置
  getLocation() {
    var that = this
    // 在需要进行定位的页面或组件中调用wx.getLocation接口
    wx.getLocation({
      type: 'wgs84', // 返回经纬度信息的坐标系类型，这里使用WGS84坐标系
      success: function (res) {
        var latitude = res.latitude; // 纬度
        var longitude = res.longitude; // 经度
        that.setData({
          weidu: latitude,
          jingdu: longitude,
          markers: [{
            id: 0,
            latitude: latitude,
            longitude: longitude,
            "width": 32,
            "height": 45,
          }]
        })
      },
      fail: function (res) {
        // 获取位置失败的处理逻辑
        wx.showToast({
          title: '获取位置失败，请重新定位',
          icon: "none"
        })
      }
    });
  },
})