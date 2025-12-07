// 存放所以登录的接口请求
import request from '@/utils/request'
// 获取图形验证码
export const getVerifyCode = () => {
  return request.get('/api/users/verify-code')
}

// 获取短信验证码
export const getSmsCode = (data) => {
  return request.post('/api/users/sms-code', data)
}

// 手机号登录
export const loginBySms = (data) => {
  return request.post('/api/users/login/sms', data)
}

// 账号密码登录
export const loginByAccount = (data) => {
  return request.post('/api/users/login/password', data)
}

// 验证用户token有效性
export const verifyToken = (token) => {
  return request.post('/api/users/verify-token', { token: token })
}
