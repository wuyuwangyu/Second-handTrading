<template>
  <div id="app">
    <keep-alive
    :include="cachedComponents">
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      cachedComponents: ['LayoutIndex', 'hotPage', 'recommendPage', 'GoodsItem']
    }
  },
  watch: {
    $route (to, from) {
      // 控制 ListIndex 是否缓存
      if (to.path === '/list') {
        if (!this.cachedComponents.includes('ListIndex')) {
          this.cachedComponents.push('ListIndex')
        }
      } else {
        const index = this.cachedComponents.indexOf('ListIndex')
        if (index > -1) {
          this.cachedComponents.splice(index, 1)
        }
      }
    }
  }
}

</script>
<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
