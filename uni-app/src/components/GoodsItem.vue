<template>
  <div class="goods-item">
    <div class="idle-box" @click="$router.push(`/prodetail/${idle.id}`)">
      <div class="goods-item-img">
        <img
          :src="idle.pictureList[0] || require('@/assets/404.png')"
          alt=""
          >
      </div>
      <div class="goods-item-content">
        <div class="goods-item-title">
          <div class="goods-item-title-icon"></div>
          <div class="goods-item-title-name">{{ idle.idleName }}</div>
        </div>
        <div class="goods-item-price">
          <div class="goods-item-price-now"><i>¥</i><span> {{idle.idlePrice}} </span>到手价</div>
          <div class="goods-item-sell-count">销量{{idle.idleSellCount}}</div>
        </div>
        <div class="goods-item-place">
          <div class="goods-item-place-name">{{idle.idlePlace}}</div>
        </div>
      </div>
    </div>
    <div class="add-button" @click="getCart"></div>
  </div>
</template>

<script>
export default {
  name: 'GoodsItem',
  props: {
    idle: {
      type: Object
    }
  },
  data () {
    return {
    }
  },
  methods: {
    getCart (event) {
      event.stopPropagation()
      console.log('getCart')
    }
  }
}
</script>

<style lang="less" scoped>
.goods-item{
  position: relative;
  width: 180px;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 5px;
  .goods-item-img{
    width: 100%;
    height: 178px;
    img{
      width: 100%;
      height: 100%;
      object-fit: cover;
      object-position: center; // 确保图片内容居中
    }
  }
  .goods-item-content{
    padding: 0 5px;
    background: #FFF;
    .goods-item-title{
      display: flex;
      align-items: center;
      text-align: left;
      font-size: 14px;
      font-weight: 700;
      .goods-item-title-name{
        overflow: hidden;
        // text-overflow: ellipsis;
        white-space: nowrap;
        width: 168px;
      }
    }
    .goods-item-price{
      display: flex;
      text-align: left;
      font-size: 12px;
      color:rgb(255,63,75);
      i{
        font-family: "fantasy,Arial,cursive";
        font-style: normal;
      }
      span{
        font-family: "SimHei,MicroSoft YaHei";
        font-size: 16px;
        font-weight: 700;
      }
      .goods-item-sell-count{
        display: flex;
        padding-top: 5px;
        padding-left: 5px;
        color: rgb(200, 200, 200);
      }
    }
    .goods-item-place{
      display: flex;
      font-size: 12px;
      color:rgb(255,63,75) ;
      padding-bottom: 5px;
    }
  }
  .add-button {
      display: flex;
      position: absolute;
      bottom: 4px;
      right: 4px;
      width: 20px;
      height: 20px;
      background: url(../assets/icon-add.png) no-repeat;
      background-size: cover;
      z-index:1;
  }
}
</style>
