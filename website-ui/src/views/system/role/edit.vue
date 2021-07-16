<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">{{title}}</h2>
    <hr class="bd-title-line"/>
    <a-form-model
      ref="ruleForm"
      :model="data"
      :rules="rules"
      @submit.prevent="onSubmit"
      :label-col="{ span: 4 }"
      :wrapper-col="{ span: 14 }">
      <a-input v-model="data.id" type="hidden"/>
      <a-form-model-item label="角色名称" prop="name">
        <a-input v-model="data.name" placeholder="请输入角色名称"/>
      </a-form-model-item>
      <a-form-model-item label="描述">
        <a-textarea v-model="data.desc"/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getRole, saveRole} from '@/api/system/role/index'

export default {
  data () {
    return {
      data: {},
      title: '创建角色',
      rules: {
        name: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    if (this.$route.query.id) {
      getRole({id: this.$route.query.id}).then(result => {
        this.data = result.data
      })
      this.title = '修改角色-' + this.$route.query.id
    }
  },
  methods: {
    onSubmit () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          saveRole(this.data)
        }
      })
    }
  }
}
</script>
