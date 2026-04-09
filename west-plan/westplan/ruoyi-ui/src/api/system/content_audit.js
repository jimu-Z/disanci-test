import request from '@/utils/request'

// 查询内容审核记录列表
export function listContent_audit(query) {
  return request({
    url: '/system/content_audit/list',
    method: 'get',
    params: query
  })
}

// 查询内容审核记录详细
export function getContent_audit(id) {
  return request({
    url: '/system/content_audit/' + id,
    method: 'get'
  })
}

// 新增内容审核记录
export function addContent_audit(data) {
  return request({
    url: '/system/content_audit',
    method: 'post',
    data: data
  })
}

// 修改内容审核记录
export function updateContent_audit(data) {
  return request({
    url: '/system/content_audit',
    method: 'put',
    data: data
  })
}

// 删除内容审核记录
export function delContent_audit(id) {
  return request({
    url: '/system/content_audit/' + id,
    method: 'delete'
  })
}
