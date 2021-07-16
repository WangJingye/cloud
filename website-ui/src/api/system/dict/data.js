import request from '@/utils/request'

export function getList (params) {
  return request({
    url: '/system/dict/data',
    method: 'get',
    data: params
  })
}

export function setStatus (params) {
  return request({
    url: '/system/dict/setDataStatus',
    method: 'post',
    data: params
  })
}

export function getDictData (params) {
  return request({
    url: '/system/dict/editData',
    method: 'get',
    data: params
  })
}

export function saveDictData (params) {
  return request({
    url: '/system/dict/editData',
    method: 'post',
    data: params
  })
}
