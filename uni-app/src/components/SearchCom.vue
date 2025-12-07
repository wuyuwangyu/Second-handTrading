<template>
  <div class="search">
    <div class="search-box">
      <div class="search-Scan">
        <img src="@/assets/icon-scan.png" alt="">
      </div>
      <div class="searchbg">
        <div class="item"
        v-for="(item,index) in top" :key="index"
        :class="{first: index === 0 && !showFirst}">
          {{item.idleName}}
        </div>
      </div>
      <div class="search-input" @click="sendInput()">
        <input type="text" name="" id="">
      </div>
      <div class="search-photograph">
        <img src="@/assets/icon-photograph.png" alt="">
      </div>
      <div class="search-space">|</div>
      <div class="search-btn" @click="searchBtn">搜索</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchPage',
  data () {
    return {
      timer: null,
      firtTime: null,
      showFirst: true,
      inputContent: ''
    }
  },
  computed: {
    // 创建一个计算属性，用于判断是否显示第一个元素
    firstContent () {
      return this.top && this.top.length > 0 ? this.top[0].idleName : ''
    }
  },
  props: {
    top: {
      type: Array,
      default () {
        return []
      }
    }
  },
  mounted () {
    // 组件挂载后启动轮播
    this.startCarousel()
  },
  beforeDestroy () {
    // 组件销毁前清除定时器
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  methods: {
    startCarousel () {
      this.timer = setInterval(() => {
        // 触发父组件更新数据，将第一个元素移到最后
        this.showFirst = !this.showFirst
        setTimeout(() => {
          this.$emit('update-top')
          // 重置状态为默认值，为下次动画做准备
          this.showFirst = true
          this.inputContent = this.top[0].idleName
        }, 500) // 这个时间应该与CSS过渡时间一致
      }, 3000)
    },
    sendInput () {
      this.$router.push({ path: '/search?placeholder=' + this.firstContent })
    },
    searchBtn () {
      this.$router.push({ path: '/list?keyword=' + this.firstContent })
    }
  }
}
</script>

<style lang="less" scoped>
.search{
  padding: 0 5px;
  .search-box{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 48px;
    border: 2px solid rgb(255, 63, 75);
    border-radius: 8px;
    margin-top: 5px;
    background-color: rgb(255, 255, 255);
    .search-Scan{
      width: 24px;
      height: 24px;
      margin: 0 5px 0 10px;
      img{
        width: 100%;
        height: 100%;
      }
    }
    .searchbg{
      position: absolute;
      top: 59px;
      left: 0;
      width: 100%;
      height: 32px;
      overflow: hidden;
       pointer-events: none; /* 添加这一行 */
      .item{
        font-size: 16px;
        padding: 0 100px 0 55px;
        height: 32px;
        line-height: 32px;
        text-align: left;
        overflow: hidden;
      }
      .first{
        margin-top: -32px;
        transition: margin-top 0.5s ease;
      }
    }
    .search-input{
      flex: 1;
      input{
        width: 100%;
        height: 32px;
        border: 0px;
        background-color:transparent;
        font-size: 16px;
        padding-left: 5px;
      }
    }
    .search-photograph{
      display: flex;
      justify-content: center;
      width: 24px;
      height: 24px;
      // margin: 0 10px;
      .img{
        width: 100%;
        height: 100%;
      }
    }
    .search-space{
      display: flex;
      justify-content: center;
      align-items: center;
      width: 10px;
      height: 100%;
      font-size: 16px;
      padding: 0 10px;
      color: rgba(0, 0, 0, .2);
    }
    .search-btn{
      display: flex;
      justify-content: center;
      align-items: center;
      color: rgb(255, 63, 75);
      font-size: 18px;
      font-weight: 600;
      margin-right: 5px;
    }
  }
}
</style>
