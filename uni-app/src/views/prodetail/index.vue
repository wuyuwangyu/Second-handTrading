<template>
  <div class="prodetail">
    <div class="prodtail-nav">
      <van-icon class="nav-back" name="arrow-left" @click="$router.go(-1)" />
      <div class="nav-content">
        <van-tabs v-model="active" type="card">
          <van-tab title="商品"></van-tab>
          <van-tab title="测评"></van-tab>
        </van-tabs>
      </div>
      <div class="nav-icon-list">
        <div class="nav-icon">
          <van-icon name="star-o" />
          <van-icon name="share-o" />
          <van-badge :content="20" max="9">
            <van-icon name="ellipsis" />
          </van-badge>
        </div>
      </div>
    </div>
    <div class="prodtail-img">
      <van-swipe
       ref="swipeRef"
      @change="onChange">
        <van-swipe-item v-for="item,index in idle.pictureList" :key="item.id">
          <img :src="idle.pictureList[index] || require('@/assets/404.png')" alt="">
        </van-swipe-item>
        <template #indicator>
          <div class="custom-indicator">{{ current + 1 }}/{{idle.pictureList.length}}</div>
        </template>
      </van-swipe>
    </div>
    <div class="prodtail-content">
      <div class="prodtail-value">
        <div class="new-value">
          <i>￥</i>
          <span class="int"> {{integerPart}} </span>
          <span class="decima">.{{ decimalPart }}</span>
          <van-tag type="danger">到手价</van-tag>
        </div>
        <div class="old-value">
          <span class="value">￥{{formattedPrice-20}}</span>
          <span class="space">|</span>
          <span class="sell-count">已售{{idle.idleCount}}件</span>
        </div>
      </div>
    </div>
    <div class="coupon">
      <!-- 优惠券单元格 -->
      <van-coupon-cell
        :coupons="coupons"
        :chosen-coupon="chosenCoupon"
        @click="showList = true"
      />
      <!-- 优惠券列表 -->
      <van-popup
        v-model="showList"
        round
        position="bottom"
        style="height: 90%; padding-top: 4px;"
      >
      <van-coupon-list
        :coupons="coupons"
        :chosen-coupon="chosenCoupon"
        :disabled-coupons="disabledCoupons"
        @change="onChangeCoupon"
        @exchange="onExchange"
        />
      </van-popup>
    </div>
    <div class="goods-action">
      <van-goods-action>
        <van-goods-action-icon icon="shop-o" text="店铺" color="" />
        <van-goods-action-icon icon="chat-o" text="客服" />
        <van-goods-action-icon icon="cart-o" text="购物车" badge="9" color="" />
        <van-goods-action-button type="warning" text="加入购物车" />
        <van-goods-action-button type="danger" text="立即购买" />
        <!-- <div class="action-btn">
          <div class="cart">加入购物车</div>
          <div class="buy-btn">
            <div class="buy-value">
              <span class="text">卷后价</span>
              <i>￥</i>
              <span class="int"> {{integerPart}}</span>
              <span class="decima">.{{ decimalPart }}</span>
            </div>
            <div class="buy-text">立刻购买</div>
          </div>
        </div> -->
      </van-goods-action>
    </div>
  </div>
</template>

<script>
import { getGoodsDetail } from '@/api/idle'
const coupon = {
  available: 1,
  condition: '无使用门槛\n最多优惠12元',
  reason: '',
  value: 150,
  name: '优惠券名称',
  startAt: 1489104000,
  endAt: 1514592000,
  valueDesc: '1.5',
  unitDesc: '元'
}
export default {
  name: 'ProDetail',
  created () {
    this.getIdle()
  },
  data () {
    return {
      idle: {
        id: '',
        idleCount: '',
        idleDetails: '',
        idleLabel: '',
        idleName: '',
        idlePlace: '',
        idlePrice: '',
        idleSellCount: '',
        pictureList: [],
        releaseTime: ''
      },
      current: 0,
      active: 0,
      chosenCoupon: -1,
      coupons: [coupon],
      disabledCoupons: [coupon],
      showList: false
    }
  },
  methods: {
    getIdle () {
      try {
        getGoodsDetail(this.$route.params.id).then(res => {
          if (res.code === 200) {
            this.idle = res.data
            console.log(res)
          } else {
            this.$toast('获取商品详情失败')
          }
        })
      } catch (err) {
        console.log(err)
      }
    },
    onChange (index) {
      this.current = index
      console.log(index)
      // // 如果需要动态改变样式
      // this.$nextTick(() => {
      // })
    },
    onClickIcon () {
      this.$toast('点击图标')
    },
    onClickButton () {
      this.$toast('点击按钮')
    },
    onChangeCoupon (index) {
      this.showList = false
      this.chosenCoupon = index
    },
    onExchange (code) {
      this.coupons.push(coupon)
    }
  },
  computed: {
    formattedPrice () {
      if (this.idle.idlePrice) {
      // 将价格转换为带两位小数的格式
        return parseFloat(this.idle.idlePrice).toFixed(2)
      }
      return '0.00'
    },
    integerPart () {
      return this.formattedPrice.split('.')[0]
    },
    decimalPart () {
      return this.formattedPrice.split('.')[1]
    }
  }
}
</script>

