// 存放所以获取商品的接口请求
import request from '@/utils/request'

export const getGoodsList = (params) => {
  return request.get('/api/items/list', { params })
}

export const getGoodsDetail = (id) => {
  return request.get(`/api/items/${id}`)
}

export const getSearchList = (params) => {
  return request.get('/api/items/search', { params })
}
