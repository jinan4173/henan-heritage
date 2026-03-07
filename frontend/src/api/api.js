import api from './index'

// 非遗项目API
export const heritageApi = {
  // 获取非遗项目列表
  list: (status = 1, page = 1, pageSize = 10) => {
    return api.get('/heritage/list', { params: { status, page, pageSize } })
  },
  // 根据ID获取非遗项目详情
  getById: (id) => {
    return api.get(`/heritage/get/${id}`)
  },
  // 根据分类和地区筛选非遗项目
  filter: (categoryId, regionId, status = 1, page = 1, pageSize = 10) => {
    return api.get('/heritage/filter', { params: { categoryId, regionId, status, page, pageSize } })
  },
  // 保存非遗项目
  save: (data) => {
    return api.post('/heritage/save', data)
  },
  // 更新非遗项目
  update: (data) => {
    return api.post('/heritage/update', data)
  },
  // 删除非遗项目
  delete: (id) => {
    return api.delete(`/heritage/delete/${id}`)
  },
  // 获取非遗项目分类列表
  getCategories: () => {
    return api.get('/heritage/categories')
  },
  // 获取地区列表
  getRegions: () => {
    return api.get('/heritage/regions')
  }
}

// 传承人API
export const inheritorApi = {
  // 获取传承人列表
  list: (page = 1, pageSize = 10) => {
    return api.get('/inheritor/list', { params: { page, pageSize } })
  },
  // 根据ID获取传承人详情
  getById: (id) => {
    return api.get(`/inheritor/get/${id}`)
  },
  // 保存传承人信息
  save: (data) => {
    return api.post('/inheritor/save', data)
  },
  // 更新传承人信息
  update: (data) => {
    return api.post('/inheritor/update', data)
  },
  // 删除传承人
  delete: (id) => {
    return api.delete(`/inheritor/delete/${id}`)
  }
}

// 认证API
export const authApi = {
  // 登录
  login: async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password })
      return response
    } catch (error) {
      console.error('登录请求失败:', error)
      throw error
    }
  },
  // 测试
  test: () => {
    return api.get('/auth/test')
  }
}

// 图片API
export const imageApi = {
  // 根据非遗项目ID获取图片列表
  listByHeritageId: (heritageId) => {
    return api.get(`/image/list/${heritageId}`)
  },
  // 上传图片
  upload: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.post('/image/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  // 保存图片信息
  save: (data) => {
    return api.post('/image/save', data)
  },
  // 删除图片
  delete: (id) => {
    return api.delete(`/image/delete/${id}`)
  },
  // 根据非遗项目ID删除图片
  deleteByHeritageId: (heritageId) => {
    return api.delete(`/image/deleteByHeritageId/${heritageId}`)
  }
}

// 宣传API
export const promotionApi = {
  // 获取宣传列表
  list: (page = 1, limit = 10, keyword = '') => {
    return api.get('/promotion/list', { params: { page, limit, keyword } })
  },
  // 获取宣传总数
  count: () => {
    return api.get('/promotion/count')
  },
  // 根据ID获取宣传
  getById: (id) => {
    return api.get(`/promotion/get/${id}`)
  },
  // 保存宣传
  save: (data) => {
    return api.post('/promotion/save', data)
  },
  // 更新宣传
  update: (data) => {
    return api.put('/promotion/update', data)
  },
  // 删除宣传
  delete: (id) => {
    return api.delete(`/promotion/delete/${id}`)
  },
  // 批量删除宣传
  deleteBatch: (ids) => {
    return api.delete('/promotion/delete/batch', { data: { ids } })
  }
}

// 导出所有API
export default {
  heritage: heritageApi,
  inheritor: inheritorApi,
  auth: authApi,
  image: imageApi,
  promotion: promotionApi
}