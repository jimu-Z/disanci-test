import request from '@/utils/request'

// 查询通用点赞/收藏列表
export function listCommon_interact(query) {
  return request({
    url: '/system/common_interact/list',
    method: 'get',
    params: query
  })
}

// 查询通用点赞/收藏详细
export function getCommon_interact(id) {
  return request({
    url: '/system/common_interact/' + id,
    method: 'get'
  })
}

// 新增通用点赞/收藏
export function addCommon_interact(data) {
  return request({
    url: '/system/common_interact',
    method: 'post',
    data: data
  })
}

// 修改通用点赞/收藏
export function updateCommon_interact(data) {
  return request({
    url: '/system/common_interact',
    method: 'put',
    data: data
  })
}

// 删除通用点赞/收藏
export function delCommon_interact(id) {
  return request({
    url: '/system/common_interact/' + id,
    method: 'delete'
  })
}
