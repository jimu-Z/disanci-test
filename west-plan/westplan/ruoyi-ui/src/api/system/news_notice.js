import request from '@/utils/request'

// 查询新闻公告列表
export function listNews_notice(query) {
  return request({
    url: '/system/news_notice/list',
    method: 'get',
    params: query
  })
}

// 查询新闻公告详细
export function getNews_notice(id) {
  return request({
    url: '/system/news_notice/' + id,
    method: 'get'
  })
}

// 新增新闻公告
export function addNews_notice(data) {
  return request({
    url: '/system/news_notice',
    method: 'post',
    data: data
  })
}

// 修改新闻公告
export function updateNews_notice(data) {
  return request({
    url: '/system/news_notice',
    method: 'put',
    data: data
  })
}

// 删除新闻公告
export function delNews_notice(id) {
  return request({
    url: '/system/news_notice/' + id,
    method: 'delete'
  })
}
