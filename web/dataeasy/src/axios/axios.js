/**
 * axios封装
 * 请求拦截、响应拦截、错误统一处理
 */
import axios from 'axios'

// 创建axios实例
const service = axios.create({
    timeout: 1000 * 10
})

// 请求地址 默认
service.defaults.baseURL = 'http://localhost:8080'

// 设置post请求头
service.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

//get请求
export const get = (url, params ={}) => {
    return service.get(url, {params} )
}

//post请求
export const post = (url, data = {}) => {
    return service.post(url, data )
}



//请求拦截器
service.interceptors.request.use((config) => {
    return config
}, err => {
    console.log(err)
})

//响应拦截器
service.interceptors.response.use((response) => {
    return response.data
}, error => {
    return error.data
})


export default service