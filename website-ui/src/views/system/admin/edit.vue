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
      <a-input v-model="data.adminId" type="hidden"/>
      <a-form-model-item label="用户名" prop="username">
        <a-input v-model="data.username" placeholder="请输入用户名"/>
      </a-form-model-item>
      <a-form-model-item label="真实姓名" prop="realname">
        <a-input v-model="data.realname" placeholder="请输入真实姓名"/>
      </a-form-model-item>
      <a-form-model-item label="邮箱" prop="email">
        <a-input v-model="data.email" placeholder="请输入邮箱"/>
      </a-form-model-item>
      <a-form-model-item label="手机号">
        <a-input v-model="data.mobile" placeholder="请输入手机号"/>
      </a-form-model-item>
      <a-form-model-item label="头像">
        <upload v-model="data.avatar"/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getAdmin, saveAdmin} from '@/api/system/admin/index'

export default {
  data () {
    return {
      data: {},
      title: '创建账号',
      rules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        realname: [
          {required: true, message: '请输入真实姓名', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    if (this.$route.query.id) {
      getAdmin({id: this.$route.query.id}).then(result => {
        this.data = result.data
      })
      this.title = '修改账号-' + this.$route.query.id
    }
  },
  methods: {
    onSubmit () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          saveAdmin(this.data)
        }
      })
    }
  }
}
</script>
