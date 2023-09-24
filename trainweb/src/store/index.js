import {createStore} from 'vuex'

// 往session中存放东西
const MEMBER = "MEMBER";

// 存储一些全局变量数据。方便页面之间的传递的参数。
// 可以将登录的用户保存到这里，然后全局获取用户的参数。
export default createStore({
    state: {
        // 从session缓存中获取 or 为空对象
        // member: window.SessionStorage.get(MEMBER) || {}
        member: sessionStorage.getItem(MEMBER) || {}
    },
    getters: {},
    mutations: { // 同步
        // 对值的修改
        setMember(state, member) {
            state.member = member
            // 存储到session中
            sessionStorage.setItem(MEMBER, member)
            // window.SessionStorage.set(MEMBER, member);
        }
    },
    actions: {
        // 异步的任务
    },
    modules: {}
})
