import request from '@/utils/request'

// 富文本编辑器图片上传
export function uploadImage(file) {
  const formData = new FormData()
  formData.append('file', file)

  return request({
    url: '/common/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
