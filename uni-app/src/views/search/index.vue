<template>
  <div class="search">
    <div class="search-input">
      <div class="icon-back">
        <van-icon name="arrow-left" @click="searchDel()" />
      </div>
      <div class="input">
        <input v-focus ref="input" v-model="inputContent" type="text" :placeholder="this.$route.query.placeholder || historyrelevance[0]" />
        <div class="search-photograph">
          <img src="@/assets/icon-photograph.png" alt="">
        </div>
        <div class="input-btn">
          <div class="btn" @click="searchBtn">搜索</div>
        </div>
      </div>
    </div>
    <div class="search-content">
      <div class="search-history">
        <div class="history-title">搜索历史</div>
        <div class="del-icon"
        v-if="!isDel"
        @click="expandDel">
          <img src="@/assets/del-btn.png" alt="">
        </div>
        <div v-else class="del-operate" >
          <span class="del-text" @click="deleteAllItesm">全部删除</span>
          <span class="space">|</span>
          <span class="del-text" style="color: rgb(255, 63, 75);" @click="expandDel">完成</span>
        </div>
      </div>
      <div class="history-card" :class="{ 'collapsed': isCollapsed }" ref="historyCard">
        <span @click="itemBtn(item,index)" class="history-text"
        v-for="(item, index) in historyItems" :key="index">
        {{ item }}
        <van-icon
          v-if="isDel"
          name="cross"
          class="delete-icon" />
        </span>
        <span class="expand-hide-btn" @click="expand" v-show="showExpandButton" :class="{ 'collapsed': !isCollapsed }">
          <van-icon v-if="this.isCollapsed" name="arrow-up" />
          <van-icon v-else name="arrow-down" />
        </span>
      </div>
      <div class="search-find" v-show="showFind">
        <div class="search-history">
          <div class="history-title">搜索发现</div>
          <div class="del-icon" @click="expandFind">
            <img v-if="!showFindButton" src="@/assets/show.png" alt="">
            <div v-else class="hidden">已隐藏<img src="@/assets/hidden.png" alt=""></div>
          </div>
        </div>
        <div class="find-card" v-show="!showFindButton">
          <span @click="itemBtn(item,index)" class="find-text"
          v-for="(item, index) in historyrelevance" :key="index">
          {{ item }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getInfo, getHistoryInfo, setHistoryInfo } from '@/utils/storage'

