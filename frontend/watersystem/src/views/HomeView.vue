<template>
  <div>
    <div style="font-size: 18px; font-family: 宋体;">欢迎进入渴了么订水后台系统</div>

    <div class="container">
      <!-- 绘制饼图 -->
      <div style="width: 50%; margin-top: 20px;">
        <div id="bie" style="width: 100%; height: 400px;"></div>
      </div>

      <!-- 绘制柱状图 -->
      <div style="width: 50%; margin-top: 20px;">z
        <div id="bar" style="width: 100%; height: 400px;"></div>
      </div>
    </div>

    <!-- 绘制折线图 -->
    <div style="width: 50%; margin-top: 20px;">
        <div id="line" style="width: 100%; height: 400px;"></div>
      </div>
  </div>
</template>
<script>
import request from '@/utils/request';
import * as echarts from 'echarts';
export default {
  name: 'HomeView',
  data() {
    return {
      data: [],
    }
  },
  //页面初始化的时候调用
  mounted() {
    this.initEcharts();
    this.initBar();
  },
  methods: {
    initEcharts() {
      request.get('/good/echarts/bie').then(res => {
        if (res.code === '200') {
          //开始渲染饼图数据
          this.initBie(res.data);
          console.log(res.data);
        } else {
          this.$message.error(res.msg);
        }
      })
      request.get('/good/echarts/bar').then(res => {
        if (res.code === '200') {
          //开始渲染柱状图数据
          let map = res.data;
          this.initBar(map.xAxis, map.yAxis);
          this.initLine(map.xAxis, map.yAxis);
          console.log(res.data);
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    // 绘制饼图
    initBie(data) {
      let chartDom = document.getElementById('bie');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '商品统计（饼图）',
          subtext: '统计纬度：商品分类',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '商品分类',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    //绘制柱状图
    initBar(xAxis, yAxis) {
      let chartDom = document.getElementById('bar');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '商品统计（柱状图）',
          subtext: '统计纬度：商品分类',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: xAxis
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      };

      option && myChart.setOption(option);
    },
    //绘制折线图
    initLine(xAxis, yAxis) {
      let chartDom = document.getElementById('line');
      let myChart = echarts.init(chartDom);
      let option;

      option = {
        title: {
          text: '商品统计（折线图）',
          subtext: '统计纬度：商品分类',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: xAxis,
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: yAxis,
            type: 'line'
          }
        ]
      };

      option && myChart.setOption(option);
    },
  }
}
</script>

<style>
.container {
  width: 100%;
  height: 100%;
  display: flex;

}
</style>