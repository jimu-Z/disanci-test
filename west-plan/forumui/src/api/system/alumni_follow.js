import request from '@/utils/request'

// 查询校友关注列表
export function listAlumni_follow(query) {
  return request({
    url: '/system/alumni_follow/list',
    method: 'get',
    params: query
  })
}

// 查询校友关注详细
export function getAlumni_follow(id) {
  return request({
    url: '/system/alumni_follow/' + id,
    method: 'get'
  })
}

// 新增校友关注
export function addAlumni_follow(data) {
  return request({
    url: '/system/alumni_follow',
    method: 'post',
    data: data
  })
}

// 修改校友关注
export function updateAlumni_follow(data) {
  return request({
    url: '/system/alumni_follow',
    method: 'put',
    data: data
  })
}

// 删除校友关注
export function delAlumni_follow(id) {
  return request({
    url: '/system/alumni_follow/' + id,
    method: 'delete'
  })
}
