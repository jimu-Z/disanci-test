import request from '@/utils/request'

// 查询微信公众号配置列表
export function listWx_mp_config(query) {
  return request({
    url: '/system/wx_mp_config/list',
    method: 'get',
    params: query
  })
}

// 查询微信公众号配置详细
export function getWx_mp_config(id) {
  return request({
    url: '/system/wx_mp_config/' + id,
    method: 'get'
  })
}

// 新增微信公众号配置
export function addWx_mp_config(data) {
  return request({
    url: '/system/wx_mp_config',
    method: 'post',
    data: data
  })
}

// 修改微信公众号配置
export function updateWx_mp_config(data) {
  return request({
    url: '/system/wx_mp_config',
    method: 'put',
    data: data
  })
}

// 删除微信公众号配置
export function delWx_mp_config(id) {
  return request({
    url: '/system/wx_mp_config/' + id,
    method: 'delete'
  })
}
