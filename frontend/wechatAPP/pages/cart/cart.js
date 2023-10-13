import Dialog from '@vant/weapp/dialog/dialog';
Page({
  data: {
    carts: [],
    //  hasList: tue,          // 列表是否有数据
    totalPrice: 3050, // 总价，初始为0
    selectAll: false, // 全选状态，默认不全选
    selectNum: 0 //选中的件数
  },
  onLoad(options) {
    this.getCartData()
  },
  onShow: function () {
    this.ifSelectedAll()
    this.totlePrice()
    this.getCartData()
  },

  goOut() {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },

  // 再去逛逛
  goIndex() {
    wx.switchTab({
      url: '/pages/ticket/ticket',
    })
  },
  // 删除已选中
  deleteSel() {
    const carts = this.data.carts;
    var selCount = this.ifSelectedAll()
    if (selCount == 0) {
      return
    } else {
      Dialog.confirm({
          title: '删除选中的宝贝',
          message: '真的要删除选中的宝贝吗',
        })
        .then(() => {
          var newCart = []
          carts.forEach((item) => {
            if (item.selected != true) {
              newCart.unshift(item)
            }
          })
          wx.setStorageSync('cartList', newCart)
          this.setData({
            carts: newCart
          })
        })
        .catch(() => {
          // on cancel
        });
    }

  },

  // 从本地存储中获取数据
  getCartData() {
    var cartList = wx.getStorageSync('cartList') || []; // 获取购物车列表
    cartList = cartList.map(item => {
      item.newprice = item.newprice;
      return item;
    });
    this.setData({
      carts: cartList // 将购物车列表设置到当前页面的data中
    });
  },
  // 计算总的钱
  totlePrice: function () {
    let carts = this.data.carts;
    let total = 0;
    let num = 0;
    for (let i = 0; i < carts.length; i++) { // 循环列表得到每个数据
      if (carts[i].selected) { // 判断选中才会计算价格
        total += carts[i].count * carts[i].newprice;
        num += carts[i].num;
      }
    }
    this.setData({
      selectNum: num,
      // totalPrice: total.toFixed(2)
      totalPrice: total * 100
    });
  },

  // 计算是不是全选---以及选择了多少个
  ifSelectedAll() {
    const carts = this.data.carts;
    var selCount = 0
    for (let i = 0; i < carts.length; i++) {
      if (!carts[i].selected) {
        continue
      } else {
        selCount += 1
      }
    }
    if (selCount != carts.length) {
      this.setData({
        selectAll: false
      })
    } else {
      this.setData({
        selectAll: true
      })
    }
    return selCount
  },
  //选中反选
  selected: function (e) {
    const index = e.currentTarget.dataset.num;
    let carts = this.data.carts;
    let selectAll = this.data.selectAll;
    let count = 0;
    carts[index].selected = !carts[index].selected;
    //判断全选状态
    for (let i = 0; i < carts.length; i++) {
      if (carts[i].selected == true) {
        count++;
      }
    }
    if (count == carts.length) {
      selectAll = true;
    } else {
      selectAll = false;
    }
    this.setData({
      carts: carts,
      selectAll: selectAll
    });
    this.totlePrice();
    // 更新本地存储的购物车列表
    wx.setStorageSync('cartList', carts);
  },
  selectedAll: function () {
    let selectAll = this.data.selectAll; // 是否全选状态
    selectAll = !selectAll;
    let carts = this.data.carts;

    for (let i = 0; i < carts.length; i++) {
      carts[i].selected = selectAll; // 改变所有商品状态
    }
    this.setData({
      selectAll: selectAll,
      carts: carts
    });
    this.totlePrice(); // 获取总价
    // 更新本地存储的购物车列表
    wx.setStorageSync('cartList', carts);
  },
  addNum: function (e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let num = carts[index].count;
    num = num + 1;
    carts[index].count = num;
    this.setData({
      carts: carts
    });
    // 更新本地存储的购物车列表
    wx.setStorageSync('cartList', carts);
    this.totlePrice();
  },
  subNum: function (e) {
    const index = e.currentTarget.dataset.index;
    let carts = this.data.carts;
    let num = carts[index].count;
    if (num <= 1) {
      // 删除对应的商品
      carts.splice(index, 1);
    } else {
      num = num - 1;
      carts[index].count = num;
    }
    this.setData({
      carts: carts
    });
    // 更新本地存储的购物车列表
    wx.setStorageSync('cartList', carts);
    this.totlePrice();
  },

  // 提交订单
  onClickButton() {
    const carts = this.data.carts;
    var selCount = this.ifSelectedAll()
    if (selCount == 0) {
      wx.showToast({
        title: '请勾选商品',
        icon: 'error',
        duration: 1000
      });
    } else {
      // 选择商品并保存到变量中
      const selectedGoods = this.data.carts.filter(item => item.selected);
      var newCart = []
      carts.forEach((item) => {
        if (item.selected != true) {
          newCart.unshift(item)
        }
      })
      wx.setStorageSync('cartList', newCart)
      // 将选中的商品存储到本地存储
      wx.setStorageSync('selectedGoods', selectedGoods);

      // 跳转到结算页面
      wx.navigateTo({
        url: '/pages/pay/pay',
      });
      wx.login({
        success: (res) => {
          const appid = "wx32ea97c66f2f334a"
          const secret = "6a1daf4133f7b9e7832a0cf60fa34447"
        },
      })
    }

  },







})