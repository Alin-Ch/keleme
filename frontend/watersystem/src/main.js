import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {VueJsonp} from 'vue-jsonp'

Vue.use(VueJsonp)

import '@/assets/global.css'

Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});//使用element-ui

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
