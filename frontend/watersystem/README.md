### 安装element-ui组件

```shell
npm install element-ui -S
```

### 在 main.js 中写入以下内容

```she
import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.use(ElementUI);

new Vue({
  el: '#app',
  render: h => h(App)
});
```

