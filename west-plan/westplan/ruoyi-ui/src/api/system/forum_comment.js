import request from '@/utils/request'

// 查询论坛评论列表
export function listForum_comment(query) {
  return request({
    url: '/system/forum_comment/list',
    method: 'get',
    params: query
  })
}


// 查询论坛评论详细
export function getForum_comment(id) {
  return request({
    url: '/system/forum_comment/' + id,
    method: 'get'
  })
}

// 新增论坛评论
export function addForum_comment(data) {
  return request({
    url: '/system/forum_comment',
    method: 'post',
    data: data
  })
}

// 修改论坛评论
export function updateForum_comment(data) {
  return request({
    url: '/system/forum_comment',
    method: 'put',
    data: data
  })
}

// 删除论坛评论
export function delForum_comment(id) {
  return request({
    url: '/system/forum_comment/' + id,
    method: 'delete'
  })
}
  // 评论点赞（24小时防重复）
    //like: commentId => request({ url: `/system/comment/like/${commentId}`, method: 'post' })
export function commentLike(commentId) {
  return request({
    url: '/system/forum_comment/like/' + commentId,
    method: 'post',
    data: commentId
  })
  }
  // 根据帖子ID查询评论树形结构
  export function getTreeByPostId(postId){
    return request({
      url: '/system/forum_comment/post/'+postId,
      method: 'get',
      params: postId
    })
  }
