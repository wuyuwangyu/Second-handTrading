<template>
  <div class="catgory">
    <van-nav-bar title="分类" left-text="" left-arrow
    placeholder
    @click-left="onClickLeft"
    @click-right="onClickRight">
      <template #right>
        <van-icon name="search" size="18" />
      </template>
    </van-nav-bar>
    <van-tree-select height="100vh" :items="items" :main-active-index.sync="active">
      <template #content>
        <div class="category-tag">
          <router-view></router-view>
        </div>
      </template>
    </van-tree-select>
  </div>
</template>

<script>
export default {
  name: 'categoryPage',
  data () {
    return {
      active: 0,
      items: [
        { text: '电脑办公' },
        { text: '手机' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' },
        { text: '数码' },
        { text: '手机' },
        { text: '电脑办公' }
      ]
    }
  },
  methods: {
    onClickLeft () {
      this.$router.push({
        path: '/'
      })
    },
    onClickRight () {
      this.$router.push({
        path: '/search'
      })
    }
  }
}
</script>
<style lang="less" scoped>
// 针对 Webkit 浏览器 (Chrome, Safari)
.van-tree-select :deep(.van-tree-select__nav::-webkit-scrollbar) {
  display: none;
}
// 针对 Firefox
.van-tree-select :deep(.van-tree-select__nav) {
  scrollbar-width: none;
}
// 针对 IE/Edge
.van-tree-select :deep(.van-tree-select__nav) {
  -ms-overflow-style: none;
}
.catgory{
  // :deep(.van-sidebar){
  // }
  :deep(.van-sidebar-item){
    width: 120px;
    height: 50px;
  }
  :deep(.van-sidebar-item--select){
    font-size: 18px;
    color: #ee0a24;
    background: #f7f8fa;
  }
  :deep(.van-sidebar-item--select::before) {
    content: '';
    width: 0;
    height: 0;
  }
}
</style>
