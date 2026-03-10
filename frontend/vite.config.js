import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8083',
        changeOrigin: true,
        secure: false
      },
      '/heritage': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/inheritor': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/auth': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/image': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/carousel': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/culture-news': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/comment': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/favorite': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/activity': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/statistics': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/dashboard': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/user/': {
        target: 'http://localhost:8083/api',
        changeOrigin: true,
        secure: false
      },
      '/uploads': {
        target: 'http://localhost:8083',
        changeOrigin: true,
        secure: false
      }
    }
  },
  build: {
    // 性能优化配置
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true
      }
    },
    // 代码分割
    rollupOptions: {
      output: {
        manualChunks: {
          // 将第三方库单独打包
          vendor: ['vue', 'vue-router', 'element-plus'],
          // 将编辑器相关库单独打包
          editor: ['@wangeditor/editor-for-vue', 'tinymce', '@tinymce/tinymce-vue'],
          // 将图表库单独打包
          chart: ['echarts']
        }
      }
    }
  }
})