<style lang="less" scoped>
.prodetail{
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-bottom: 170.71px;
  .prodtail-nav{
    display: flex;
    position: fixed;
    top: 5px;
    left: 0;
    width: 100%;
    height: 44px;
    z-index: 1;
    .nav-back{
      display: flex;
      align-items: center;
      font-size: 20px;
    }
    .nav-content{
      display: flex;
      justify-content: left;
      align-items: center;
      width: 100%;
      height: 100%;
      :deep(.van-tabs__nav){
        border: 0;
        background: rgba(0,0,0,.5);
        border-radius: 5px;
        padding: 2px;
      }
      :deep(.van-tab){
        color: rgb(255, 255, 255);
        font-weight: 700;
        border: 0;
        z-index: 1;
      }
      :deep(.van-tab--active){
        color: rgb(0, 0, 0);
        background: rgb(255,255,255);
        border-radius: 5px;
      }
    }
    .nav-icon-list{
      margin-right: 15px;
      display: flex;
      justify-content: right;
      align-items: center;
      height: 100%;
      .nav-icon{
        display: flex;
        justify-content: center;
        align-items: center;
        border-radius: 5px;
        height: 30px;
        background: rgba(0,0,0,.5);
        :deep(.van-badge--fixed){
          border: 0;
        }
      }
      :deep(.van-icon){
        color: rgb(255, 255, 255);
        font-size: 20px;
        padding: 2px 5px;
      }
    }
  }
  .prodtail-img{
  .van-swipe{
    width: 375px;
    height: 375px;
      img{
        width: 100%;
        height: 100%;
        object-fit: cover;
        object-position: center;
      }
    }
    .custom-indicator {
      position: absolute;
      right: 15px;
      bottom: 10px;
      padding: 2px 5px;
      font-size: 16px;
      background: rgba(0, 0, 0, 0.1);
    }
  }
  .prodtail-content{
    display: flex;
    width: 100%;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .prodtail-value{
      display: flex;
      flex-direction: column; // 改为垂直排列
      padding: 2px 15px 8px 15px;
      width: 100%;
      height: 100%;
      align-items: flex-start;
      background: rgb(255,63,75);
      color: rgb(255,255,255);
      .new-value{
        display: flex;
        justify-content: center;
        align-items: center;
        i{
          display: flex;
          height: 24px;
          align-items: flex-end;
          font-family: "fantasy,Arial,cursive";
          font-size: 12px;
          font-style: normal;
        }
        .int{
          font-family: "SimHei,MicroSoft YaHei";
          font-size: 24px;
          font-weight: 700;
        }
        :deep(.van-tag){
          margin-left: 7px;
          font-size: 12px;
          font-weight: 600;
          margin-top: 5px;
          padding: 2px;
          color: rgb(255,63,75);
          background: rgb(255,255,255);
          border-radius: 3px;
        }
        .decima{
          display: flex;
          height: 29.17px;
          align-items: flex-end;
          font-family: "SimHei,MicroSoft YaHei";
          font-size: 16px;
          font-weight: 700;
        }
      }
      .old-value{
        display: flex;
        justify-content: left;
        align-items: center;
        .value{
          font-family: "SimHei,MicroSoft YaHei";
          font-size: 12px;
          font-weight: 700;
          text-decoration: line-through;
          color: rgba(255,255,255,.5);
        }
        .space{
          display: flex;
          justify-content: center;
          width: 15px;
          height: 17.26px;
          color: rgba(255,255,255,.5);
        }
        .sell-count{
          font-size: 12px;
          font-family: "SimHei,MicroSoft YaHei";
        }
      }
    }
  }
  .coupon{
    display: flex;
    .van-cell{
      width: 375px
    }
    .van-popup {
      width: 375px;
    }
  }
  .goods-action{
    :deep(.van-goods-action-button--warning){
      border-radius: 5px 0 0 5px;
    }
    :deep(.van-goods-action-button--danger){
      border-radius: 0 5px 5px 0;
    }
    .action-btn{
      display: flex;
    }
  }
}
</style>
