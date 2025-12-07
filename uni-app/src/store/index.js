import Vue from 'vue'
import Vuex from 'vuex'
import user from '@/store/module/user'
import cart from '@/store/module/cart'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
    token (state) {
      return state.user.userInfo.token
    }
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user,
    cart
  }
})
