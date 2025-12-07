<template>
  <div class="user">
    <div class="user-title">
      <div class="username">{{userInfo.username}}</div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'UserPage',
  data () {
    return {
      userInfo: {
        id: '',
        username: ''
      }
    }
  },
  created () {
    this.userInfo = this.$store.state.user.userInfo
  }
}
</script>
<style lang="less" scoped>
</style>
