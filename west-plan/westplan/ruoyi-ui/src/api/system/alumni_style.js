import request from '@/utils/request'

// 查询校友个人风采列表
export function listAlumni_style(query) {
  return request({
    url: '/system/alumni_style/list',
    method: 'get',
    params: query
  })
}

// 查询校友个人风采详细
export function getAlumni_style(id) {
  return request({
    url: '/system/alumni_style/' + id,
    method: 'get'
  })
}

// 新增校友个人风采
export function addAlumni_style(data) {
  return request({
    url: '/system/alumni_style',
    method: 'post',
    data: data
  })
}

// 修改校友个人风采
export function updateAlumni_style(data) {
  return request({
    url: '/system/alumni_style',
    method: 'put',
    data: data
  })
}

// 删除校友个人风采
export function delAlumni_style(id) {
  return request({
    url: '/system/alumni_style/' + id,
    method: 'delete'
  })
}
