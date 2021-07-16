import request from '@/utils/request'

export function getList (params) {
  return request({
    url: '/system/dict/index',
    method: 'get',
    data: params
  })
}

export function setStatus (params) {
  return request({
    url: '/system/dict/setStatus',
    method: 'post',
    data: params
  })
}

export function getDictType (params) {
  return request({
    url: '/system/dict/edit',
    method: 'get',
    data: params
  })
}

export function saveDictType (params) {
  return request({
    url: '/system/dict/edit',
    method: 'post',
    data: params
  })
}
