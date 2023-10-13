 const app = getApp();
 import {
   get,
   post
 } from '../../utils/req';
 Page({
   data: {
     latitude: 25.06254,
     longitude: 110.30378,
     markers: [{
       "id": 0,
       latitude: 25.278334,
       longitude: 110.316815,
       "width": 32,
       "height": 45,
     }, ],
     username: '', // 收货人
     phone: '', // 手机号
     address: "", // 收货地址
     detailAddress: "", // 详细地址
     stationList: [], // 水站列表
     jingweidulist: []
   },


   onLoad(options) {
     this.getLocation()


   },
   onShow() {
     this.getStation()
   },

   // 获取水站数据
   getStation() {
     var that = this
     var jingweidulist = [];

     wx.request({
       url: 'http://localhost:8080/api/station',
       method: "GET",
       success: function (res) {
         if (res.data.code == 200) {
           console.log('获取水站成功~', res);
           // 遍历res.data.data数组，封装经纬度数据并添加到jingweidulist数组中
           res.data.data.forEach(function (item) {
             var id = item.sid
             var weidu1 = that.data.latitude
             var jingdu1 = that.data.longitude
             var jingdu = item.jingdu;
             var weidu = item.weidu;
             var distribution = item.distribution //水站的配送距离
             var distance = that.calculateDistance(weidu1, jingdu1, weidu, jingdu) //计算距离
             console.log(item.sname, "配送距离：", distribution, "实际距离：", distance);
             if (distance < distribution) {
               var obj = {
                 id: id,
                 latitude: Number(weidu),
                 longitude: Number(jingdu),
                 "width": 32,
                 "height": 45,
               };
               jingweidulist.push(obj);
             }
           });
           var newMarkers = that.data.markers.concat(jingweidulist)
           console.log("拼接之后的，符合条件的markers 第一个是自己的坐标:", newMarkers);
           that.setData({
             jingweidulist,
             markerss: newMarkers,
           })

         } else {
           console.log('获取失败!');
         }
       },
       fail: function (err) {
         wx.showToast({
           title: '服务器错误！',
           icon: "error"
         })
       }
     })
     console.log("`````````符合条件的经纬度列表", jingweidulist);


   },

   // 获取两个点之间的距离  第一个函数好像没用
   getAlldistance() {
     var that = this
     // 计算每一项与data中的latitude和longitude的差值并存储在newList数组中
     var newRemarkList = [];
     var jingweidulist = this.data.jingweidulist
     jingweidulist.forEach(function (item) {
       var weidu1 = that.data.latitude
       var jingdu1 = that.data.longitude
       var weidu2 = item.weidu
       var jingdu2 = item.jingdu
       var distance = that.calculateDistance(weidu1, jingdu1, weidu2, jingdu2)
       //  console.log('111', distance);
       newRemarkList.push(distance);
     });
     console.log(newRemarkList);
   },
   calculateDistance(lat1, lon1, lat2, lon2) {
     const EARTH_RADIUS = 6371; // 地球半径，单位为千米

     const dLat = this.toRadians(lat2 - lat1);
     const dLon = this.toRadians(lon2 - lon1);

     const a =
       Math.sin(dLat / 2) * Math.sin(dLat / 2) +
       Math.cos(this.toRadians(lat1)) * Math.cos(this.toRadians(lat2)) *
       Math.sin(dLon / 2) * Math.sin(dLon / 2);

     const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

     const distance = EARTH_RADIUS * c;
     return distance * 1000;
   },
   toRadians(degrees) {
     return degrees * (Math.PI / 180);
   },


   // 表单内容发生变化
   nameChange(event) {
     this.setData({
       username: event.detail.trim()
     });
   },
   phoneChange(event) {
     const inputValue = event.detail.trim();
     const filteredValue = inputValue.replace(/\D/g, ''); // 过滤非数字字符
     this.setData({
       phone: filteredValue
     });
   },
   detailChange(event) {
     this.setData({
       detailAddress: event.detail.trim()
     });
   },
   submitForm() {
     var userinfo = wx.getStorageSync('userinfo') || ""
     var params = {
       username: this.data.username,
       phone: this.data.phone,
       detailaddress: this.data.address + this.data.detailAddress,
       weidu: this.data.latitude.toString(),
       jingdu: this.data.longitude.toString(),
       address_userid: userinfo.userid
     }
     if (this.validateForm()) {
       // 表单校验通过，执行提交操作
       post('/address?', params).then((res) => {
         if (res.code == 200) {
           wx.showToast({
             title: '添加成功！',
             icon: "success",
             duration: 1000,
           })
           setTimeout(() => {
             wx.navigateBack()
           }, 1000)
         }
       }).catch((error) => {
         console.error(error);
       });
     }
   },
   // 校验规则
   validateForm() {
     const {
       username,
       phone,
       address,
       detailAddress
     } = this.data;
     if (!username || !phone || !address || !detailAddress) {
       wx.showToast({
         title: '清填写完整收货信息',
         icon: 'none'
       });
       return false;
     }
     // 用户名校验
     if (username.length < 2 || username.length > 10) {
       wx.showToast({
         title: '用户名长度必须为2~10个字符',
         icon: "none"
       })
       return false;
     }
     // 手机号正则校验
     const phonePattern = /^1[3456789]\d{9}$/;
     if (!phonePattern.test(phone)) {
       wx.showToast({
         title: '请输入正确的手机号',
         icon: 'none'
       });
       return false;
     }
     return true;
   },



   // 定点选择
   chooseAddress() {
     var that = this
     // 在需要进行地图选点的页面或组件中调用wx.chooseLocation接口
     wx.chooseLocation({
       success: function (res) {
         var latitude = res.latitude; // 选点的纬度
         var longitude = res.longitude; // 选点的经度
         var name = res.name; // 选点的名称
         var address = res.address; // 选点的详细地址

         // 在成功选点后，可以根据需要进行进一步处理
         //  console.log('纬度：', latitude);
         //  console.log('经度：', longitude);
         // console.log('名称：', name);
         // console.log('详细地址：', address);
         that.setData({
           address,
           latitude,
           longitude,
           'markers[0].latitude': latitude,
           'markers[0].longitude': longitude,
           'markerss[0].latitude': latitude,
           'markerss[0].longitude': longitude
         })
         var map = wx.createMapContext('myMap');
         map.moveToLocation();
         that.getStation()

       },
       fail: function (res) {
         // 选点失败的处理逻辑
         console.log('选点失败：', res);
         wx.showToast({
           title: '选择地址失败！请重新选择',
           icon: "none"
         })
         // wx.openLocation({
         //   latitude: 25,
         //   longitude: 110,
         // })
       }
     });
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
           latitude: res.latitude,
           longitude: res.longitude,
           'markers[0].latitude': latitude,
           'markers[0].longitude': longitude,
           'markerss[0].latitude': latitude,
           'markerss[0].longitude': longitude,
           /* //这里要改成这样才行
             markers: [{
             id: 0,
             latitude: res.latitude,
             longitude: res.longitude,
             title: '当前位置'
           }] */
         })
         // 调用其他相关接口或方法，进行地图展示或位置处理等操作
         // 移动地图到当前位置
         var map = wx.createMapContext('myMap');
         map.moveToLocation();
         //  that.getStation()
       },
       fail: function (res) {
         // 获取位置失败的处理逻辑
       }
     });
   },
   // 连续定位
   continue () {
     // 在需要进行连续定位的页面或组件中调用wx.startLocationUpdateBackground接口
     wx.startLocationUpdateBackground({
       success: function (res) {
         console.log('开始连续定位');
       },
       fail: function (res) {
         // 开启连续定位失败的处理逻辑
         console.log('开启连续定位失败：', res);
       }
     });

     // 监听位置变化事件
     wx.onLocationChange(function (res) {
       var latitude = res.latitude; // 纬度
       var longitude = res.longitude; // 经度

       // 在位置变化时，可以根据需要进行进一步处理
       console.log('纬度：', latitude);
       console.log('经度：', longitude);

       // 调用其他相关接口或方法，进行地图展示或位置处理等操作
     });

   },

 })