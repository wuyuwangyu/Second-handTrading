<template>
  <div class="hot">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'hotPage',
  data () {
    return {
    }
  }
}
</script>

<style lang="less" scoped>
.hot{
  width: 100%;
  height: 100%;
  background: rgb(245, 245, 245);
}
</style>
