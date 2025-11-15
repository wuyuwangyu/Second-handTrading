import axios from 'axios';

/* axios功能封装  */

const service = axios.create({
    timeout: 5000,
    baseURL: 'http://localhost:8080',
    withCredentials: true
});

// request interceptor(请求拦截器)
service.interceptors.request.use(
    config => {
        // 添加认证token到请求头
        const token = localStorage.getItem('token') || sessionStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        // 设置Content-Type
        if (!config.headers['Content-Type']) {
            config.headers['Content-Type'] = 'application/json;charset=UTF-8';
        }

        // 添加时间戳防止缓存
        if (config.method === 'get') {
            config.params = {
                ...config.params,
                _t: Date.now()
            };
        }

        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

// response interceptor（接收拦截器）
service.interceptors.response.use(
    response => {
        if (response.status === 200) {
            console.log(response.data);
            return response.data;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log('err' + error);
        return Promise.reject();
    }
);

export default service;
