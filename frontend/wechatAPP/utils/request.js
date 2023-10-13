// request.js

let baseURL = 'http://localhost:8080/api'; // 设置默认的基础 URL
const userinfo = wx.getStorageSync('userinfo');

// 设置默认请求头
const defaultHeader = {
  'Content-Type': 'application/json;charset=UTF-8', // 默认请求头
  // 其他自定义请求头...
};

function setTokenHeader(headers) {
  if (userinfo && userinfo.token) {
    headers['Authorization'] = `Bearer ${userinfo.token}`;
  }
}

function request(url, method, data) {
  return new Promise((resolve, reject) => {
    const headers = {
      ...defaultHeader
    }; // 复制默认请求头

    setTokenHeader(headers); // 设置Token请求头

    wx.request({
      url: baseURL + url,
      method: method,
      data: data,
      header: headers, // 使用设置好的请求头
      success: (res) => {
        resolve(res.data);
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}

module.exports = {
  // 当需要请求不同的后端接口，就可以调用这个接口
  setBaseURL: (url) => {
    baseURL = url; // 设置基础 URL
  },
  get: (url, data) => request(url, 'GET', data),
  post: (url, data) => request(url, 'POST', data),
  put: (url, data) => request(url, 'PUT', data),
  delete: (url, data) => request(url, 'DELETE', data),
};