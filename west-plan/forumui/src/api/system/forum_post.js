import request from '@/utils/request'

// 查询论坛帖子列表
export function listForum_post(query) {
  return request({
    url: '/system/forum_post/list',
    method: 'get',
    params: query
  })
}
// 查询置顶热点帖子（首页展示）
export function topHotList( ) {
  return request({
    url: '/system/forum_post/top/hot',
    method: 'get'

  })
}
// 查询最新发布帖子
export function newList( query) {
  return request({
    url: '/system/forum_post/new/list',
    method: 'get',
   params: query
  })
}
 // 帖子点赞/取消点赞
 export function like(postId) {
  return request({
    url: '/system/forum_post/like/'+postId,
    method: 'post',
    data: postId
  })
}
        // 帖子收藏/取消收藏
 export function collect(postId) {
  return request({
    url: '/system/forum_post/collect/'+postId,
    method: 'post',
    data: postId
  })
}
// 查询论坛帖子详细
export function getForum_post(id) {
  return request({
    url: '/system/forum_post/' + id,
    method: 'get'
  })
}
//我的帖子,params: {pageNum: 1, pageSize: 10}
export function myPosts(query) {
  return request({
    url: '/system/forum_post/myPosts',
    method: 'get',
    params: query
  })
}
// 新增论坛帖子
export function addForum_post(data) {
  return request({
    url: '/system/forum_post',
    method: 'post',
    data: data
  })
}

// 修改论坛帖子
export function updateForum_post(data) {

  return request({
    url: '/system/forum_post',
    method: 'put',
    data: data
  })
}

// 删除论坛帖子
export function delForum_post(id) {
  return request({
    url: '/system/forum_post/' + id,
    method: 'delete'
  })
}
