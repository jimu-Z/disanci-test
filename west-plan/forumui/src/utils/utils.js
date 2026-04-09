import dayjs from 'dayjs';
//import { getPermissionList } from '@/utils/auth';

/**
 * 时间格式化
 * @param {Date|String|Number} time 时间
 * @param {String} format 格式，默认YYYY-MM-DD HH:mm:ss
 */
export function formatTime(time, format = 'YYYY-MM-DD HH:mm:ss') {
  if (!time) return '-';
  return dayjs(time).format(format);
}



/**
 * 分割附件地址为数组（适配后端逗号分隔的postAttach字段）
 * @param {String} urlStr 逗号分隔的地址字符串
 * @returns {Array} 附件地址数组
 */
export function splitAttachUrl(urlStr) {
  if (!urlStr) return [];
  return urlStr.split(',').filter(item => item.trim());
}

/**
 * 从附件URL中提取文件名
 * @param {String} url 附件地址
 * @returns {String} 文件名
 */
export function getFileName(url) {
  if (!url) return '未知文件';
  return url.substring(url.lastIndexOf('/') + 1);
}

/**
 * 递归统计评论总数（含子评论）
 * @param {Array} list 评论树形列表
 * @returns {Number} 评论总数
 */
export function countCommentTotal(list) {
  let total = list.length;
  list.forEach(item => {
    if (item.children && item.children.length > 0) {
      total += countCommentTotal(item.children);
    }
  });
  return total;
}
