import request from '@/utils/request'

// 查询论坛板块列表
export function listForum_board(query) {
  return request({
    url: '/system/forum_board/list',
    method: 'get',
    params: query
  })
}

// 查询论坛板块可用板块
export function enableList( ) {
  return request({
    url: '/system/forum_board/enable/list'  ,
    method: 'get'
  })
}
// 查询论坛板块详细
export function getForum_board(id) {
  return request({
    url: '/system/forum_board/' + id,
    method: 'get'
  })
}

// 新增论坛板块
export function addForum_board(data) {
  return request({
    url: '/system/forum_board',
    method: 'post',
    data: data
  })
}

// 修改论坛板块
export function updateForum_board(data) {
  return request({
    url: '/system/forum_board',
    method: 'put',
    data: data
  })
}

// 删除论坛板块
export function delForum_board(id) {
  return request({
    url: '/system/forum_board/' + id,
    method: 'delete'
  })
}
