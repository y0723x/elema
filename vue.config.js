// const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })

// 配置代理对象
let proxyObj = {}

proxyObj['/'] = {
  // websocket
  ws: false,
  // 目标地址
  target: 'http://localhost:8081/',
  // 发送请求host会变成target
  changeOrigin: true,
  // 检查代理地址
  logLevel: 'debug',
  // 不重写地址
  pathReWrite: {
    '^/': '/'
  }
}

module.exports = {
  devServer: {
    host: 'localhost',
    port: 8080,
    proxy: proxyObj
  },
  publicPath: './'
}
