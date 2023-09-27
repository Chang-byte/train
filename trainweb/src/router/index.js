import {createRouter, createWebHistory} from 'vue-router'
import Main from "@/views/main";
import store from '../store'
import {notification} from "ant-design-vue";
//存储路由跳转的信息。
const routes = [
    // { // 组件
    //     path: '/',
    //     name: 'home',
    //     component: HomeView
    // },
    // { // 懒加载: 大型项目，页面多，80%的页面不常用，可以用懒加载的方式，
    //     // 减少编译后文件的大小，提高初始访问速度。
    //     // 小项目，页面少，可以用静态导入的方式，对编译后的文件大小影像不大。
    //     path: '/about',
    //     name: 'about',
    //     component: () => import( '../views/AboutView.vue')
    // },

    {
        path: '/login',
        name: 'login',
        component: () => import( '../views/login.vue')
    },
    {
        path: '/',
        name: 'main',
        component: Main,
        meta: { // 自定义元数据
            loginRequire: true
        },
        children: [{
            path: 'welcome',
            component: () => import('../views/main/welcome.vue'),
        }, {
            path: 'passenger',
            component: () => import('../views/main/passenger.vue'),
        }]
    }
]

/**
 * axios 是与后端交互的拦截器。向后端发送请求携带token，或者判断响应。
 * 只有登录后才会有token，然后每次发送请求都携带token。
 * 要与后端进行配合。
 * 如果没有token，就在gateway那里就拦截掉了。
 * 路由拦截是为了防止用户在没有登录的情况下，直接通过路径url，就可以访问到页面。
 * 所以在每次路由跳转的时候，都要判断一下，当前用户是否登录。
 * 我们可以使用元数据loginRequire来判断。
 */
const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// 路由登录拦截url 发生变化的时候做一个拦截。是否需要登录。
router.beforeEach((to, from, next) => {
    // 要对meta.loginRequire属性做监控拦截，所有的请求都会console.log() 日志。
    // 但是只有返回true, 才走下面的拦截。
    // eg: {path: '/login', redirect: undefined, name: 'login', meta: {…}, aliasOf: undefined, …} '是否需要登录校验：' false
    if (to.matched.some(function (item) {
        // item就是每个的路由的选项，就是上面我们定义的routes的选项。
        console.log(item, "是否需要登录校验：", item.meta.loginRequire || false);
        return item.meta.loginRequire
    })) {
        // 上面返回true，走下面的拦截。
        const member = store.state.member;
        console.log("页面登录校验开始：", member);
        if (!member.token) {
            console.log("用户未登录或登录超时！");
            notification.error({description: "未登录或登录超时"});
            // 直接跳转到登录页面。
            next('/login');
        } else {
            // 上面返回false，放行。
            next();
        }
    } else {
        next();
    }
});

export default router
