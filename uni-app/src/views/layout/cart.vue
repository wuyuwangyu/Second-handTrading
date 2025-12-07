<template>
  <div class="cart">
    <div class="cart-title">
      <div class="cart-text">购物车<span class="count">(9)</span></div>
      <div class="cart-address"></div>
      <div class="cart-control">管理</div>
      <div class="cart-redirect-btn">
        <van-icon name="ellipsis" />
      </div>
    </div>
    <div class="cart-tag">
    </div>
    <div class="cart-content">
      <cart-item v-for="item in cartList" :key="item.id" :item="item"></cart-item>
    </div>
    <div class="cart-goods-cal"></div>
  </div>
</template>

<script>
import CartItem from '@/views/layout/cart/CartItem.vue'
export default {
  name: 'cartPage',
  components: { CartItem },
  data () {
    return {
      cartList: [
        {
          shopId: 1,
          shopName: '店铺1',
          items: [
            { id: 1, item_name: '商品1', item_price: 10.9, item_img: require('@/assets/product1.avif'), item_count: 1 },
            { id: 2, item_name: '商品2', item_price: 20.9, item_img: require('@/assets/product2.avif'), item_count: 1 }
          ]
        },
        {
          shopId: 2,
          shopName: '店铺2',
          items: [
            { id: 3, item_name: '商品3', item_price: 30.9, item_img: require('@/assets/product1.avif'), item_count: 1 },
            { id: 4, item_name: '商品4', item_price: 40.9, item_img: require('@/assets/product2.avif'), item_count: 1 }
          ]
        }
      ]
    }
  }
}
</script>
<style lang="less" scoped>
.cart{
  // height: 100vh;
  background-color: #f2f3f5;
  margin-bottom: 45px;
  .cart-title{
    display: flex;
    justify-content: space-between;
    height: 40px;
    .cart-text{
      display: flex;
      align-items: center;
      margin-left: 10px;
      font-size: 20px;
      font-weight: 600;
      color: rgb(51, 51, 51);
      .count{
        margin-left: 5px;
        font-size: 14px;
        font-weight: 500;
        color: rgb(153, 153, 153);
      }
    }
    .cart-address{
      display: flex;
      width: 200px;
    }
    .cart-control{
      display: flex;
      align-items: center;
      // overflow: hidden;
    }
    .cart-redirect-btn{
      display: flex;
      width: 20px;
      align-items: center;
      justify-content: center;
      margin-right: 10px;
    }
  }
  .cart-content{
    padding: 5px;
    .cart-item{
      margin-bottom: 10px;
    }
  }
}
</style>
