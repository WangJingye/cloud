<template>
  <a-breadcrumb>
    <template v-for="item in list">
      <a-breadcrumb-item :key="item.id">
        <template v-if="item.url">
          <router-link :to="{path:item.url}">{{item.name}}</router-link>
        </template>
        <template v-if="!item.url">{{item.name}}</template>
      </a-breadcrumb-item>
    </template>
  </a-breadcrumb>
</template>

<script>
import {getActiveMenuList} from '@/api/common/menu'

export default {
  data () {
    return {
      list: []
    }
  },
  created () {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb () {
      getActiveMenuList({url: '/' + this.$helper.trim(this.$route.path, '/')}).then(response => {
        this.list = response.data
      })
    }
  }
}
</script>
