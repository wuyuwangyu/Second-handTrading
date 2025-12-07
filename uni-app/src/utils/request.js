import axios from 'axios'
import { Toast } from 'vant'
// 创建axios实例，不会污染原始的axios实例
const instance = axios.create({
  baseURL: '',
  timeout: 10000,
  headers: { 'X-Custom-Header': 'foobar' }
})
// 自定义配置--请求/响应 拦截器
// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  // 开启loading，静止背景点击
  Toast.loading({
    message: '加载中...',
    // loadingType: 'spinner',// 圆形加载
    forbidClick: true, // 禁用点击
    duration: 0 // 不关闭
  })
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
  // 2xx 范围内的状态码都会触发该函数。
  // 对响应数据做点什么
  const res = response.data
  if (res.code !== 200) {
    Toast(res.msg)
    return Promise.reject(res.msg)
  } else {
    Toast.clear()
  }
  return res
}, function (error) {
  // 超出 2xx 范围的状态码都会触发该函数。
  // 对响应错误做点什么
  return Promise.reject(error)
})
// 导出配置好的axios实例
export default instance
