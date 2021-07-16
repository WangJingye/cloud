import request from '@/utils/request'

export function getSystemMenuList (data) {
  return request({
    url: '/system/menu/getSystemMenuList',
    method: 'get',
    data: data
  })
}

export function getActiveMenuList (data) {
  return request({
    url: '/system/menu/getActiveMenuList',
    method: 'get',
    data: data
  })
}
