import request from '@/utils/request'

export function getList (params) {
  return request({
    url: '/system/menu/index',
    method: 'get',
    data: params
  })
}

export function getTreeList () {
  return request({
    url: '/system/menu/getTreeList',
    method: 'get',
    data: {}
  })
}

export function getMenu (params) {
  return request({
    url: '/system/menu/edit',
    method: 'get',
    data: params
  })
}

export function saveMenu (params) {
  return request({
    url: '/system/menu/edit',
    method: 'post',
    data: params
  })
}

export function setStatus (params) {
  return request({
    url: '/system/menu/setStatus',
    method: 'post',
    data: params
  })
}

export function getAllMethodList (params) {
  return request({
    url: '/system/menu/getAllMethodList',
    method: 'get',
    data: params
  })
}
