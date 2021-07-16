<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">微信相关</h2>
    <hr class="bd-title-line"/>
    <a-form-model
      :model="data"
      @submit.prevent="onSubmit"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 14 }">
      <a-form-model-item label="微信APPID" prop="wechatAppId  ">
        <a-input v-model="data.wechatAppId" placeholder="微信APPID"/>
      </a-form-model-item>
      <a-form-model-item label="微信密钥" prop="wechatAppSecret">
        <a-input v-model="data.wechatAppSecret" placeholder="微信密钥"/>
      </a-form-model-item>
      <a-form-model-item label="微信支付商户ID" prop="wechatMchId">
        <a-input v-model="data.wechatMchId" placeholder="微信支付商户ID"/>
      </a-form-model-item>
      <a-form-model-item label="微信支付商户密钥">
        <a-input v-model="data.wechatPayKey" placeholder="微信支付商户密钥"/>
      </a-form-model-item>
      <a-form-model-item label="微信支付回调地址">
        <a-input v-model="data.wechatNotify" placeholder="微信支付回调地址"/>
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
    getInfo({type: 'wechat'}).then(result => {
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
