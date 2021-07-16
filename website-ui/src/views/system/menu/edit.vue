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
      <a-form-model-item label="父级功能" prop="parentId">
        <a-tree-select :tree-data="treeList" v-model="data.parentId" placeholder="请选择"
                       :dropdown-style="{maxHeight:'25rem',overflow:'auto'}"
                       treeNodeFilterProp="title" show-search :allow-clear="true"/>
      </a-form-model-item>
      <a-form-model-item label="链接地址" prop="url">
        <a-select v-model="data.url" placeholder="请选择">
          <a-select-option value="">请选择</a-select-option>
          <template v-for="item in methodList">
            <a-select-option :key="item" :value="item">{{item}}</a-select-option>
          </template>
        </a-select>
      </a-form-model-item>
      <a-form-model-item label="标题" prop="name">
        <a-input v-model="data.name" placeholder="请输入标题"/>
      </a-form-model-item>
      <a-form-model-item label="排序" prop="sort">
        <a-input-number v-model="data.sort" placeholder="请输入排序数字"/>
      </a-form-model-item>
      <a-form-model-item label="图标样式">
        <a-input v-model="data.icon" placeholder="icon"/>
      </a-form-model-item>
      <a-form-model-item label="菜单描述">
        <a-textarea v-model="data.desc"/>
      </a-form-model-item>
      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <input class="ant-btn ant-btn-primary" type="submit" value="保存"/>
      </a-form-model-item>
    </a-form-model>
  </div>
</template>

<script>
import {getMenu, saveMenu, getTreeList, getAllMethodList} from '@/api/system/menu/index'

export default {
  data () {
    return {
      data: {},
      title: '创建菜单',
      treeList: [],
      methodList: [],
      rules: {
        name: [
          {required: true, message: '请输入标题', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '请输入排序数字', trigger: 'blur', type: 'number'}
        ]
      }
    }
  },
  created () {
    getTreeList().then(result => {
      let data = result.data
      data.unshift({title: '顶级目录', value: '0'})
      this.treeList = result.data
    })

    if (this.$route.query.id) {
      getMenu({id: this.$route.query.id}).then(result => {
        this.data = result.data
      })
      this.title = '修改菜单-' + this.$route.query.id
    }
    getAllMethodList({id: this.$route.query.id}).then(result => {
      this.methodList = result.data
    })
  },
  methods: {
    onSubmit () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          saveMenu(this.data)
        }
      })
    }
  }
}
</script>
