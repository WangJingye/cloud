// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import VueCookies from 'vue-cookies'
import router from './router'
import App from './App'
import store from './store'
import DefaultLayout from './layout/index'
import EmptyLayout from './layout/empty'
import Antd from 'ant-design-vue'
import Breadcrumb from './components/breadcrumb/index'
import EmptyList from './components/table/empty'
import Pagination from './components/table/pagination'
import Loading from './components/modal/loading'
import Upload from './components/input/upload'
import helper from './utils/helper'

import 'ant-design-vue/dist/antd.css'
import './assets/css/btn.css'
import './assets/css/main.css'

Vue.use(VueCookies)
Vue.use(Vuex)
Vue.use(Antd)

Vue.component('default-layout', DefaultLayout)
Vue.component('empty-layout', EmptyLayout)
Vue.component('breadcrumb', Breadcrumb)
Vue.component('emptyList', EmptyList)
Vue.component('pagination', Pagination)
Vue.component('loading', Loading)
Vue.component('upload', Upload)

Vue.config.productionTip = false
Vue.prototype.$helper = helper
/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: {App},
  template: '<App/>'
})
