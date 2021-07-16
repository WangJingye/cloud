<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">字典数据列表</h2>
    <hr class="bd-title-line"/>
    <div class="ant-table ant-table-scroll-position-left ant-table-default ant-table-bordered">
      <div class="btn-box clearfix">
        <a class="ant-btn ant-btn-success pull-right" @click="showModal()">
          <a-icon type="plus"/>
          创建
        </a>
      </div>
      <form class="ant-form ant-form-horizontal search-form" onsubmit="return false;">
        <div class="form-content">
          <span class="ant-col ant-form-item-label search-label">数据状态</span>
          <a-select class="search-input" placeholder="请选择" v-model="searchForm.status">
            <a-select-option value="">请选择</a-select-option>
            <template v-for="(item,index) in statusOptions">
              <a-select-option :key="index" :value="index">{{item}}</a-select-option>
            </template>
          </a-select>
        </div>
        <div class="form-content">
          <span class="ant-col ant-form-item-label search-label">查询条件</span>
          <div class="clearfix" style="display: inline-flex;">
            <a-select class="search-type" placeholder="请选择" v-model="searchForm.searchType">
              <a-select-option value="">请选择</a-select-option>
              <a-select-option value="id">ID</a-select-option>
              <a-select-option value="dictLabel">数据名称</a-select-option>
              <a-select-option value="dictValue">数据值</a-select-option>
            </a-select>
            <input type="text" class="ant-input search-value" placeholder="关键词" v-model="searchForm.searchValue">
            <button class="ant-btn ant-btn-primary search-btn" @click="search">
              <a-icon type="search"/>
              搜索
            </button>
          </div>
        </div>
      </form>
      <div class="ant-table-content">
        <div class="ant-table-body">
          <table>
            <thead class="ant-table-thead">
            <tr>
              <th>ID</th>
              <th>数据名称</th>
              <th>数据值</th>
              <th>排序</th>
              <th>状态</th>
              <th>备注</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody class="ant-table-tbody">
            <template v-for="item in list">
              <tr :key="item.id">
                <td>{{ item.id }}</td>
                <td>{{ item.dictLabel }}</td>
                <td>{{ item.dictValue }}</td>
                <td>{{ item.sort }}</td>
                <td>{{ statusOptions[item.status] }}</td>
                <td>{{ item.remark }}</td>
                <td>{{ $helper.formatDate(item.createTime) }}</td>
                <td>
                  <a class="ant-btn ant-btn-primary ant-btn-sm" @click="showModal(item.id)">
                    <a-icon type="edit"/>
                    编辑
                  </a>
                  <a v-if="item.status === 1" @click="setStatus(item,0)" class="ant-btn ant-btn-danger ant-btn-sm">
                    <a-icon type="stop"/>
                    禁用</a>
                  <a v-if="item.status === 0" @click="setStatus(item,1)" class="ant-btn ant-btn-success ant-btn-sm">
                    <a-icon type="check-circle"/>
                    启用
                  </a>
                </td>
              </tr>
            </template>
            <emptyList v-if="list.length===0"/>
            </tbody>
          </table>
          <pagination
            :total-page="pager.totalPage"
            :page="pager.page"
            :search-form="searchForm"
            :search-function="getList"
            v-if="list.length>0"/>
        </div>
      </div>
    </div>
    <a-modal
      :title="modalTitle"
      :visible="visible"
      @ok="handleOk"
      @cancel="handleCancel"
      ok-text="确定"
      cancel-text="取消"
    >
      <a-form-model
        ref="ruleForm"
        :model="modalData"
        :rules="rules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 20 }">
        <a-input v-model="modalData.id" type="hidden"/>
        <a-form-model-item label="数据名称" prop="dictLabel">
          <a-input v-model="modalData.dictLabel" placeholder="请输入数据名称"/>
        </a-form-model-item>
        <a-form-model-item label="数据值" prop="dictValue">
          <a-input v-model="modalData.dictValue" placeholder="请输入数据值"/>
        </a-form-model-item>
        <a-form-model-item label="字典类型">
          <a-input v-model="modalData.dictType" :disabled="true"/>
        </a-form-model-item>
        <a-form-model-item label="排序" prop="sort">
          <a-input-number v-model="modalData.sort" placeholder="请输入排序值"/>
        </a-form-model-item>
        <a-form-model-item label="备注">
          <a-textarea v-model="modalData.remark" placeholder=""/>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>
<script>
import {getList, setStatus, getDictData, saveDictData} from '@/api/system/dict/data'

export default {
  data () {
    return {
      list: [],
      visible: false,
      modalTitle: '创建数据',
      modalData: {},
      searchForm: {},
      statusOptions: {'1': '可用', '0': '停用'},
      pager: {},
      rules: {
        dictLabel: [
          {required: true, message: '请输入数据名称', trigger: 'blur'}
        ],
        dictValue: [
          {required: true, message: '请输入数据值', trigger: 'blur'}
        ],
        sort: [
          {required: true, message: '请输入排序值', trigger: 'blur'}
        ]
      }
    }
  },
  created () {
    this.getList(this.$route.query)
    let query = this.$route.query
    for (let i in query) {
      this.$set(this.searchForm, i, query[i])
    }
  },
  methods: {
    getList (params) {
      this.$router.replace({path: '/system/dict/data', query: params})
      getList(params).then(response => {
        this.list = response.data.list
        this.pager = {
          page: response.data.page,
          totalPage: response.data.totalPage
        }
      })
    },
    setStatus (item, status) {
      setStatus({id: item.id, status: status}).then(result => {
        item.status = status
      })
    },
    search (event) {
      event.preventDefault()
      this.$set(this.searchForm, 'page', 1)
      this.getList(JSON.parse(JSON.stringify(this.searchForm)))
    },
    showModal (id) {
      if (id !== undefined && id != null) {
        this.modalTitle = '修改数据-' + id
        getDictData({id: id}).then(result => {
          this.modalData = result.data
        }).then(() => {
          this.visible = true
        })
      } else {
        this.modalTitle = '创建数据'
        this.modalData = {dictType: this.$route.query.type}
        this.visible = true
      }
    },
    handleOk () {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          saveDictData(this.modalData).then(() => {
            this.getList(this.$route.query)
          })
        }
      })
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
