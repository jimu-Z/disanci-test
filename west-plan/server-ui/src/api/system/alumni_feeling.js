import request from '@/utils/request'

// 查询校友工作感悟列表
export function listAlumni_feeling(query) {
  return request({
    url: '/system/alumni_feeling/list',
    method: 'get',
    params: query
  })
}

// 查询校友工作感悟详细
export function getAlumni_feeling(id) {
  return request({
    url: '/system/alumni_feeling/' + id,
    method: 'get'
  })
}

// 新增校友工作感悟
export function addAlumni_feeling(data) {
  return request({
    url: '/system/alumni_feeling',
    method: 'post',
    data: data
  })
}

// 修改校友工作感悟
export function updateAlumni_feeling(data) {
  return request({
    url: '/system/alumni_feeling',
    method: 'put',
    data: data
  })
}

// 删除校友工作感悟
export function delAlumni_feeling(id) {
  return request({
    url: '/system/alumni_feeling/' + id,
    method: 'delete'
  })
}
