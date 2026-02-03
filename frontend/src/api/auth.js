import api from './index'

export const authApi = {
  login: async (username, password) => {
    try {
      console.log('发送登录请求:', { username, password })
      const response = await api.post('/auth/login', { username, password })
      console.log('登录请求响应:', response)
      return response
    } catch (error) {
      console.error('登录请求失败:', error)
      throw error
    }
  },
  test: () => {
    return api.get('/auth/test')
  }
}