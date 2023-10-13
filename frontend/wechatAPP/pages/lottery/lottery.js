/**
 * 大转盘抽奖
 */

var util = require("../../utils/util.js");
var app = getApp();
import {
  post
} from '../../utils/req';
// const url = "http://localhost:8080/api/user"
Page({

  //奖品配置
  awardsConfig: {
    chance: true
  },

  data: {
    awardsList: {},
    animationData: {},
    btnDisabled: '',
    showModalStatus: false,
    drawSetting: {
      "rate1": 9,
      "rate2": 2,
      "rate3": 2,
      "rate4": 2,
      "rate5": 1,
      "rate6": 1
    },
    awardContent: "", // 抽到的奖品
    hasDrawn: false, // 储存用户今天是否已经抽过奖的状态，默认为false
    prize: '', // 储存抽中的奖品，默认为空字符串
    lottery: false,
    jifen: 0,
  },

  onLoad: function (e) {
    var userinfo = wx.getStorageSync('userinfo')
    this.setData({
      jifen: userinfo.jifen
    })
  },

  // 抽奖--判断是否登录
  draw() {
    var isChecked = this.checked()
    if (isChecked) {
      const today = new Date().toLocaleDateString();
      var userinfo = wx.getStorageSync('userinfo')
      // const { token, verCode, ...newUserinfo } = userinfo;
      // 使用新的 userinfo 对象，不包含 token 和 verCode 字段
      // console.log(newUserinfo);
      // 提取数字部分
      const jifenStr = this.data.awardContent.match(/\d+/);
      const jifen = parseInt(jifenStr);
      // 将提取到的数字赋值给 userinfo.jifen
      userinfo.jifen = parseInt(userinfo.jifen) + jifen;
      // 执行抽奖操作
      var that = this

      post("/user", userinfo).then((res) => {
        if (res.code == 200) {
          wx.setStorageSync('userinfo', userinfo)
          // 更新用户的抽奖记录， 保存到本地存储中
          wx.setStorageSync(`${today}-${userinfo.userid}`, {
            date: `${today}`,
            userid: userinfo.userid
          });
          that.setData({
            jifen: userinfo.jifen
          })
        } else if (res.data.code == -1) {
          wx.showToast({
            title: "请重新登录",
            icon: "none",
            duration: 500
          })
          wx.removeStorageSync('userinfo')
        }
      }).catch((err) => {
        console.log(err);
      })
    }
  },
  // 判断今天是否抽过奖
  checked() {
    const userInfo = wx.getStorageSync('userinfo');
    if (!userInfo) {
      wx.showToast({
        title: '请先登录',
        icon: 'none',
      });
      return false;
    }

    // 检查用户今天是否已经抽过奖
    const today = new Date().toLocaleDateString();
    const userDrawRecord = wx.getStorageSync(`${today}-${userInfo.userid}`); // 使用用户ID作为存储的 key
    if (userDrawRecord && userDrawRecord.date === today && userDrawRecord.userid === userInfo.userid) {
      return false;
    } else {
      this.setData({
        lottery: false
      })
      return true
    }
  },

  onShow: function (e) {
    //刷新抽奖配置
    this.awardsConfig.awards = [{
        'id': '"rate1"',
        'index': 1,
        'name': '10积分'
      },
      {
        'id': '"rate2"',
        'index': 2,
        'name': '20积分'
      },
      {
        'id': '"rate3"',
        'index': 3,
        'name': '30积分'
      },
      {
        'id': '"rate4"',
        'index': 4,
        'name': '40积分'
      },
      {
        'id': '"rate5"',
        'index': 5,
        'name': '50积分'
      },
      {
        'id': '"rate6"',
        'index': 6,
        'name': '60积分'
      }
    ]
    this.drawAwardRoundel()

    //设置抽奖概率
    this.setDrawRate()
  },

  //设置中奖概率
  setDrawRate: function () {
    var drawSetting = this.data.drawSetting
    var drawRateIndexArr = []
    if (drawSetting.rate1 > 0) {
      for (var i = 0; i < drawSetting.rate1; i++) {
        drawRateIndexArr.push(0)
      }
    }

    if (drawSetting.rate2 > 0) {
      for (var i = 0; i < drawSetting.rate2; i++) {
        drawRateIndexArr.push(1)
      }
    }

    if (drawSetting.rate3 > 0) {
      for (var i = 0; i < drawSetting.rate3; i++) {
        drawRateIndexArr.push(2)
      }
    }

    if (drawSetting.rate4 > 0) {
      for (var i = 0; i < drawSetting.rate4; i++) {
        drawRateIndexArr.push(3)
      }
    }

    if (drawSetting.rate5 > 0) {
      for (var i = 0; i < drawSetting.rate5; i++) {
        drawRateIndexArr.push(4)
      }
    }

    if (drawSetting.rate6 > 0) {
      for (var i = 0; i < drawSetting.rate6; i++) {
        drawRateIndexArr.push(5)
      }
    }
    // console.log(drawRateIndexArr)
    this.setData({
      drawRateIndexArr: drawRateIndexArr
    })
  },


  onReady: function (e) {

  },

  //画抽奖圆盘
  drawAwardRoundel: function () {
    var awards = this.awardsConfig.awards;
    var awardsList = [];
    var turnNum = 1 / awards.length; // 文字旋转 turn 值

    // 奖项列表
    for (var i = 0; i < awards.length; i++) {
      awardsList.push({
        turn: i * turnNum + 'turn',
        lineTurn: i * turnNum + turnNum / 2 + 'turn',
        award: awards[i].name
      });
    }

    this.setData({
      btnDisabled: this.awardsConfig.chance ? '' : 'disabled',
      awardsList: awardsList
    });
  },

  //发起抽奖
  playReward: function () {

    //中奖index
    // console.log(this.data.drawRateIndexArr.length)
    var randIndex = util.rand(0, this.data.drawRateIndexArr.length - 1)
    var awardIndex = this.data.drawRateIndexArr[randIndex] //2
    // console.log(awardIndex + " , " + randIndex)
    var runNum = 8; //旋转8周
    var duration = 4000; //时长

    // 旋转角度
    this.runDeg = this.runDeg || 0;
    this.runDeg = this.runDeg + (360 - this.runDeg % 360) + (360 * runNum - awardIndex * (360 / 6))
    //创建动画
    var animationRun = wx.createAnimation({
      duration: duration,
      timingFunction: 'ease'
    })
    animationRun.rotate(this.runDeg).step();
    this.setData({
      animationData: animationRun.export(),
      btnDisabled: 'disabled'
    });

    // 中奖提示
    var awardsConfig = this.awardsConfig;
    setTimeout(function () {
      var awardContent = awardsConfig.awards[awardIndex].name
      wx.showModal({
        title: '恭喜获奖',
        content: '奖品：' + awardContent,
        showCancel: false
      });
      this.setData({
        btnDisabled: '',
        awardContent
      });

    }.bind(this), duration);

    setTimeout(() => {
      this.draw()
    }, duration)
  }

})