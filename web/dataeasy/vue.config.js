const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    //错误弹窗
    client: {
      overlay: true,
    },

    //代理
    proxy: {
      'api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
