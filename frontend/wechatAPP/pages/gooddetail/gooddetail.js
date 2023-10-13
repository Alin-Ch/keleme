import Toast from '@vant/weapp/toast/toast';
import {
  get
} from '../../utils/req';
const baseUrl = "http://localhost:8080/api/good/"
Page({
  data: {
    gooditem: "",
    show: false,
    count: 1,
  },


  onLoad(options) {
    var goodid = options.goodid
    if (goodid) {
      this.getGoodById(goodid)
    }
    if (options.good) {
      var goodJson = decodeURIComponent(options.good);
      var good = JSON.parse(goodJson);
      this.setData({
        gooditem: good
      })
    }
  },
  onShow() {
    var cartList = wx.getStorageSync('cartList') || []
    this.setData({
      cartList
    })
  },

  // 根据商品id查找商品
  getGoodById(goodid) {
    get("/good/" + goodid).then((res) => {
      if (res.code == 200) {
        this.setData({
          gooditem: res.data
        })
      } else {
        wx.showToast({
          title: '查询失败',
          icon: "error",
          duration: 1000
        })
      }

    }).catch((err) => {
      console.log(err);
    })
  },
  // 查看大图
  previewImage(e) {
    var url = "http://localhost:8080/api/files/" + this.data.gooditem.cover
    wx.previewImage({
      current: url, // 当前显示图片的链接
      urls: [url] // 需要预览的图片链接列表
    })
  },

  // 底部点击事件
  onClickIcon() {
    Toast('点击图标');
  },
  goBuy() {
    Toast('点击按钮');
  },

  // 选择商品规格和数量
  showPopup() {
    this.setData({
      show: true
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  countChange(event) {
    // 购买数量
    this.setData({
      count: event.detail
    })
  },
  // 添加购物车和购买
  addCart() {
    // 将当前商品的信息存储到购物车列表中
    var cartList = wx.getStorageSync('cartList') || []; // 获取购物车列表，若购物车为空则初始化为空数组
    var gooditem = this.data.gooditem
    var count = this.data.count
    gooditem.count = count
    gooditem.selected = true
    // 检查购物车中是否已存在相同商品
    const existingProduct = cartList.find(item => item.goodid === gooditem.goodid);
    if (existingProduct) {
      // 如果商品已存在，增加其数量
      existingProduct.count += count;
      wx.setStorageSync('cartList', existingProduct);
    } else {
      // 如果商品不存在，将其添加到购物车列表
      gooditem.count = this.data.count
      gooditem.selected = true
      cartList.push(gooditem);
    }
    wx.setStorageSync('cartList', cartList); // 存储购物车列表到本地缓存

    Toast('已加入购物车');
    this.setData({
      show: false,
      cartList: cartList
    });
  },
  buy() {
    this.setData({
      show: false
    });
    var good = this.data.gooditem
    var count = this.data.count
    var goodJson = JSON.stringify(good);
    wx.navigateTo({
      url: '/pages/pay/pay?good=' + encodeURIComponent(goodJson) + "&count=" + count,
    })
  },
  gotoCart() {
    wx.navigateTo({
      url: '/pages/cart/cart',
    })
  },
  addToCart: function (product) {
    // 将当前商品的信息存储到购物车列表中
    var cartList = wx.getStorageSync('cartList') || []; // 获取购物车列表，若购物车为空则初始化为空数组
    cartList.push(product); // 添加当前商品到购物车列表
    wx.setStorageSync('cartList', cartList); // 存储购物车列表到本地缓存

    // 弹出提示框或 toast，提示用户商品已成功加入购物车
    wx.showToast({
      title: '已加入购物车',
      icon: 'success',
      duration: 2000
    });
  },




  onShareAppMessage() {

  }
})