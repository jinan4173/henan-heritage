import api from './index'

export const imageApi = {
  listByHeritageId: (heritageId) => {
    return api.get(`/image/list/${heritageId}`)
  },
  upload: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.post('/image/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  },
  save: (data) => {
    return api.post('/image/save', data)
  },
  delete: (id) => {
    return api.delete(`/image/delete/${id}`)
  },
  deleteByHeritageId: (heritageId) => {
    return api.delete(`/image/deleteByHeritageId/${heritageId}`)
  }
}