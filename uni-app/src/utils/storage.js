// 约定一个通用的键名
const USER_KEY = 'shopping_demo'
const IDLE_KEY = 'idle_demo'
const HISTORY_KEY = 'history_demo'

/**
 * 获取用户信息
 */
export const getInfo = () => {
  const defaultObj = { token: '', userId: '' }
  const result = localStorage.getItem(USER_KEY)
  return result ? JSON.parse(result) : defaultObj
}

/**
 * 设置用户信息
 */
export const setInfo = (obj) => {
  localStorage.setItem(USER_KEY, JSON.stringify(obj))
}

/**
 * 删除用户信息
 */
export const removeInfo = () => {
  localStorage.removeItem(USER_KEY)
}

/**
 * 获取top10商品信息
 */
export const getIdleInfo = () => {
  const defaultObj = { id: '', idleName: '', idleSellCount: '' }
  const result = localStorage.getItem(IDLE_KEY)
  return result ? JSON.parse(result) : defaultObj
}

/**
 * 设置top10商品信息
 */
export const setIdleInfo = (obj) => {
  localStorage.setItem(IDLE_KEY, JSON.stringify(obj))
}

/**
 * 删除top10商品信息
 */
export const removeIdleInfo = () => {
  localStorage.removeItem(IDLE_KEY)
}

/**
 * 根据用户id获取历史记录信息
 */
export const getHistoryInfo = (userId) => {
  const key = userId ? (HISTORY_KEY + '_' + userId) : HISTORY_KEY + '_guest'
  const result = localStorage.getItem(key)
  return result ? JSON.parse(result) : []
}

/**
 * 根据用户id设置历史记录信息
 */
export const setHistoryInfo = (userId, obj) => {
  const key = userId ? (HISTORY_KEY + '_' + userId) : HISTORY_KEY + '_guest'
  localStorage.setItem(key, JSON.stringify(obj))
}
