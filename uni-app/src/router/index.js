import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginSms from '@/views/login/login-sms.vue'
import Login from '@/views/login'
import Layout from '@/views/layout'
import Myorder from '@/views/myorder'
import Pay from '@/views/pay'
import Search from '@/views/search'
import ProDetail from '@/views/prodetail/index.vue'
import store from '@/store/index'
import { verifyToken } from '@/api/login'

Vue.use(VueRouter)

const router = new VueRouter({
  routes: [
    { path: '/', redirect: '/home' },
    {
      path: '/layout',
      component: Layout,
      children: [
        {
          path: '/home',
          component: () => import('@/views/layout/home.vue'),
          children: [
            { path: '/', redirect: '/hot' },
            { path: '/discount', component: () => import('@/views/layout/home/discount.vue') },
            {
              path: '/hot',
              component: () => import('@/views/layout/home/hot.vue'),
              children: [
                { path: '/', redirect: '/recommend' },
                {
                  path: '/recommend',
                  component: () => import('@/views/layout/home/hot/recommend.vue')
                }
              ]
            },
            { path: '/new', component: () => import('@/views/layout/home/new.vue') }
          ]
        },
        { path: '/video', component: () => import('@/views/layout/video.vue') },
        { path: '/message', component: () => import('@/views/layout/message.vue') },
        { path: '/cart', component: () => import('@/views/layout/cart.vue') },
        { path: '/user', component: () => import('@/views/layout/user.vue') }
      ]
    },
    {
      path: '/category',
      component: () => import('@/views/category'),
      children: [
        { path: '/', redirect: '/tag' },
        { path: '/tag', component: () => import('@/views/category/tag.vue') }
      ]
    },
    { path: '/login', component: Login },
    { path: '/loginsms', component: LoginSms },
    { path: '/myorder', component: Myorder },
    { path: '/pay', component: Pay },
    { path: '/search', component: Search },
    { path: '/list', component: () => import('@/views/search/list.vue') },
    { path: '/prodetail/:id', component: ProDetail }
  ],

  // 滚动行为
  // to: 要跳转的页面对象
  // from: 当前页面对象
  // savedPosition: 如果页面有滚动条，savedPosition是记录的滚动条位置
  // 如果没有保存的滚动条位置，savedPosition为null
  scrollBehavior (to, from, savedPosition) {
    return savedPosition || { x: 0, y: 0 }
  }
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) {
    return originalPush.call(this, location, onResolve, onReject)
  }
  return originalPush.call(this, location).catch(err => {
    if (
      err.name !== 'NavigationDuplicated' &&
      !err.message.includes('Navigation cancelled')
    ) {
      throw err
    }
  })
}

// 全局前置守卫
// to: 要跳转到的页面对象
// from: 当前页面对象
// next: 放行函数，默认放行next()

const authUrls = ['/myorder', '/pay', '/message', '/cart', '/user']

router.beforeEach(async (to, from, next) => {
  console.log('Navigating from:', from.path, 'to:', to.path)

  // 非认证路径直接放行
  if (!authUrls.includes(to.path)) {
    return next()
  }

  // 处理认证路径
  const token = store.getters.token

  // 如果没有token，使用next重定向到登录页
  if (!token) {
    // return router.push('/loginsms')
    return next('/loginsms')
  }

  try {
    const getToken = await verifyToken(token)
    console.log(getToken)
    if (getToken.data.isValid) {
      next()
    } else {
      // 使用next重定向而不是router.push
      next('/loginsms')
    }
  } catch (err) {
    console.error('Token verification error:', err)
    // 验证失败也重定向到登录页
    next('/home')
  }
})

export default router
