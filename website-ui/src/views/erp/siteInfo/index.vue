<template>
  <div style="text-align: center;font-size: 2rem">欢迎使用{{data.webName}}</div>
</template>

<script>
import {getInfo} from '@/api/erp/siteInfo/index'

export default {
  data () {
    return {
      data: {}
    }
  },
  created () {
    getInfo({type: 'index'}).then(result => {
      this.data = result.data
    })
  }
}
</script>

<style scoped>

</style>
