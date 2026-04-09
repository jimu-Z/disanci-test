import request from '@/utils/request'

// 查询友情链接列表
export function listSys_friend_link(query) {
  return request({
    url: '/system/sys_friend_link/list',
    method: 'get',
    params: query
  })
}

// 查询友情链接详细
export function getSys_friend_link(id) {
  return request({
    url: '/system/sys_friend_link/' + id,
    method: 'get'
  })
}

// 新增友情链接
export function addSys_friend_link(data) {
  return request({
    url: '/system/sys_friend_link',
    method: 'post',
    data: data
  })
}

// 修改友情链接
export function updateSys_friend_link(data) {
  return request({
    url: '/system/sys_friend_link',
    method: 'put',
    data: data
  })
}

// 删除友情链接
export function delSys_friend_link(id) {
  return request({
    url: '/system/sys_friend_link/' + id,
    method: 'delete'
  })
}
