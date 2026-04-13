import request from '@/utils/request'

// 修改好友申请状态
export function changeFriendApplyStatus(data) {
    return request({
        url: '/system/friendApply/changeStatus',
        method: 'put',
        data: data
    })
}
