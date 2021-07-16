import axios from 'axios'
import {message} from 'ant-design-vue'
import store from '../store'
import router from '../router'
import cookies from 'vue-cookies'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.REQUEST_HOST,
  // 超时
  timeout: 10000
})
// request拦截器
service.interceptors.request.use(config => {
  let userInfo = cookies.get('userInfo')
  if (userInfo) {
    config.headers['token'] = userInfo.token // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  if (config.data === undefined || config.data === null) {
    config.data = {}
  }

  // get请求映射params参数
  if (config.method === 'get' && config.data) {
    let url = config.url + '?'
    for (const propName of Object.keys(config.data)) {
      const value = config.data[propName]
      let part = encodeURIComponent(propName) + '='
      if (value && typeof (value) !== 'undefined') {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            let params = propName + '[' + key + ']'
            let subPart = encodeURIComponent(params) + '='
            url += subPart + encodeURIComponent(value[key]) + '&'
          }
        } else {
          url += part + encodeURIComponent(value) + '&'
        }
      }
    }
    url = url.slice(0, -1)
    config.params = {}
    config.url = url
  }

  // 除get请求外，其它请求都会有loading(get请求已单独加Loding)
  if (config.method !== 'get') {
    store.commit('showLoading')
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
  store.commit('hideLoading')
  // 未设置状态码则默认成功状态
  const code = res.data.code
  // 获取错误信息
  const msg = res.data.message
  if (code === 999) {
    message.error(msg).then(() => {
      router.replace({
        path: '/system/public/login'
      })
    })
    return Promise.reject(new Error(msg))
  } else if (code !== 200) {
    message.error(msg)
    return Promise.reject(new Error(msg))
  } else {
    if (msg.length) {
      message.success(msg)
    }
    // res.data.authorize = true
    // if (code === 998) {
    //   res.data.authorize = false
    // }
    return res.data
  }
},
error => {
  store.commit('hideLoading')
  console.log('err' + error)
  let msg = error.message
  if (msg === 'Network Error') {
    msg = '后端接口连接异常'
  } else if (msg.includes('timeout')) {
    msg = '系统接口请求超时'
  } else if (msg.includes('Request failed with status code')) {
    msg = '系统接口' + msg.substr(msg.length - 3) + '异常'
  }
  message.error(msg)
  return Promise.reject(error)
}
)
export default service
