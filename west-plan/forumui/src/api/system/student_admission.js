import request from '@/utils/request'

// 查询学生录取信息列表
export function listStudent_admission(query) {
  return request({
    url: '/system/student_admission/list',
    method: 'get',
    params: query
  })
}

// 查询学生录取信息详细
export function getStudent_admission(id) {
  return request({
    url: '/system/student_admission/' + id,
    method: 'get'
  })
}

// 新增学生录取信息
export function addStudent_admission(data) {
  return request({
    url: '/system/student_admission',
    method: 'post',
    data: data
  })
}

// 修改学生录取信息
export function updateStudent_admission(data) {
  return request({
    url: '/system/student_admission',
    method: 'put',
    data: data
  })
}

// 删除学生录取信息
export function delStudent_admission(id) {
  return request({
    url: '/system/student_admission/' + id,
    method: 'delete'
  })
}
