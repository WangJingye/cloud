<template>
  <a-layout>
    <a-layout-header class="bd-header">
      <div class="logo" @click="goHome">后台管理系统</div>
      <a-menu class="bd-menu" theme="dark" mode="horizontal"
              :default-selected-keys="[activeConfig.top]"
              v-if="!isLoading"
              @click="topMenuClick">
        <template v-for="item in topMenuList">
          <a-menu-item :key="item.id">
            {{ item.name }}
          </a-menu-item>
        </template>
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="16rem" class="bd-sidebar">
        <a-menu mode="inline" theme="dark"
                :default-selected-keys="[activeConfig.child]"
                :default-open-keys="[activeConfig.left]"
                @click="menuClick"
                v-if="!isLoading"
                :style="{ height: '100%', borderRight: 0 }">
          <template v-for="item in leftMenuList">
            <a-sub-menu :key="item.item.id">
              <span slot="title"><a-icon :type="item.item.icon"/>{{item.item.name}}</span>
              <template v-for="child in item.list">
                <a-menu-item :key="child.id">
                  {{child.name}}
                </a-menu-item>
              </template>
            </a-sub-menu>
          </template>
        </a-menu>
      </a-layout-sider>
      <a-layout>
        <a-layout-content :style="{ background: '#fff',padding:'0.5rem 1.5rem', margin: 0, minHeight: '280px' }">
          <router-view v-if="isRouterAlive"/>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
import {getSystemMenuList} from '@/api/common/menu'

export default {
  inject: ['reload'],
  data () {
    return {
      isRouterAlive: true,
      topMenuList: [],
      leftMenuList: [],
      childList: [],
      activeConfig: {
        top: '',
        left: '',
        child: ''
      },
      title: '',
      isLoading: false,
      activeMenuList: []
    }
  },
  methods: {
    getMenuList () {
      this.isLoading = true
      var that = this
      getSystemMenuList({url: '/' + that.$helper.trim(this.$route.path, '/')}).then(response => {
        this.topMenuList = response.data.topList
        let leftMenuList = this.leftMenuList = response.data.leftList
        let activeList = this.activeMenuList = response.data.activeList
        let childList = {}
        for (let i in leftMenuList) {
          let data = leftMenuList[i]
          for (let j in data['list']) {
            let item = data['list'][j]
            childList[item.id] = {
              id: item['id'],
              name: item['name'],
              parent: {name: data['item']['name'], id: data['item']['id']},
              url: that.$helper.trim(item['url'], '/')
            }
          }
        }
        this.childList = childList
        this.activeConfig = {
          top: activeList[0].id,
          left: activeList[1].id,
          child: activeList[2].id
        }
        this.isLoading = false
      })
    },
    topMenuClick ({item, key, keyPath}) {
      var that = this
      let topList = this.topMenuList
      for (let i in topList) {
        if (topList[i]['id'] === key) {
          this.$router.push({
            path: '/' + topList[i]['url']
          }).then(function () {
            that.reload(function () {
              that.getMenuList()
            })
          })
          break
        }
      }
    },
    menuClick ({item, key, keyPath}) {
      let data = this.childList[key]
      let path = '/' + data['url']
      if (path === this.$route.path) {
        this.isRouterAlive = false
        this.$router.replace({
          path: path
        }).then(router => {
          this.isRouterAlive = true
        })
      } else {
        this.$router.push({
          path: '/' + data['url']
        })
      }
    },
    goHome () {
      if (this.$helper.trim(this.$route.path, '/').length) {
        this.isRouterAlive = false
        this.$router.push({
          path: '/'
        }).then(router => {
          this.isRouterAlive = true
        })
      } else {
        this.isRouterAlive = false
        this.$router.replace({
          path: '/'
        }).then(router => {
          this.isRouterAlive = true
        })
      }
    }
  },
  created () {
    this.getMenuList()
  },
  watch: {
    '$route' (to, from) {
      this.isRouterAlive = false
      setTimeout(() => {
        this.isRouterAlive = true
      })
    }
  }
}
</script>
