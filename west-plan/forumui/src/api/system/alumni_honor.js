import request from '@/utils/request'

// 查询校友荣誉彰列表
export function listAlumni_honor(query) {
  return request({
    url: '/system/alumni_honor/list',
    method: 'get',
    params: query
  })
}
export function  myHonor(){
  return request({
    url: '/system/alumni_honor/myHonor',
    method: 'get'
  })
}
// 查询校友荣誉彰详细
export function getAlumni_honor(id) {
  return request({
    url: '/system/alumni_honor/' + id,
    method: 'get'
  })
}

// 新增校友荣誉彰
export function addAlumni_honor(data) {
  return request({
    url: '/system/alumni_honor',
    method: 'post',
    data: data
  })
}

// 修改校友荣誉彰
export function updateAlumni_honor(data) {
  return request({
    url: '/system/alumni_honor',
    method: 'put',
    data: data
  })
}

// 删除校友荣誉彰
export function delAlumni_honor(id) {
  return request({
    url: '/system/alumni_honor/' + id,
    method: 'delete'
  })
}
