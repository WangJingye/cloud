<template>
  <div id="app">
    <component :is="layout">
      <router-view v-if="isKeepAlive"/>
    </component>
    <loading v-if="LOADING"/>
  </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
  name: 'App',
  provide () { // 父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      default_layout: 'default', // 设置layout
      isKeepAlive: true
    }
  },
  computed: {
    layout () {
      return (this.$route.meta.layout || this.default_layout) + '-layout'
    },
    ...mapState([
      'LOADING'
    ])
  },
  methods: {
    reload (callback) {
      this.isKeepAlive = false
      this.$nextTick(function () {
        if (callback !== undefined) {
          callback()
        }
        this.isKeepAlive = true
      })
    }
  }
}
</script>
