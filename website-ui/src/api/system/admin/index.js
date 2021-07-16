import request from '@/utils/request'

export function getList (params) {
  return request({
    url: '/system/admin/index',
    method: 'get',
    data: params
  })
}

export function getAdmin (params) {
  return request({
    url: '/system/admin/edit',
    method: 'get',
    data: params
  })
}

export function saveAdmin (params) {
  return request({
    url: '/system/admin/edit',
    method: 'post',
    data: params
  })
}

export function setStatus (params) {
  return request({
    url: '/system/admin/setStatus',
    method: 'post',
    data: params
  })
}

export function resetPassword (params) {
  return request({
    url: '/system/admin/resetPassword',
    method: 'post',
    data: params
  })
}

export function getAdminList (params) {
  return request({
    url: '/system/admin/getAdminList',
    method: 'get',
    data: params
  })
}
