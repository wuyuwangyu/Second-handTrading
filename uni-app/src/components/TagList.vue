<template>
  <div class="tag-list">
    <div class="tag-title">超级趋势</div>
    <div class="tag-content">
      <div
      v-for="item,index in tagList" :key="index"
      class="tag-box">
        <div class="tag-img">
          <img :src="item.tagImg" alt="">
        </div>
        <div class="tag-text">{{item.tagText}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'tagListPage',
  data () {
    return {
      tagList: [
        { tagImg: require('@/assets/tag1.jpg'), tagText: '游戏本' },
        { tagImg: require('@/assets/tag2.jpg'), tagText: '无线充' },
        { tagImg: require('@/assets/tag3.jpg'), tagText: '主机' },
        { tagImg: require('@/assets/tag1.jpg'), tagText: '游戏本' },
        { tagImg: require('@/assets/tag2.jpg'), tagText: '无线充' },
        { tagImg: require('@/assets/tag3.jpg'), tagText: '主机' },
        { tagImg: require('@/assets/tag1.jpg'), tagText: '游戏本' },
        { tagImg: require('@/assets/tag2.jpg'), tagText: '无线充' },
        { tagImg: require('@/assets/tag3.jpg'), tagText: '主机' },
        { tagImg: require('@/assets/tag1.jpg'), tagText: '游戏本' }
      ]
    }
  }
}
</script>

<style scoped>
.tag-list{
  .tag-title{
    display: flex;
    padding: 10px 0 0 10px;
    font-size: 20px;
    font-weight: 700;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    background: #ffffff;
    border-radius: 20px 20px 0 0;
  }
  .tag-content{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    background: #ffffff;
    border-radius: 0 0 20px 20px;
    padding: 0 10px 10px 10px;
    .tag-box{
      display: flex;
      flex-direction: column;
      align-items: center;
      width: 60px; /* 每行显示3个，可根据需要调整 */
      height: 80px;
      margin-top: 10px;
      .tag-img{
        width: 60px;
        height: 60px;
        img{
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
      .tag-text{
        height: 20px;
        font-size: 12px;
        text-align: center;
      }
    }
  }
}
</style>
