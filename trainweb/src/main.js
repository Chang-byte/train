import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
// 引入图标组件
import * as Icons from '@ant-design/icons-vue';

// 通过main.js(框架配置入口), 将index.html(html页面入口)和App.Vue(Vue页面入口)关联起来。
// <div id="app"></div>  index.html
const app = createApp(App)
app.use(store).use(router).use(Antd).mount('#app')

const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i])
}
