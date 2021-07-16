<template>
  <div>
    <breadcrumb/>
    <h2 class="bd-title">账号列表</h2>
    <hr class="bd-title-line"/>
    <div class="ant-table ant-table-scroll-position-left ant-table-default ant-table-bordered">
      <div class="btn-box clearfix">
        <router-link class="ant-btn ant-btn-success pull-right" :to="{path:'/system/admin/edit'}">
          <a-icon type="plus"/>
          创建
        </router-link>
      </div>
      <form class="ant-form ant-form-horizontal search-form" onsubmit="return false;">
        <div class="form-content">
          <span class="ant-col ant-form-item-label search-label">用户状态</span>
          <a-select class="search-input" placeholder="请选择" v-model="searchForm.status">
            <a-select-option value="">请选择</a-select-option>
            <template v-for="item in statusOptions">
              <a-select-option :key="item.value" :value="item.value">{{item.title}}</a-select-option>
            </template>
          </a-select>
        </div>
        <div class="form-content">
          <span class="ant-col ant-form-item-label search-label">查询条件</span>
          <div class="clearfix" style="display: inline-flex;">
            <a-select class="search-type" placeholder="请选择" v-model="searchForm.searchType">
              <a-select-option value="">请选择</a-select-option>
              <a-select-option value="adminId">用户ID</a-select-option>
              <a-select-option value="username">用户名</a-select-option>
              <a-select-option value="realname">真实姓名</a-select-option>
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
              <th>用户ID</th>
              <th>用户名称</th>
              <th>真实名称</th>
              <th>邮箱</th>
              <th>手机号</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody class="ant-table-tbody">
            <template v-for="item in list">
              <tr :key="item.adminId">
                <td>{{ item.adminId }}</td>
                <td>{{ item.username }}</td>
                <td>{{ item.realname }}</td>
                <td>{{ item.email }}</td>
                <td>{{ item.mobile }}</td>
                <td>{{ statusList[item.status] }}</td>
                <td>
                  <router-link class="ant-btn ant-btn-primary ant-btn-sm"
                               :to="{path:'/system/admin/edit',query:{id:item.adminId}}">
                    <a-icon type="edit"/>
                    编辑
                  </router-link>
                  <a v-if="item.status === 1" @click="setStatus(item,0)" class="ant-btn ant-btn-danger ant-btn-sm">
                    <a-icon type="stop"/>
                    禁用</a>
                  <a v-if="item.status === 0" @click="setStatus(item,1)" class="ant-btn ant-btn-success ant-btn-sm">
                    <a-icon type="check-circle"/>
                    启用
                  </a>
                  <a v-if="item.identity === 0 && user.adminId !== item.adminId" @click="resetPassword(item)"
                     class="ant-btn ant-btn-outline-danger ant-btn-sm">
                    <a-icon type="reload"/>
                    重置密码</a>
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
  </div>
</template>
<script>
import {getList, setStatus, resetPassword} from '@/api/system/admin/index'
import {getDicts} from '@/api/config/dict/data'

export default {
  data () {
    return {
      user: {},
      list: [],
      searchForm: {},
      statusOptions: [],
      statusList: [],
      pager: {}
    }
  },
  created () {
    this.user = this.$cookies.get('userInfo')
    this.getList(this.$route.query)
    let query = this.$route.query
    for (let i in query) {
      this.$set(this.searchForm, i, query[i])
    }
    getDicts('user_status').then(result => {
      this.statusOptions = result.data
      let statusList = []
      for (let i in result.data) {
        statusList[result.data[i]['value']] = result.data[i]['title']
      }
      this.statusList = statusList
    })
  },
  methods: {
    getList (params) {
      this.$router.replace({path: '/system/admin/index', query: params})
      getList(params).then(response => {
        this.list = response.data.list
        this.pager = {
          page: response.data.page,
          totalPage: response.data.totalPage
        }
      })
    },
    setStatus (item, status) {
      setStatus({id: item.adminId, status: status}).then(result => {
        item.status = status
      })
    },
    resetPassword (item) {
      if (confirm('是否重置密码为123456?')) {
        resetPassword({id: item.adminId, password: 123456})
      }
    },
    search (event) {
      event.preventDefault()
      this.$set(this.searchForm, 'page', 1)
      this.getList(JSON.parse(JSON.stringify(this.searchForm)))
    }
  }
}
</script>
