import request from '@/utils/request'

// 查询微信用户绑定列表
export function listWp_wx_user(query) {
  return request({
    url: '/system/wp_wx_user/list',
    method: 'get',
    params: query
  })
}

// 查询微信用户绑定详细
export function getWp_wx_user(id) {
  return request({
    url: '/system/wp_wx_user/' + id,
    method: 'get'
  })
}

// 新增微信用户绑定
export function addWp_wx_user(data) {
  return request({
    url: '/system/wp_wx_user',
    method: 'post',
    data: data
  })
}

// 修改微信用户绑定
export function updateWp_wx_user(data) {
  return request({
    url: '/system/wp_wx_user',
    method: 'put',
    data: data
  })
}

// 删除微信用户绑定
export function delWp_wx_user(id) {
  return request({
    url: '/system/wp_wx_user/' + id,
    method: 'delete'
  })
}
