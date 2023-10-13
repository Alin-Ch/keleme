import {
  get,
  del
} from '../../utils/req';
import Dialog from '@vant/weapp/dialog/dialog';
import Toast from '@vant/weapp/toast/toast';
const url = "http://localhost:8080/api"
Page({
  data: {
    addressList: [{
      name: "张三",
      phone: "188888",
      isdefault: "1",
      address: "广西桂林理工大学给了两个咕噜咕噜咕噜咕噜给了两个六公里"
    }, {
      name: "李四",
      phone: "188888",
      isdefault: "0",
      address: "广西桂林理工大学给了两个咕噜咕噜咕噜咕噜给了两个六公里"
    }, {
      name: "王五",
      phone: "188888",
      isdefault: "0",
      address: "广西桂林理工大学给了两个咕噜咕噜咕噜咕噜给了两个六公里"
    }, ]
  },

  onLoad() {},
  onShow() {
    this.getAddress()
  },


  // 获取当前用户的收货地址
  getAddress() {
    var userinfo = wx.getStorageSync('userinfo')
    var params = {
      id: userinfo.userid,
    }
    get('/address/', params).then((res) => {
      if (res.code == 200) {
        var userinfo = wx.getStorageSync('userinfo');
        var filteredArray = res.data.filter(function (item) {
          return item.address_userid == userinfo.userid;
        });
        for (let i = 0; i < filteredArray.length; i++) {
          if (filteredArray[i].isdefault == 1) {
            this.setData({
              address: filteredArray[i].detailaddress
            })
          }
          filteredArray[i].name = filteredArray[i].detailaddress
        }
        this.setData({
          addressList: filteredArray
        })
      } else if (res.code == -1) {
        wx.showToast({
          title: res.msg,
          icon: "none",
          duration: 1000,
          complete: () => {
            wx.removeStorageSync('userinfo')
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }
        })
      }
    }).catch((error) => {
      console.error(error);
    });
  },

  // 点击地址
  clickItem(e) {
    var address = e.currentTarget.dataset.address

    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2]; // 上一个页面对象

    if (prevPage) {
      var prevPagePath = prevPage.route; // 上一个页面路径
      console.log("上一个页面路径：", prevPagePath);
      if (prevPagePath == "pages/pay/pay") {
        wx.setStorageSync('selectedAddress', address); // 存储用户选择的地址到本地缓存
        wx.navigateBack()
      }
    } else {
      console.log("当前页面为首个页面");
    }

  },
  // 删除
  delete(e) {
    var item = e.currentTarget.dataset.item
    var addressid = item.addressid
    Dialog.confirm({
        title: '确认删除该收货地址吗',
      }).then(() => {
        // 发请求删除数据
            del("/address/" + addressid).then((res) => {
              if (res.code == 200) {
                Toast.success('删除成功');
                this.getAddress()
              } else {
                Toast.fail('删除失败，请联系管理员');
              }
            }).catch((err) => {
              wx.showToast({
                title: '删除失败，请联系管理员',
                icon: "none",
                duration: 500
              })
            })
      })
      .catch(() => {
        // 啥也不干
      });
  },
  // 编辑
  edit(e) {
    var item = e.currentTarget.dataset.item
    wx.navigateTo({
      url: '/pages/addrsetting/addrsetting?item=' + JSON.stringify(item),
    })
  },
  // 添加地址
  addAddress() {
    wx.navigateTo({
      url: '/pages/addaddress/addaddress',
    })
  },
})