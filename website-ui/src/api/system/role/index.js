import request from '@/utils/request'

export function getList (params) {
  return request({
    url: '/system/role/index',
    method: 'get',
    data: params
  })
}

export function getTreeList () {
  return request({
    url: '/system/role/getTreeList',
    method: 'get',
    data: {}
  })
}

export function getRole (params) {
  return request({
    url: '/system/role/edit',
    method: 'get',
    data: params
  })
}

export function saveRole (params) {
  return request({
    url: '/system/role/edit',
    method: 'post',
    data: params
  })
}

export function setStatus (params) {
  return request({
    url: '/system/role/setStatus',
    method: 'post',
    data: params
  })
}

export function getRoleMenuList (params) {
  return request({
    url: '/system/role/setRoleMenu',
    method: 'get',
    data: params
  })
}

export function saveRoleMenuInfo (params) {
  return request({
    url: '/system/role/setRoleMenu',
    method: 'post',
    data: params
  })
}
export function getRoleAdminList (params) {
  return request({
    url: '/system/role/setRoleAdmin',
    method: 'get',
    data: params
  })
}

export function saveRoleAdminInfo (params) {
  return request({
    url: '/system/role/setRoleAdmin',
    method: 'post',
    data: params
  })
}
