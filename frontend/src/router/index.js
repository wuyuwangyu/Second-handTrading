import Vue from 'vue';
import Router from 'vue-router';


const originalReplace = Router.prototype.replace;
Router.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch(err => err);
};
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
};

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/index'
        },
        {
            path: '/index',
            /*组件懒加载*/
            component: () => import('../components/page/index.vue'),
            meta: { title: '校园跳蚤市场' }
        },
        {
            path: '/search',
            component: () => import('../components/page/search.vue'),
            meta: { title: '闲置搜索 | 校园跳蚤市场' }
        },
        {
            path: '/me',
            component: () => import('../components/page/me.vue'),
            meta: { title: '个人中心 | 校园跳蚤市场' }
        },
        {
            path: '/message',
            component: () => import('../components/page/message.vue'),
            meta: { title: '消息 | 校园跳蚤市场' }
        },
        {
            path: '/release',
            component: () => import('../components/page/release.vue'),
            meta: { title: '发布闲置 | 校园跳蚤市场' }
        },
        {
            path: '/details',
            component: () => import('../components/page/idle-details.vue'),
            meta: { title: '闲置详情 | 校园跳蚤市场' }
        },
        {
            path: '/order',
            component: () => import('../components/page/order.vue'),
            meta: { title: '订单详情 | 校园跳蚤市场' }
        },
        {
            path: '/alipay/pay',
            component: () => import('../components/page/alipay-success.vue'),
            meta: { title: '支付成功 | 校园跳蚤市场' }
        },
        {
            path: '/login',
            component: () => import('../components/page/login.vue'),
            meta: { title: '登录 | 校园跳蚤市场' }
        },
        {
            path: '/sign-in',
            component: () => import('../components/page/sign-in.vue'),
            meta: { title: '注册 | 校园跳蚤市场' }
        },
        {
            path: '/login-admin',
            component: () => import('../components/page/login-admin.vue'),
            meta: { title: '管理员登陆' }
        },
        {
            path: '/platform-admin',
            component: () => import('../components/page/platform-admin.vue'),
            meta: { title: '后台管理' }
        },
        {
            path: '*',
            redirect: '/'
        },
        //     ,{
        //         path: '/details',
        //         name: 'Details',
        //         component: () => import('../components/page/details.vue')
        //     // 配置路由，2025.9.20
        // }
    ],

    // 滚动行为
    // to: 要跳转的页面对象
    // from: 当前页面对象
    // savedPosition: 如果页面有滚动条，savedPosition是记录的滚动条位置
    // 如果没有保存的滚动条位置，savedPosition为null
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return { x: 0, y: 0 }
        }
    }
});
