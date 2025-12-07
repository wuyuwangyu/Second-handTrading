<template>
  <div class="cart-item">
    <div class="cart-item-title">
      <div class="cart-item-checkbox">
        <van-checkbox v-model="all">
          <div class="cart-item-name">{{item.shopName}}</div>
        </van-checkbox>
      </div>
      <div class="cart-item-top">
        <div class="cart-item-coupon">
          <div class="cart-item-coupon-value">
            <div class="coupon-text">满200减20</div>
          </div>
          <div class="cart-item-coupon-preferential">
            <span class="coupon-text">领卷凑单</span>
          </div>
        </div>
      </div>
    </div>
    <div class="cart-item-content" v-for="item in item.items" :key="item.id">
      <div class="cart-item-checkbox">
        <van-checkbox name="item"></van-checkbox>
      </div>
      <div class="cart-item-img">
        <img :src="item.item_img" alt="">
      </div>
      <div class="cart-item-text">
        <div class="cart-item-name">商品名称</div>
        <div class="cart-item-categories">
          <div class="categories-text">商品样式</div>
          <div class="categories-icon"></div>
        </div>
        <div class="cart-item-coupon">
          <span class="coupon-value"></span>
          <span class="coupon-text">7天价保</span>
        </div>
        <div class="cart-item-price">
          <span class="price-text"><i class="price-icon">¥</i><span class="int">{{item.item_price}}</span>.9 到手价</span>
          <span class="price-old"><i>¥</i>20.9</span>
          <van-stepper v-model="value"
          width="20px" button-size="20px"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CartItem',
  data () {
    return {
      all: false,
      value: 1
    }
  },
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  methods: {
  }
}
</script>

<style lang="less" scoped>
.cart-item{
  background-color: rgb(255, 255, 255);
  padding: 10px;
  border-radius: 10px;
  .cart-item-title{
    display: flex;
    flex-direction: column;
    .cart-item-checkbox{
      display: flex;
      align-items: center;
      :deep(.van-checkbox__icon){
        display: flex;
        width: 20px;
      }
      :deep(.van-checkbox__label){
        display: flex;
      }
      .cart-item-name{
        display: flex;
        align-items: center;
        height: 20px;
        font-size: 18px;
        font-weight: 500;
        color: rgb(51, 51, 51);
        &:after{
          height: 20px;
          content: '\f105'; /* 具体图标的Unicode */
          font-family: 'icomoon';
          font-size: 12px;
          margin-left: 5px;
        }
      }
    }
    .cart-item-top{
      display: flex;
      align-items: center;
      margin-left: 28px;
      .cart-item-coupon{
        margin-top: 10px;
        display: flex;
        flex: 1;
        justify-content: space-between;
        align-items: center;
        .cart-item-coupon-value{
          padding: 3px 5px;
          border-radius: 5px;
          font-size: 10px;
          color: rgb(238, 10, 36);
          background-color:rgba(238, 10, 36,.2) ;
          .coupon-text{
            display: flex;
            align-items: center;
            font-weight: 500;
            letter-spacing: 1px; /* 增加字符间距 */
            -webkit-font-smoothing: antialiased; /* 平滑字体边缘 */
            text-rendering: optimizeLegibility;   /* 提高可读性 */
            &:after{
              content: '\f105';
              font-family: 'icomoon';
              font-size: 12px;
              margin-left: 2px;
            }
          }
        }
        .cart-item-coupon-preferential{
          display: flex;
          align-items: center;
          margin-left: 10px;
          .coupon-text{
            font-size: 12px;
            &:after{
              content: '\f105';
              font-family: 'icomoon';
              font-size: 12px;
              margin-left: 8px;
            }
          }
        }
      }
    }
  }
  .cart-item-content{
    display: flex;
    align-items: center;
    margin-top: 10px;
    .cart-item-checkbox{
      display: flex;
      align-items: center;
      :deep(.van-checkbox__icon){
        display: flex;
        width: 20px;
      }
      :deep(.van-checkbox__label){
        display: flex;
      }
    }
    .cart-item-img{
      width: 100px;
      height: 100px;
      margin-left: 10px;
      border-radius: 8px;
      overflow: hidden;
      img{
        width: 100%;
        height: 100%;
      }
    }
    .cart-item-text{
      display: flex;
      flex-direction: column;
      height: 100px;
      flex: 1;
      margin-left: 10px;
      .cart-item-name{
        display: flex;
        align-items: center;
        height: 20px;
        font-size: 18px;
        font-weight: 500;
        color: rgb(51, 51, 51);
      }
      .cart-item-categories{
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 10px;
        font-size: 12px;
        font-weight: 500;
        color: rgb(153, 153, 153);
        background-color: rgb(238, 238, 238);
        border-radius: 5px;
        padding-left: 8px;
        .categories-text{
          width: 180px;
          height: 20px;
          text-align: left;
          // 文本超出部分显示省略号
          white-space: nowrap;       /* 防止文本换行 */
          overflow: hidden;          /* 超出部分隐藏 */
          text-overflow: ellipsis;   /* 溢出时显示省略号 */
        }
        .categories-icon{
          display: flex;
          align-items: center;
          margin-right: 5px;
          &::after{
            display: flex;
            align-items: center;
            width: 10px;
            height: 20px;
            content: '\f107';
            font-family: 'icomoon';
            font-size: 12px;
          }
        }
      }
      .cart-item-coupon{
        display: flex;
        align-items: center;
        margin-top: 5px;
        font-size: 10px;
        font-weight: 500;
        .coupon-value{
          color: rgb(238, 10, 36);
        }
        .coupon-text{
          color: rgb(205,133,63);
        }
      }
      .cart-item-price{
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 25px;
        margin-top: 5px;
        font-size: 12px;
        font-weight: 500;
        color: rgb(238, 10, 36);
        i{
          font-family: "fantasy,Arial,cursive";
          font-style: normal;
        }
        span{
          font-family: "SimHei,MicroSoft Ya";
          color: rgb(153, 153, 153);
        }
        .price-icon,.price-text,.int{
          color: rgb(238, 10, 36);
          .int{
            font-size: 18px;
          }
        }
        .price-old{
          display: flex;
          align-items: flex-end;
          height: 25px;
          margin-left: 10px;
          color: rgb(153, 153, 153);
        }
        :deep(.van-stepper){
          display: flex;
          align-items: flex-end;
          height: 25px;
        }
        :deep(.van-stepper__input){
          width: 28px;
          font-size: 10px;
          border-radius: 4px;
        }
        :deep(.van-stepper__minus--disabled){
          background-color:transparent;
        }
        :deep(.van-stepper__minus){
          background-color:transparent;
        }
        :deep(.van-stepper__minus::before){
          height: 2px;
        }
        :deep(.van-stepper__plus){
          background-color:transparent;
        }
        :deep(.van-stepper__plus::before){
          height: 2px;
        }
        :deep(.van-stepper__plus::after){
          width: 2px;
        }
      }
    }
  }
}
</style>
