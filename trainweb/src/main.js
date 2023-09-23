import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


// 通过main.js(框架配置入口), 将index.html(html页面入口)和App.Vue(Vue页面入口)关联起来。
// <div id="app"></div>  index.html
createApp(App).use(store).use(router).mount('#app')
