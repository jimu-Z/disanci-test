import request from '@/utils/request'

// 查询校友私信列表
export function listAlumni_message(query) {
  return request({
    url: '/system/alumni_message/list',
    method: 'get',
    params: query
  })
}

// 查询校友私信详细
export function getAlumni_message(id) {
  return request({
    url: '/system/alumni_message/' + id,
    method: 'get'
  })
}

// 新增校友私信
export function addAlumni_message(data) {
  return request({
    url: '/system/alumni_message',
    method: 'post',
    data: data
  })
}

// 修改校友私信
export function updateAlumni_message(data) {
  return request({
    url: '/system/alumni_message',
    method: 'put',
    data: data
  })
}

// 删除校友私信
export function delAlumni_message(id) {
  return request({
    url: '/system/alumni_message/' + id,
    method: 'delete'
  })
}
