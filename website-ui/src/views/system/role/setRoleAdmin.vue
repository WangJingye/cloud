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
      <a-form-model-item label="所选用户">
        <a-select
          :allow-clear="true"
          mode="multiple"
          placeholder="请选择用户"
          v-model="data.adminList"
          :label-in-value="true"
          :default-active-first-option="false"
          option-filter-prop="label">
          <template v-for="item in selectList">
            <a-select-option :key="item.value" :value="item.value" :label="item.title">{{item.title}}</a-select-option>
          </template>
        </a-select>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getRoleAdminList, saveRoleAdminInfo} from '@/api/system/role/index'
import {getAdminList} from '@/api/system/admin/index'

export default {
  data () {
    return {
      data: {},
      list: [],
      title: '设置角色用户'
    }
  },
  created () {
    getAdminList().then(result => {
      this.list = result.data
    }).then(result => {
      if (this.$route.query.id) {
        getRoleAdminList({id: this.$route.query.id}).then(result => {
          let admins = result.data.adminList
          let adminList = []
          for (let i in admins) {
            adminList.push({key: admins[i].toString(), label: this.list[admins[i]]})
          }
          result.data.adminList = adminList
          this.data = result.data
        })
      }
    })
  },
  computed: {
    selectList () {
      var that = this
      let adminList = []
      if (that.data.adminList) {
        adminList = that.data.adminList.map(o => o.key)
      }
      let result = []
      for (let i in that.list) {
        if (!adminList.includes(i)) {
          result.push({value: i, title: that.list[i]})
        }
      }
      return result
    }
  },
  methods: {
    onSubmit () {
      let adminList = this.data.adminList.map(item => item.key)
      saveRoleAdminInfo({id: this.data.id, adminList: adminList})
    }
  }
}
</script>
