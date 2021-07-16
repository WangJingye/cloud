import request from '@/utils/request'

export function login (params) {
  return request({
    url: '/system/public/login',
    method: 'post',
    data: params
  })
}
