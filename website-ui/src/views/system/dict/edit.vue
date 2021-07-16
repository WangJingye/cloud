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
      <a-form-model-item label="字典名称" prop="dictName">
        <a-input v-model="data.dictName" placeholder="请输入字典名称"/>
      </a-form-model-item>
      <a-form-model-item label="字典类型" prop="dictType">
        <a-input v-model="data.dictType" placeholder="请输入字典类型"/>
      </a-form-model-item>
      <a-form-model-item label="备注">
        <a-textarea v-model="data.remark" placeholder=""/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getDictType, saveDictType} from '@/api/system/dict/index'

export default {
  data () {
    return {
      data: {},
      title: '创建字典',
      rules: {
        dictName: [
          {required: true, message: '请输入字典名称', trigger: 'blur'}
        ],
        dictType: [
          {required: true, message: '请输入字典类型', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    if (this.$route.query.id) {
      getDictType({id: this.$route.query.id}).then(result => {
        this.data = result.data
      })
      this.title = '修改字典-' + this.$route.query.id
    }
  },
  methods: {
    onSubmit () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          saveDictType(this.data)
        }
      })
    }
  }
}
</script>
