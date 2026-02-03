import api from './index'

export const heritageApi = {
  list: (status = 1, page = 1, pageSize = 10) => {
    return api.get('/heritage/list', { params: { status, page, pageSize } })
  },
  getById: (id) => {
    return api.get(`/heritage/get/${id}`)
  },
  filter: (categoryId, regionId, status = 1) => {
    return api.get('/heritage/filter', { params: { categoryId, regionId, status } })
  },
  save: (data) => {
    return api.post('/heritage/save', data)
  },
  update: (data) => {
    return api.post('/heritage/update', data)
  },
  delete: (id) => {
    return api.delete(`/heritage/delete/${id}`)
  }
}