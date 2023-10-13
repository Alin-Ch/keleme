import Dialog from '@vant/weapp/dialog/dialog';
Page({

  data: {
    score: 200,
  },


  onLoad(options) {},

  gift() {
    Dialog.alert({
      title: '我的礼品',
      message: '1.哇哈哈纯净水      2023-09-06 16:16',
    }).then(() => {
      // on close
    });

  },

  // 兑换
  change(e) {
    var id = e.currentTarget.dataset.id
    console.log(id);
  },
})