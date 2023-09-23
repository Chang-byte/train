import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import axios from 'axios';
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

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log('请求参数：', config);
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log('返回结果：', response);
    return response;
}, error => {
    console.log('返回错误：', error);
    return Promise.reject(error);
});
// axios.defaults.baseURL = process.env.VUE_APP_SERVER;
axios.defaults.baseURL = 'http://localhost:8000';
console.log('环境：', process.env.NODE_ENV);
console.log('服务端：', process.env.VUE_APP_SERVER);
