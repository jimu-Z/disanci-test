import request from '@/utils/request'
// ========== 文件上传API（帖子附件专用） ==========

// 上传文件到OSS（返回fileId，适配sys_file表）
export function upload(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data,
    headers: { 'Content-Type': 'multipart/form-data' }
  })
}

