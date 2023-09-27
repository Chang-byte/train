import {createRouter, createWebHashHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [{
    path: '/',
    component: () => import('../views/main.vue'),
    children: [{
        path: 'welcome',
        component: () => import('../views/main/welcome.vue'),
    }]
}, {
    path: '',
    redirect: '/welcome'
}];

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
