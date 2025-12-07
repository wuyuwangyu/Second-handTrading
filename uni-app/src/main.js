import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/utils/vant-ui'
import '@/style/common.less'
import '@/assets/icomoon/style.css'

Vue.config.productionTip = false

// 自定义指令input获取焦点
Vue.directive('focus', {
  inserted (el) {
    el.focus()
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
