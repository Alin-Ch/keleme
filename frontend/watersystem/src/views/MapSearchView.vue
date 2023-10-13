<template>
    <div>
        <div id="container" @click="sendMessage"></div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            // 当前地址坐标为桂林理工大学雁山校区
            weidu: 25.063238,
            jingdu: 110.299288,
            activeNames: [0, 1, 2],
            // 腾讯定位服务 - 安全域名设置，添加https://apis.map.qq.com
            qqmapsdk: null, // 腾讯sdk
            key: 'FIZBZ-H3LWB-UQYUF-JJQ3M-6KD47-W3FAH', // 腾讯key
            type: 'gcj02', //  wgs84: 返回GPS坐标，gcj02: 返回国测局坐标
            address: {}, // 详细地址信息
            searchText: '', // 搜索文本
            searchResult: [], // 搜索结果数组
            info: '', // 详细地址信息
        }
    },
    mounted() {
        //创建地图
        var map = new window.qq.maps.Map(document.getElementById("container"), {
            // 地图的中心地理坐标。
            center: new window.qq.maps.LatLng(this.weidu, this.jingdu),
            // 地图的默认缩放级别。
            zoom: 11,
        });
        //创建标注的位置
        var center = new window.qq.maps.LatLng(this.weidu, this.jingdu);
        //创建标注
        var marker = new window.qq.maps.Marker({
            position: center,
            map: map
        });
        // console.log(marker);
        //绑定单击事件添加参数
        var that = this; // 保存Vue实例的引用
        window.qq.maps.event.addListener(map, 'click', function (event) {
            /* alert('您点击的位置为: [' + event.latLng.getLat() + ', ' +
                event.latLng.getLng() + ']'); */
            var lat = event.latLng.getLat();
            var lng = event.latLng.getLng();
            this.weidu = lat;
            this.jingdu = lng;
            //点击地点后更改图标的位置
            marker.setPosition(new window.qq.maps.LatLng(lat, lng));
            console.log('您点击的位置为: [' + lat + ',' + lng + ']');
            that.getAreaCode(this.weidu, this.jingdu); // 使用that调用getAreaCode方法
            // that.sendMessage(lat, lng); // 使用that调用sendMessage方法
        });
        console.log("拿到的维度和经度是：", this.weidu, this.jingdu);
        // that.searchLocation();
    },
    methods: {
        sendMessage(lat, lng, info) {
            const data = { lat, lng, info };
            this.$emit('send-data', data);
        },
        //地址逆解析获取详细地址
        //维度 经度
        getAreaCode(lat, lng) {
            console.log(lat, lng);
            let that = this;
            //这里可以直接this.$jsonp地址传入你的经纬度;
            that.$jsonp("https://apis.map.qq.com/ws/geocoder/v1/?", {
                location: `${lat},${lng}`, // 经纬度
                key: 'FIZBZ-H3LWB-UQYUF-JJQ3M-6KD47-W3FAH', //这里就是要开启那个service不然会报错让你开启
                output: "jsonp", // output必须jsonp   不然会超时
            }).then((res) => {
                //获取到的res 就是继续的地址的所有信息;
                console.log('res', res);
                console.log('逆解析后获得该坐标的详细地址是', res.result.formatted_addresses.standard_address);
                this.info = res.result.formatted_addresses.standard_address;
                that.sendMessage(lat, lng, this.info); // 将详细地址信息传入sendMessage方法
            });
        },
        // 搜索附近地点
        searchLocation() {
            let that = this;
            let url = `https://apis.map.qq.com/ws/place/v1/search?boundary=nearby(${that.weidu},${that.jingdu},5000)&keyword=${encodeURIComponent(that.searchText)}&page_size=5&page_index=1&key=${that.key}`;
            that.$jsonp(url, {
                output: 'jsonp'
            }).then((res) => {
                // 获取搜索结果数组
                that.searchResult = res.data;
                try {
                    res.data = JSON.parse(JSON.stringify(res.data));
                } catch (e) {
                    // console.log('e',e);
                    res.data = null;
                }
                console.log(that.weidu, that.jingdu);
                console.log('搜索结果数组', res.data);
            });
        }
    }
}
</script>

<style scoped lang="scss">
#container {
    width: 350px;
    height: 350px;
}
</style>