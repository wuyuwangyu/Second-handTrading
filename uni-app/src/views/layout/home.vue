<template>
  <div class="home">
    <div class="nav">
      <div class="nav-tabs">
        <van-tabs
        line-width="20px"
        v-model="active"
        >
          <van-tab v-for="(item,index) in navTabs" :key="index" :title="item.title" :to="item.path">
          </van-tab>
        </van-tabs>
      </div>
      <searchPage :top="this.records" @update-top="updateTopItems"></searchPage>
    </div>
    <div class="nav-placeholder"></div>
    <van-pull-refresh
      v-model="isLoading"
      success-text="刷新成功"
      @refresh="onRefresh"
    >
      <div class="category-tabs">
        <van-tabs>
          <van-tab v-for="(item,index) in categoryTabs" :key="index" :title="item.title" :to="item.path">
          </van-tab>
        </van-tabs>
        <div class="category" @click="$router.push('/category')">
          <div class="category-text">分类</div>
        </div>
      </div>
      <router-view></router-view>
    </van-pull-refresh>
  </div>
</template>

<script>
import searchPage from '@/components/SearchCom.vue'
import { getGoodsList } from '@/api/idle'
export default {
  name: 'homePage',
  created () {
    this.getIdelList()
  },
  components: { searchPage },
  data () {
    return {
      active: 1,
      navTabs: [{ title: '特价', icon: 'apps-o', path: '/discount' },
        { title: '首页', icon: 'wap-home-o', path: '/home' },
        { title: '新品', icon: 'shopping-cart-o', path: '/new' }
      ],
      categoryTabs: [{ title: '推荐', icon: 'apps-o', path: '/recommend' },
        { title: '数码', icon: 'wap-home-o', path: '/home' },
        { title: '生活旅行', icon: 'shopping-cart-o', path: '/new' },
        { title: '女装', icon: 'shopping-cart-o', path: '/new' },
        { title: '女鞋', icon: 'shopping-cart-o', path: '/new' },
        { title: '美妆', icon: 'shopping-cart-o', path: '/new' },
        { title: '工业品', icon: 'shopping-cart-o', path: '/new' },
        { title: '家装', icon: 'shopping-cart-o', path: '/new' },
        { title: '钟表眼镜', icon: 'shopping-cart-0', path: '/new' },
        { title: '母婴', icon: 'shopping-cart-o', path: '/new' },
        { title: '运动户外', icon: 'shopping-cart-o', path: '/new' },
        { title: '汽车用品', icon: 'shopping-cart-o', path: '/new' },
        { title: '食品', icon: 'shopping-cart-o', path: '/new' }
      ],
      records: [],
      count: 0,
      isLoading: false
    }
  },
  methods: {
    getIdelList () {
      getGoodsList().then(res => {
        this.records = res.data.records
      })
    },
    updateTopItems () {
      // 将第一个元素移到最后
      if (this.records.length > 1) {
        const firstItem = this.records.shift()
        this.records.push(firstItem)
      }
    },
    onRefresh () {
      setTimeout(() => {
        this.$toast('刷新成功')
        this.isLoading = false
        this.count++
      }, 1000)
    }
  }
}
</script>
<style lang="less" scoped>
.home {
  background: #f5f5f5;
  .nav{
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 999;
    padding-bottom: 5px;
    background: linear-gradient(to bottom, #ff3f4b 50%, rgb(245,245,245) 100%);
  }
  .nav-placeholder{
    height: 101.81px;
    background: rgb(220, 220, 220);
  }
  .nav-tabs{
    :deep(.van-tabs__wrap){
      padding: 0 5px;
    }

    :deep(.van-tabs__nav){
      background: transparent;
    }
    :deep(.van-tabs__line){
      width: 20px!important;
      background: #fff;
    }
    :deep(.van-tab) {
      flex: none;
      font-size: 18px;
      padding: 5px 8px;
      color: rgb(220, 220, 220);
    }
    :deep(.van-tab--active){
      font-size: 20px;
      color: rgb(255, 255, 255);
    }
    :deep(.van-tabs__nav--line)  {
      padding-bottom: 10px;
    }
  }
  .category-tabs{
    position: relative;
    background: transparent;
    :deep(.van-tabs__wrap){
      height: 25px;
    }
    :deep(.van-tabs__line){
      width: 0px!important;
    }
    :deep(.van-tabs__nav){
      display: flex;
      background: rgb(245, 245, 245);
    }
    :deep(.van-tab) {
      display: flex;
      align-items: center;
      font-size: 14px;
      font-weight: 700;
      padding: 5px;
      color:rgb(120, 120, 120);
    }
    :deep(.van-tab--active){
      color:rgb(rgb(0, 0, 0))
    }
    :deep(.van-tabs__nav--line)  {
      padding-bottom: 10px;
    }
    .category{
      display: flex;
      align-items: center;
      position: absolute;
      content: '';
      top: 0;
      right: 0;
      background: rgb(245, 245, 245);
      color:rgb(120, 120, 120);
      height: 25px;
      font-size: 14px;
      font-weight: 700;
      padding: 1px 8px 4px 5px;
      // line-height: 15.8px;
      // padding: 5px;
      .category-text{
        display: flex;
        height: 20px;
      }
      &::after{
        content: '';
        position: absolute;
        top: 2.5px;
        left: -5px;
        width: 10px;
        height: 20px;
        background: rgb(245, 245, 245);
        filter: blur(2px);
      }
    }
  }
}
</style>
