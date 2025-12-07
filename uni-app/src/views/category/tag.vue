<template>
  <div class="tag">
    <div class="tag-title">
      <van-tabs v-model="active" scrollspy sticky :offset-top="46">
        <van-tab v-for="index in 6" :key="index" :title="'选项 ' + index">
          <div class="tag-content">
            <tag-list class="tag-list"></tag-list>
          </div>
        </van-tab>
      </van-tabs>
      <div class="tab-list"><van-icon name="arrow-down" /></div>
    </div>
  </div>
</template>

<script>
import TagList from '@/components/TagList.vue'
export default {
  name: 'tagPage',
  data () {
    return {
      active: 0
    }
  },
  components: {
    TagList
  }
}
</script>

<style scoped>
.tag{
  .tag-title{
    position: relative;
    width: 100%;
    height: 48px;
    background: #f7f8fa;
    :deep(.van-sticky--fixed){
      left: 120px;
    }
    .tab-list{
      position: absolute;
      top: 0;
      right: 0;
      display: flex;
      align-items: center;
      justify-content: center;
      width: 44px;
      height: 44px;
      border-radius: 50%;
      z-index: 100;
      &::before{
        position: absolute;
        content: '';
        width: 100%;
        height: 100%;
        background: #f7f8fa;
        border-radius: 50% 0 0 50% ;
      }
      .van-icon{
        display: flex;
        align-items: center;
        justify-content: center;
        width: 25px;
        height: 25px;
        background: rgb(255, 255, 255);
        border-radius: 50%;
      }
    }
    :deep(.van-tab){
      padding: 0 5px;
    }
    :deep(.van-tabs__nav){
      background: #f7f8fa;
      padding: 0;
    }
    :deep(.van-tabs__line){
      display: none;
    }
    :deep(.van-tab__text){
      display: flex;
      justify-content: center;
      align-items: center;
      /* width: 60px; */
      padding: 0 10px;
      height: 25px;
      border-radius: 12.5px;
      background: rgb(255, 255, 255);
    }
    :deep(.van-tab--active .van-tab__text){
      padding-top: 2px;
      color: #ee0a24;
      border: 1px solid #ee0a24;
    }
  }
  .tag-content{
    padding-right: 10px;
    width: 100%;
    height: 100%;
    background: #f7f8fa;
  }
  .tag-list{
    padding-bottom: 10px;
  }
}
</style>
