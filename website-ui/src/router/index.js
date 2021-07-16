import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/erp/siteInfo/baseInfo'
Vue.use(Router)

// 路由自动化注册
const requireComponent = require.context('@/views', true) // 找到views路径下的所有文件

const dynamicRoute = requireComponent.keys().filter(fileName => {
  return fileName.indexOf('.vue') !== -1 // 过滤非.vue结尾的路由
}).map(fileName => {
  const componentConfig = requireComponent(fileName)
  const componentPath = fileName.replace(/^\.\//, '').replace(/\.vue$/, '')// 剥去文件名开头的 `./` 和`.vue`结尾的扩展名
  const componentName = componentPath.replace(/\//g, '-') // 设置name为文件夹名-index
  const component = Vue.component(componentName, componentConfig.default || componentConfig) // 根据路径注册成组件
  let layout = 'default'
  if (componentConfig.default.hasOwnProperty('data') && componentConfig.default.data().layout) {
    layout = componentConfig.default.data().layout
  }
  return {
    path: '/' + componentPath,
    name: componentName,
    component,
    meta: {
      layout: layout
    }
  }
})
dynamicRoute.push({
  path: '/',
  name: '首页',
  component: Home,
  meta: {
    layout: 'default'
  }
})
export default new Router({
  mode: 'history',
  routes: dynamicRoute
})
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
Router.prototype.replace = function replace (location) {
  return originalPush.call(this, location).catch(err => err)
}
