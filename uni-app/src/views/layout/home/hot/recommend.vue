<template>
  <div class="recommend">
    <goods-item
    v-for="item in records" :key="item.id"
    :idle="item"
    ></goods-item>
    <!-- 回到顶部 按钮 -->
    <!-- <div class="top" @click="top" v-show="showTop"><img src="@/assets/top.png" alt=""></div> -->
  </div>
</template>

<script>
import { getGoodsList } from '@/api/idle'
import GoodsItem from '@/components/GoodsItem.vue'
export default {
  name: 'recommendPage',
  components: { GoodsItem },
  created () {
    this.getGoodsList()
  },

  data () {
    return {
      records: [],
      currentPage: 1,
      pageSize: 10,
      hasMore: true,
      Loading: false,
      debounceTimer: null
      // showTop: false // 控制回到顶部按钮的显示
    }
  },
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    async getGoodsList (isLoadMore = false) {
      if (!isLoadMore && !this.hasMore) return
      this.loading = true
      try {
        // 确定当前页码
        const currentPage = isLoadMore ? this.currentPage + 1 : 1
        console.log(currentPage)

        const res = await getGoodsList({
          page: currentPage,
          size: this.pageSize
        })

        if (isLoadMore) {
          // 合并新数据前去重
          const newRecords = res.data.records.filter(newItem =>
            !this.records.some(existingItem => existingItem.id === newItem.id)
          )
          this.records = [...this.records, ...newRecords]
          this.currentPage = currentPage
        } else {
          this.records = res.data.records
          this.currentPage = 1
        }
        console.log(this.records)
        // 判断是否还有更多数据
        this.hasMore = res.data.records.length === this.pageSize
      } catch (err) {
        console.error(err)
        if (isLoadMore) this.currentPage-- // 加载失败回退页码
      } finally {
        this.loading = false
      }
    },
    handleScroll () {
    // 添加防抖处理
      if (this.debounceTimer) clearTimeout(this.debounceTimer)
      this.debounceTimer = setTimeout(() => {
        const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
        const clientHeight = document.documentElement.clientHeight
        const scrollHeight = document.documentElement.scrollHeight

        // // 控制回到顶部按钮显示（当滚动超过一屏时显示）
        // this.showTop = scrollTop > clientHeight

        // 当距离底部小于一定高度时触发加载
        if (scrollTop + clientHeight >= scrollHeight - 50 && this.hasMore && !this.loading) {
          this.getGoodsList(true) // 触发加载更多
        }
      }, 200) // 200ms防抖延迟
    }
    // top () {
    //   window.scrollTo({
    //     top: 0,
    //     behavior: 'auto'
    //   })
    // }
  }
}
</script>

<style lang="less" scoped>
.recommend{
  display: flex;
  flex-wrap: wrap; /* 允许换行 */
  padding: 0 5px 5px 5px;
  justify-content: space-between;
  margin-bottom: 50px;
  .top{
    position: fixed;
    bottom: 100px;
    right: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 30px;
    height: 30px;
    border-radius: 30px;
    background: rgba(255, 255, 255,.8);
    z-index: 999;
    overflow: hidden;
    img{
      width: 20px;
      height: 20px;
    }
  }
}
</style>
