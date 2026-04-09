import request from '@/utils/request'

// 查询学生报名列表
export function listStudent_apply(query) {
  return request({
    url: '/system/student_apply/list',
    method: 'get',
    params: query
  })
}

// 查询学生报名详细
export function getStudent_apply(id) {
  return request({
    url: '/system/student_apply/' + id,
    method: 'get'
  })
}

// 新增学生报名
export function addStudent_apply(data) {
  return request({
    url: '/system/student_apply',
    method: 'post',
    data: data
  })
}

// 修改学生报名
export function updateStudent_apply(data) {
  return request({
    url: '/system/student_apply',
    method: 'put',
    data: data
  })
}

// 删除学生报名
export function delStudent_apply(id) {
  return request({
    url: '/system/student_apply/' + id,
    method: 'delete'
  })
}
