import request from '@/utils/request'

export function getInfo (params) {
  return request({
    url: '/erp/siteInfo/getInfo',
    method: 'get',
    data: params
  })
}
export function saveInfo (params) {
  return request({
    url: '/erp/siteInfo/saveInfo',
    method: 'post',
    data: params
  })
}
