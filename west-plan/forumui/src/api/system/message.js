import request from '@/utils/request'

// 查询校友工作感悟列表
export function   getMyMessage() {
  return request({
    url: '/system/message/my',
    method: 'get',

  })
}

// 提交留言
export function   addMessage(data) {
   return request({
    url: '/system/message',
    method: 'post',
    data: data
  })

  }
  // 删除留言
export function delMessage(id) {
  return request({
    url: '/system/message/' + id,
    method: 'delete'
  })
}
//管理员获取留言列表
export function getAdminList() {
  return request({
    url: '/system/message/admin/list',
    method: 'get',

  })
}
// 管理员回复留言
 export function replyMessage(data) {
  return request({
    url: '/system/message/reply',
    method: 'post',
    data: data
  })
 }



