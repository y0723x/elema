import { useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'
// import { useStore } from 'vuex'

// elema后端请求
// 前缀路径
let base = ''

const elemaBack = axios.create({
    baseURL: base,
    timeout: 10000
})

const router = useRouter()

// 获取token
// const Authorization = `Bearer `+window.localStorage.token+'ss'

// 响应式拦截器的返回信息
let resp = {}

// 请求式拦截器
elemaBack.interceptors.request.use((config) => {
    // 成功请求,将token放在每个请求头上
    config.headers['Authorization'] = sessionStorage.getItem('token') || ''
    return config;
    // console.log('请求成功')
}, (error) => {
    // 失败请求
    return Promise.reject(error)
    // console.log('请求失败')
})

// 响应式拦截器
elemaBack.interceptors.response.use((success) => {
    // 成功调用到接口
    if (success.status && success.status == 200) {
        // 返回错误信息
        if (success.data.code == 500 || success.data.code == 401 || success.data.code == 403) {
            // resp = {
            //     code: success.data.code,
            //     message: success.data.message
            // }
            // return resp
            ElMessage.error({ message: success.data.message })
            return
        }
        // 返回正确信息
        if (success.data.message) {
            // resp = {
            //     code: success.data.code,
            //     data: success.data
            // }
            // return resp
            ElMessage.success({ message: success.data.message })
        }
    }
    // console.log('请求成功：',success.data)
    // return Promise.resolve(success.data)
    return success.data
}, (error) =>{
    // 未调用到接口
    if (error.response.code == 504 || error.response.code == 404) {
        // resp = {
        //     code: error.response.code,
        //     message: '服务器被吃啦( ╯□╰ )'
        // }
        // return resp
        ElMessage.error({ message: '服务器被吃啦( ╯□╰ )' })
    } else if (error.response.code == 403) {
        // resp = {
        //     code: error.response.code,
        //     message: '权限不足，请联系管理员'
        // }
        // return resp
        ElMessage.error({ message: '权限不足，请联系管理员' })
    } else if (error.response.code == 401) {
        // resp = {
        //     code: error.response.code,
        //     message: '尚未登陆，请登录'
        // }
        // return resp
        ElMessage.error({ message: '尚未登录，请登录' })
        router.replace('/')
    } else if (error.response.data.message) {
        // resp = {
        //     code: error.response.code,
        //     message: '未知错误'
        // }
        // return resp
        ElMessage.error({ message: error.response.data.message })
    } else {
        ElMessage.error({ message: '未知错误' })
    }
    return
})

// fastmock请求
const instance = axios.create({
    baseURL: 'https://www.fastmock.site/mock/8010df9cc430474a725248a21d34fec1/elema',
    timeout: 10000
})

export const get = (url, param = {}) => {
    return new Promise((resolve, reject) => {
        instance.get(url, { param }).then((response) => {
            resolve(response.data)
        }, (error) => {
            reject(error)
        })
    })
}

export const post = (url, data = {}) => {
    return new Promise((resolve, reject) => {
        instance.post(url, data, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            resolve(response.data)
        }, error => {
            reject(error)
        })
    })
}

export const elemaGet = (url, params = {}) => {
    return new Promise((resolve, reject) => {
        elemaBack.get(url, { params }).then((response) => {
            resolve(response.data)
        }, (error) => {
            reject(error)
        })
    })
}

export const elemaPost = (url, params = {}) => {
    return new Promise((resolve, reject) => {
        elemaBack.post(url, params, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            resolve(response.data)
        }, (error) => {
            reject(error)
        })
    })
}

export const postRequest = (url, data) => {
    return elemaBack({
        method: 'post',
        url: url,
        data: data
    })
}

export const putRequest = (url, data) => {
    return elemaBack({
        method: 'put',
        url: url,
        data: data
    })
}

export const getRequest = (url, params) => {
    return elemaBack({
        method: 'get',
        url: url,
        params: params
    })
}

export const deleteRequest = (url, data) => {
    return elemaBack({
        method: 'delete',
        url: url,
        data: data
    })
}