export default {
  name: 'SearchIndex',
  data () {
    return {
      inputContent: '',
      isCollapsed: false, // 控制是否历史记录折叠
      previousIsCollapsed: false, // 添加这一行来保存isCollapsed之前的状态
      showExpandButton: false, // 通过实际高度来控制展开和收起按钮
      isDel: false, // 控制删除按钮的显示
      showFind: true, // 默认显示 控制搜索发现
      showFindButton: false, // 控制搜索发现按钮的显示
      userInfo: getInfo(), // 获取用户信息
      historyItems: getHistoryInfo(getInfo().userId), // 默认历史记录
      historyrelevance: [
        'iPhone 15 Pro Max',
        '华为Mate60 Pro',
        '小米14 Ultra',
        'MacBook Air M2',
        '联想ThinkPad X1',
        '华硕ROG魔霸新锐',
        'AirPods Pro 2',
        '索尼WH-1000XM5',
        ' Beats Studio Pro',
        'Apple Watch Series 9',
        '华为Watch GT 4',
        '小米手环8'
      ] // 默认搜索推荐
    }
  },
  created () {
    console.log(this.$route.query)
    console.log('User Info:', this.userInfo)
    console.log('User ID:', this.userInfo.userId)
    console.log('History Key:', this.userInfo.userId ? `history_demo_${this.userInfo.userId}` : 'history_demo_guest')
    // 获取用户信息
    this.userInfo = getInfo()
    // 使用用户ID初始化历史记录
    this.historyItems = getHistoryInfo(this.userInfo.userId || '')
    if (this.$route.query.keyword) {
      this.inputContent = this.$route.query.keyword
    }
    // 处理从列表页返回时添加搜索历史
    if (this.$route.query.addToHistory && this.$route.query.keyword) {
      this.addToHistory(this.$route.query.keyword)
      // 清除路由参数避免重复添加
      this.$router.replace({
        path: '/search',
        query: {
          keyword: this.$route.query.keyword
        }
      })
    }
  },
  watch: {
    historyItems: {
      handler (newVal) {
        if (this.userInfo.userId) {
        // 正确传递 userId 和 newVal 参数
          setHistoryInfo(this.userInfo.userId, newVal)
        } else {
        // 对于未登录用户也应保存历史记录
          setHistoryInfo('', newVal)
        }
      },
      deep: true // 深度监听数组内部元素变化
    }
  },
  mounted () {
    this.checkOverflow()
  },
  updated () {
    this.$nextTick(() => {
      this.checkOverflow() // 数据更新后检测
    })
  },
  methods: {
    expand () {
      this.isCollapsed = !this.isCollapsed
    },
    expandDel () {
      this.isDel = !this.isDel
      if (this.isDel) {
        // 进入删除模式时，展开历史记录并隐藏搜索发现
        this.previousIsCollapsed = this.isCollapsed
        this.isCollapsed = true
        this.showFind = false
        this.showExpandButton = false
      } else {
        // 退出删除模式时，恢复原始状态
        this.showFind = true
        this.isCollapsed = this.previousIsCollapsed
        this.$nextTick(() => {
          this.checkOverflow()
        }) // 重新检查是否需要展开按钮
      }
    },
    expandFind () {
      this.showFindButton = !this.showFindButton
    },
    checkOverflow () {
      const card = this.$refs.historyCard
      if (card && !this.isDel) {
        // 添加调试信息
        console.log('scrollHeight:', card.scrollHeight)
        // 比较实际高度判断是否溢出
        this.showExpandButton = card.scrollHeight > 65
      }
    },
    searchBtn () {
      // 获取输入的内容
      if (!this.inputContent.trim()) {
        // 如果输入为空，使用占位符内容
        if (this.$route.query.placeholder) {
          this.itemBtn(this.$route.query.placeholder)
        } else {
          this.$toast('请输入搜索关键词')
        }
      } else {
        // 如果有输入内容，则使用输入的内容进行搜索
        this.itemBtn(this.inputContent)
      }
    },
    itemBtn (item, index) {
      if (this.isDel) {
        this.deleteHistoryItem(index)
        return
      }
      const i = this.historyItems.indexOf(item)
      if (i !== -1) {
        this.historyItems.splice(i, 1)
      }
      this.historyItems.unshift(item)
      // 强制触发更新（可选）
      this.$set(this.historyItems, [...this.historyItems])
      this.$nextTick(() => {
        this.$router.push({ path: '/list', query: { keyword: item } })
      })
    },
    deleteHistoryItem (index) {
      this.historyItems.splice(index, 1)
    },
    deleteAllItesm () {
      this.historyItems = []
    },
    searchDel () {
      // this.$router.push({
      //   path: '/recommend'
      // })
      this.$router.go(-1)
    },
    addToHistory (keyword) {
      const i = this.historyItems.indexOf(keyword)
      if (i !== -1) {
        this.historyItems.splice(i, 1)
      }
      this.historyItems.unshift(keyword)
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
        line-height: 30px;
        font-size: 14px;
        padding-left: 10px;
        border: 0;
        background: transparent;
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
      .input-btn{
        display: flex;
        justify-content: end;
        align-items: center;
        height: 30px;
        padding: 2px;
        .btn{
          width: 60px;
          height: 100%;
          border-radius: 6px;
          color: rgb(255, 255, 255);
          background: rgb(255, 63, 75);
        }
      }
    }
  }
  .search-content{
    padding: 5px 10px;
    background: rgb(255, 255, 255);
    border-radius: 10px 10px 0 0;
    .search-history{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 5px;
      .history-title{
        font-size: 12px;
        font-weight: 700;
      }
      .del-icon{
        display: flex;
        justify-content: center;
        align-items: center;
        // width: 15px;
        height: 15px;
        img{
          width: 100%;
          height: 100%;
        }
        .hidden{
          display: flex;
          align-items: center;
          justify-content: space-between;
          width: 50px;
          font-size: 10px;
          color: rgb(150, 150, 150);
          img{
            width: 15px;
            height: 100%;
          }
        }
      }
      .del-operate{
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 12px;
        color: rgb(150, 150, 150);
        .space{
          font-size: 10px;
          margin: 0 5px;
        }
      }
    }
    .history-card{
      position: relative;
      margin-top: 5px;
      display: flex;
      padding-right: 35px;
      flex-wrap: wrap; /* 允许换行 */
      gap: 5px; /* 设置元素间距 */
      max-height: 65px;
      overflow:hidden;
      align-content: flex-start; /* 确保内容从顶部开始排列 */
      &.collapsed {
        max-height: none; /* 展开时取消高度限制 */
      }
      .history-text{
        display: flex;
        height: 25px;
        align-items: center;
        padding: 5px 10px;
        border-radius: 5px;
        background: rgb(245, 245, 245);
        font-size: 12px;
        margin-bottom: 5px;
        .delete-icon{
          padding-left: 5px;
          font-size: 10px;
          color: rgb(190, 190, 190);
        }
      }
      .expand-hide-btn{
        display: flex;
        height: 25px;
        padding: 5px;
        border-radius: 5px;
        background: rgb(245, 245, 245);
        font-size: 12px;
        :deep(.van-icon){
          font-size: 14px;
        }
        &.collapsed {
          position: absolute;
          right: 5px;
          top: 35px;
        }
      }
    }
    .search-find{
      margin-top: 10px;
      .find-card{
        display: flex;
        flex-wrap: wrap;
        .find-text{
          display: flex;
          align-items: center;
          width: 170px;
          height: 20px;
          margin: 5px 0;
          font-size: 12px;
          white-space: nowrap;      // 不换行
          overflow: hidden;         // 超出部分隐藏
          text-overflow: ellipsis;  // 超出部分显示省略号
        }
      }
    }
  }
}
</style>
