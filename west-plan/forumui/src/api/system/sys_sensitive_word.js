import request from '@/utils/request'

// 查询敏感词库列表
export function listSys_sensitive_word(query) {
  return request({
    url: '/system/sys_sensitive_word/list',
    method: 'get',
    params: query
  })
}

// 查询敏感词库详细
export function getSys_sensitive_word(id) {
  return request({
    url: '/system/sys_sensitive_word/' + id,
    method: 'get'
  })
}

// 新增敏感词库
export function addSys_sensitive_word(data) {
  return request({
    url: '/system/sys_sensitive_word',
    method: 'post',
    data: data
  })
}

// 修改敏感词库
export function updateSys_sensitive_word(data) {
  return request({
    url: '/system/sys_sensitive_word',
    method: 'put',
    data: data
  })
}

// 删除敏感词库
export function delSys_sensitive_word(id) {
  return request({
    url: '/system/sys_sensitive_word/' + id,
    method: 'delete'
  })
}
