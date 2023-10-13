import Dialog from '@vant/weapp/dialog/dialog';
Page({
    data: {
        showShare: false,
        options: [
            [{
                    name: '微信',
                    icon: 'wechat'
                },
                {
                    name: '微博',
                    icon: 'weibo'
                },
                {
                    name: 'QQ',
                    icon: 'qq'
                },
            ],
            [{
                    name: '复制链接',
                    icon: 'link'
                },
                {
                    name: '分享海报',
                    icon: 'poster'
                },
                {
                    name: '二维码',
                    icon: 'qrcode'
                },
            ],
        ],
        money: 50,
        people: 5,
        user: {
            username: "胡歌",
            phone: "15456406540",
            date: "2023-09-99",
        },
    },
    onLoad() {
        this.onShareButtonTap()
    },

    // 返利
    payback() {
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

    // 分享功能
    share() {
        this.setData({
            showShare: true
        })
    },
    onClose() {
        this.setData({
            showShare: false
        });
    },
    onSelect(event) {
        this.onShareButtonTap()
        this.onClose();
    },

    onShareButtonTap: function () {
        wx.showShareMenu({
            withShareTicket: true,
            menus: ['shareAppMessage', 'shareTimeline']
        });
    },
    onShareAppMessage: function () {
        return {
            title: '渴了么欢迎您的使用~~',
            path: '/pages/index/index',
            imageUrl: '/images/huge.jpg'
        };
    },

});