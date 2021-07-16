<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">网站信息</h2>
    <hr class="bd-title-line"/>
    <a-form-model
      :model="data"
      @submit.prevent="onSubmit"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 14 }">
      <a-form-model-item label="管理后台名称" prop="webName">
        <a-input v-model="data.webName" placeholder="管理后台名称"/>
      </a-form-model-item>
      <a-form-model-item label="网站域名" prop="webHost">
        <a-input v-model="data.webHost" placeholder="请输入网站域名"/>
      </a-form-model-item>
      <a-form-model-item label="网站IP" prop="webIp">
        <a-input v-model="data.webIp" placeholder="请输入网站IP"/>
      </a-form-model-item>
      <a-form-model-item label="用户初始密码">
        <a-input-password v-model="data.defaultPassword" placeholder="请输入用户初始密码"/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>
<script>
import {getInfo, saveInfo} from '@/api/erp/siteInfo/index'

export default {
  data () {
    return {
      data: {}
    }
  },
  created () {
    getInfo({type: 'baseInfo'}).then(result => {
      this.data = result.data
    })
  },
  methods: {
    onSubmit () {
      saveInfo(this.data)
    }
  }
}
</script>

<style scoped>

</style>
