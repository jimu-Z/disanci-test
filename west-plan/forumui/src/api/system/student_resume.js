import request from '@/utils/request'

// 查询学生履历列表
export function listStudent_resume(query) {
  return request({
    url: '/system/student_resume/list',
    method: 'get',
    params: query
  })
}

// 查询学生履历详细
export function getStudent_resume(id) {
  return request({
    url: '/system/student_resume/' + id,
    method: 'get'
  })
}
export function myResume() {
  return request({
    url: '/system/student_resume/myResume',
    method: 'get'
  })
}

// 新增学生履历
export function addStudent_resume(data) {
  return request({
    url: '/system/student_resume',
    method: 'post',
    data: data
  })
}

// 修改学生履历
export function updateStudent_resume(data) {
  return request({
    url: '/system/student_resume',
    method: 'put',
    data: data
  })
}

// 删除学生履历
export function delStudent_resume(id) {

  return request({
    url: '/system/student_resume/' + id,
    method: 'delete'
  })
}
export function delResume(id) {
  return delStudent_resume(id)
}
