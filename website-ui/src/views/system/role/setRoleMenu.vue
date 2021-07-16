<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">{{title}}</h2>
    <hr class="bd-title-line"/>
    <a-form-model
      :model="data"
      @submit.prevent="onSubmit"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 14 }">
      <a-input v-model="data.id" type="hidden"/>
      <a-form-model-item label="角色名称">
        <a-input :disabled="true" :value="data.name"/>
      </a-form-model-item>
      <a-form-model-item label="所选权限">
        <a-tree checkable :tree-data="treeList" v-model="data.menuList" @check="onCheck"/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getTreeList} from '@/api/system/menu/index'
import {getRoleMenuList, saveRoleMenuInfo} from '@/api/system/role/index'

export default {
  data () {
    return {
      data: {},
      treeList: [],
      title: '设置角色权限'
    }
  },
  created () {
    getTreeList().then(result => {
      this.treeList = result.data
    }).then(result => {
      if (this.$route.query.id) {
        getRoleMenuList({id: this.$route.query.id}).then(result => {
          this.data = result.data
        })
      }
    })
  },
  methods: {
    onSubmit () {
      saveRoleMenuInfo(this.data)
    },
    onCheck (keys, info) {
      this.data.halfList = info.halfCheckedKeys
    }
  }
}
</script>
