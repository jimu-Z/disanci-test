import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

// 获取当前登录用户ID
export function getCurrentUserId() {
  const user = JSON.parse(localStorage.getItem('userInfo') || '{}')
  return user.userId || user.id || null
}
