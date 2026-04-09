import request from '@/utils/request'

// 查询公告未读记录列表
export function listNews_notice_unread(query) {
  return request({
    url: '/system/news_notice_unread/list',
    method: 'get',
    params: query
  })
}

// 查询公告未读记录详细
export function getNews_notice_unread(id) {
  return request({
    url: '/system/news_notice_unread/' + id,
    method: 'get'
  })
}

// 新增公告未读记录
export function addNews_notice_unread(data) {
  return request({
    url: '/system/news_notice_unread',
    method: 'post',
    data: data
  })
}

// 修改公告未读记录
export function updateNews_notice_unread(data) {
  return request({
    url: '/system/news_notice_unread',
    method: 'put',
    data: data
  })
}

// 删除公告未读记录
export function delNews_notice_unread(id) {
  return request({
    url: '/system/news_notice_unread/' + id,
    method: 'delete'
  })
}
