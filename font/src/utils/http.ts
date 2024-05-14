import axios, { AxiosInstance, AxiosResponse } from 'axios';

// 创建自定义的 Axios 实例
const customAxios: AxiosInstance = axios.create({
    baseURL: 'http://127.0.0.1:9007/taki', // 设置基本的请求 URL
    timeout: 5000, // 设置请求超时时间
    headers: {
        'Content-Type': 'application/json', // 设置请求头
    },
});

// 添加响应拦截器
customAxios.interceptors.response.use(
    (response: AxiosResponse) => {
        // 在接收响应数据之前做一些处理，例如解析数据等
        return response;
    },
    (error: any) => {
        // 处理响应错误
        return Promise.reject(error);
    }
);

export default customAxios;