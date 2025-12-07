<template>
  <div class="idle-list" @click="goToDetail(item.id)">
    <div class="idle-img">
      <img :src="item.pictureList[0] || require('@/assets/404.png')" alt="">
    </div>
    <div class="idle-content">
      <div class="idle-title">{{item.idleName}}</div>
      <div class="idle-details">{{item.idleDetails}}</div>
      <div class="idle-config">
        <div class="config">
          <div class="config-content">16GB</div>
          <div class="config-tag">内存容量</div>
        </div>
        <div class="config">
          <div class="config-content">RTX 5050</div>
          <div class="config-tag">显卡</div>
        </div>
        <div class="config">
          <div class="config-content">锐龙200系列</div>
          <div class="config-tag">处理器</div>
        </div>
      </div>
      <div class="idle-price">
        <div class="price">
          <span class="price-icon">￥</span>
          {{item.idlePrice}}
          <span class="price-tag"></span>
        </div>
        <div class="add-button" @click="getCart"></div>
      </div>
      <div class="idle-tag"></div>
      <div class="idle-sell">已售{{item.idleSellCount}}</div>
      <div class="idle-place" @click="getShop">
        {{item.idlePlace}}
        <span class="idle-place-tag">进店<van-icon name="arrow" /></span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'IdleList',
  props: {
    item: {
      type: Object,
      default () {
        return {}
      }
    }
  },
  data () {
    return {
      pictureList: [],
      idleName: '',
      idleDetails: '',
      idlePrice: 0,
      idleSellCount: 0,
      idlePlace: ''
    }
  },
  methods: {
    getCart (event) {
      event.stopPropagation()
      console.log('getCart')
    },
    getShop () {
      console.log('getShop')
    },
    goToDetail (id) {
      // 跳转到商品详情页
      this.$router.push(`/prodetail/${id}`)
      console.log('跳转到商品详情页')
    }
  }
}
</script>

<style lang="less" scoped>
.idle-list{
  display: flex;
  padding: 10px 15px;
  .idle-img{
    width: 100px;
    height: 100px;
    img{
      width: 100%;
      height: 100%;
      object-fit: cover;
      border-radius: 5px;
    }
  }
  .idle-content{
    display: flex;
    flex: 1;
    width: 235px;
    height: 100%;
    flex-direction: column;
    margin-left: 10px;
    .idle-title{
      display: flex;
      height: 20px;
      font-size: 14px;
      font-weight: 600;
      color: rgb(51, 51, 51);
    }
    .idle-details{
      height: 20px;
      font-size: 12px;
      text-align: left;
      color: rgb(153, 153, 153);
      white-space: nowrap;       // 防止文本换行
      overflow: hidden;          // 超出部分隐藏
      text-overflow: ellipsis;   // 显示省略号
    }
    .idle-config{
      display: flex;
      height: 30px;
      .config{
        flex: 1;
        display: flex;
        justify-content: center;
        flex-direction: column;
        .config-content{
          height: 15px;
          line-height: 15px;
          font-size: 10px;
          color: rgb(0, 0, 0);
        }
        .config-tag{
          height: 15px;
          line-height: 15px;
          font-size: 10px;
          color: rgb(153, 153, 153);
        }
      }
    }
    .idle-price{
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 20px;
      .price{
        font-size: 16px;
        font-weight: 700;
        color: rgb(255, 63, 75);
        .price-icon{
          font-size: 10px;
        }
      }
      .add-button {
        display: flex;
        bottom: 4px;
        right: 4px;
        width: 20px;
        height: 20px;
        background: url(../assets/icon-add.png) no-repeat;
        background-size: cover;
        z-index:1;
      }
    }
    .idle-sell{
      height: 15px;
      font-size: 10px;
      text-align: left;
      color: rgb(153, 153, 153);
    }
    .idle-place{
      height: 15px;
      font-size: 10px;
      text-align: left;
      color: rgb(153, 153, 153);
      .idle-place-tag{
        margin-left: 5px;
        font-weight: 500;
        color: rgb(0, 0, 0);
      }
      :deep(.van-icon){
        font-size: 8px;
      }
    }
  }
}
</style>
