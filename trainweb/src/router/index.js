import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

//存储路由跳转的信息。
const routes = [
    { // 组件
        path: '/',
        name: 'home',
        component: HomeView
    },
    { // 懒加载: 大型项目，页面多，80%的页面不常用，可以用懒加载的方式，
        // 减少编译后文件的大小，提高初始访问速度。
        // 小项目，页面少，可以用静态导入的方式，对编译后的文件大小影像不大。
        path: '/about',
        name: 'about',
        component: () => import( '../views/AboutView.vue')
    },

    {
        path: '/login',
        name: 'login',
        component: () => import( '../views/login.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
