import axios from "axios";

// 创建一个axios对象出来
const request = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 50000
});

//request拦截器
// 可以让请求发送前对请求做一些处理
//如统一的token，对请求参数加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=UTF-8';

    const user = localStorage.getItem('user');
    if (user) {
        config.headers['token'] = JSON.parse(user).token; //设置请求头
    }
    //config.headers['token'] = user.token; //设置请求头
    return config;
},error => {
    return Promise.reject(error)
});

//response拦截器
//可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        //response.data即为后端返回的R
        let res = response.data;
        //兼容服务端返回的字符串数据
        if(typeof res === "string"){
            res = res ? JSON.parse(res) : res;
        }
        return res;
    },error => {
        console.log('error' + error); //for debug
        return Promise.reject(error)
    });

export default request;
