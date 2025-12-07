<template>
  <div class="list">
    <div class="search">
      <div class="search-input">
        <div class="icon-back">
          <van-icon name="arrow-left" @click="searchDel()" />
        </div>
        <div class="input" @click="searchList()">
          <!-- <input v-model="inputContent" type="text" /> -->
          <span class="search-tag">{{this.inputContent}}
          <van-icon
          name="cross"
          class="delete-icon"
          @click="searchTagDel()" /></span>
          <div class="search-photograph">
            <img src="@/assets/icon-photograph.png" alt="">
          </div>
        </div>
        <div class="class-btn">
          <img src="@/assets/class.png" alt="">
        </div>
      </div>
    </div>
    <div class="idle-list">
      <IdleList
      v-for="item in records" :key="item.id"
      :item="item"
      ></IdleList>
    </div>
  </div>
</template>

<script>
import { getSearchList } from '@/api/idle'
import IdleList from '@/components/IdleList.vue'
export default {
  name: 'ListIndex',
  components: { IdleList },
  created () {
    this.inputContent = this.$route.query.keyword
    console.log('搜索关键词:', this.inputContent) // 添加日志确认
    this.getSearchList()
  },
  data () {
    return {
      inputContent: '',
      records: [],
      loading: false,
      debounceTimer: null,
      currentPage: 1, // 添加当前页码
      pageSize: 10, // 添加每页大小
      hasMore: true // 添加是否有更多数据标志
    }
  },
  mounted () {
    window.addEventListener('scroll', this.handleScroll)
  },
  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    searchList () {
      this.$router.push({
        path: '/search',
        query: {
          keyword: this.inputContent
        }
      })
    },
    searchDel () {
      // this.$router.push({
      //   path: '/search',
      //   query: {
      //     keyword: this.inputContent,
      //     addToHistory: true
      //   }
      // })
      this.$router.go(-1)
    },
    searchTagDel () {
      this.$router.push({
        path: '/search',
        query: {
          // keyword: this.inputContent,
          // addToHistory: true
        }
      })
    },
    async getSearchList (isLoadMore = false) {
      if (!isLoadMore && !this.hasMore) return
      this.loading = true
      try {
        // 确定当前页码
        const currentPage = isLoadMore ? this.currentPage + 1 : 1
        console.log(currentPage)

        const res = await getSearchList({
          keyword: this.inputContent,
          page: currentPage,
          size: this.pageSize
        })

        console.log('API返回结果:', res) // 检查返回的数据结构

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
        // 当距离底部小于一定高度时触发加载
        if (scrollTop + clientHeight >= scrollHeight - 50 && this.hasMore && !this.loading) {
          this.getSearchList(true) // 触发加载更多
        }
      }, 200) // 200ms防抖延迟
    }
  }
}
</script>

<style lang="less" scoped>
.search {
  background: #f5f5f5;
  .search-input {
    display: flex;
    align-items: center;
    padding: 20px 15px 10px 10px;
    .icon-back{
      display: flex;
      justify-content: center;
      align-items: center;
      height: 30px;
      :deep(.van-icon){
        font-size: 22px;
        margin-right: 5px;
      }
    }
    .input {
      display: flex;
      flex: 1;
      align-items: center;
      justify-content: space-between;
      border-radius: 8px;
      border: 1px solid rgb(255, 63, 75);
      input {
        width: 100%;
        height: 30px;
        font-size: 14px;
        padding-left: 10px;
        border: 0;
        background: rgb(255, 255, 255);
      }
      .search-tag{
        display: flex;
        height: 24px;
        align-items: center;
        margin-left: 5px;
        padding: 5px 10px;
        border-radius: 5px;
        background: rgb(233, 233, 233);
        font-size: 12px;
        .delete-icon{
          padding-left: 5px;
          font-size: 10px;
          color: rgb(190, 190, 190);
        }
      }
      .search-photograph {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 30px;
        height: 30px;
        padding-right: 10px;
        img {
          width: 20px;
        }
      }
    }
    .class-btn{
      display: flex;
      justify-content: center;
      align-items: center;
      padding-left: 20px;
      img{
        width: 20px;
        height: 20px;
      }
    }
  }
}
</style>
