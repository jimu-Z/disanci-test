import request from '@/utils/request'

// 查询校友视频列表
export function listAlumni_video(query) {
  return request({
    url: '/system/alumni_video/list',
    method: 'get',
    params: query
  })
}

// 查询校友视频详细
export function getAlumni_video(id) {
  return request({
    url: '/system/alumni_video/' + id,
    method: 'get'
  })
}

// 新增校友视频
export function addAlumni_video(data) {
  return request({
    url: '/system/alumni_video',
    method: 'post',
    data: data
  })
}

// 修改校友视频
export function updateAlumni_video(data) {
  return request({
    url: '/system/alumni_video',
    method: 'put',
    data: data
  })
}

// 删除校友视频
export function delAlumni_video(id) {
  return request({
    url: '/system/alumni_video/' + id,
    method: 'delete'
  })
}
