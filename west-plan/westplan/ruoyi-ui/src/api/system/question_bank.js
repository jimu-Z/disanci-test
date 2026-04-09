import request from '@/utils/request'

// 查询题库信息列表
export function listQuestion_bank(query) {
  return request({
    url: '/system/question_bank/list',
    method: 'get',
    params: query
  })
}

// 查询题库信息详细
export function getQuestion_bank(id) {
  return request({
    url: '/system/question_bank/' + id,
    method: 'get'
  })
}

// 新增题库信息
export function addQuestion_bank(data) {
  return request({
    url: '/system/question_bank',
    method: 'post',
    data: data
  })
}

// 修改题库信息
export function updateQuestion_bank(data) {
  return request({
    url: '/system/question_bank',
    method: 'put',
    data: data
  })
}

// 删除题库信息
export function delQuestion_bank(id) {
  return request({
    url: '/system/question_bank/' + id,
    method: 'delete'
  })
}
