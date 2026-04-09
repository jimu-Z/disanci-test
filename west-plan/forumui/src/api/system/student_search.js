import request from '@/utils/request'

// 发起好友申请
export function addFriendApply(data) {
  return request({
    url: '/system/friendApply/add',
    method: 'post',
    data: data
  })
}
