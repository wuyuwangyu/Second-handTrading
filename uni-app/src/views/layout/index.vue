<template>
  <div class="Layout">
    <router-view></router-view>
    <van-tabbar route active-color="#ee0a24" inactive-color="#000">
      <van-tabbar-item v-if="showTop" @click="top">
        <div class="top-img"><img src="@/assets/top-icon.png" alt=""></div>
      </van-tabbar-item>
      <van-tabbar-item v-else to="/home" @click="refresh">
        <span ref="home">首页</span>
        <template #icon="props">
            <van-icon v-if="props.active" name="wap-home" />
            <van-icon v-else name="wap-home-o" />
          <!-- <img :src="props.active ? icon.active : icon.inactive" /> -->
        </template>
      </van-tabbar-item>
      <van-tabbar-item to="/video">
        <span>逛</span>
        <template #icon="props">
          <van-icon v-if="props.active" name="video" />
          <van-icon v-else name="video-o" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item to="/message">
        <span>消息</span>
        <template #icon="props">
          <van-icon v-if="props.active" name="comment" />
          <van-icon v-else name="comment-o" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item to="/cart">
        <span>购物车</span>
        <template #icon="props">
          <van-icon v-if="props.active" name="shopping-cart" />
          <van-icon v-else name="shopping-cart-o" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item to="/user">
        <span>我的</span>
        <template #icon="props">
          <van-icon v-if="props.active" name="user" />
          <van-icon v-else name="user-o" />
        </template>
      </van-tabbar-item>
      <!-- <van-tabbar-item to="/video" icon="video-o">逛</van-tabbar-item>
      <van-tabbar-item to="/message" icon="comment-o">消息</van-tabbar-item>
      <van-tabbar-item to="/cart" icon="shopping-cart-o">购物车</van-tabbar-item>
      <van-tabbar-item to="/user" icon="user-o">我的</van-tabbar-item> -->
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: 'LayoutIndex',
  data () {
    return {
      icon: {
        active: '',
        inactive: ''
      },
      userInfo: {
        token: '',
        userId: '',
        userName: ''
      },
      showTop: false,
      debounceTimer: null
    }
  },
  created () {
    // 读取本地存储的shopping_demo，验证是否已登录
    this.userInfo = localStorage.getItem('shopping_demo')
  },
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    top () {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      })
    },
    handleScroll () {
      // 判断当前路由是否为home
      // 检查当前路由匹配的所有记录中是否包含 home 路由
      const isHomeRelated = this.$route.matched.some(route => route.path === '/home')
      console.log(isHomeRelated)
      if (!isHomeRelated) {
        return
      }
      // 添加防抖处理
      if (this.debounceTimer) clearTimeout(this.debounceTimer)
      this.debounceTimer = setTimeout(() => {
        const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        const clientHeight = document.documentElement.clientHeight

        // 控制回到顶部按钮显示（当滚动超过一屏时显示）
        this.showTop = scrollTop > clientHeight

        // this.$refs.home.textContent = this.showTop ? '首页' : ''
      }, 200) // 200ms防抖延迟
    },
    refresh () {
      // this.$router.go(0)
      // location.reload()
      // 只在当前不是 /home 时才跳转
      if (this.$route.path !== '/home') {
        this.$router.push('/home')
      } else {
        window.scrollTo({ top: 0, behavior: 'smooth' })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.Layout{
  .top-img{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 30px;
    height: 30px;
    background:#ee0a24;
    border-radius: 30px;
    overflow: hidden;
    img{
      width: 20px;
      height: 20px;
      animation: slideUp 0.5s ease-out forwards; // 添加动画效果
      transform: translateY(100%); // 初始位置在底部外
    }
  }
  @keyframes slideUp {
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
}
}
</style>
