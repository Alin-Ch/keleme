// request.js

const BASE_URL = 'http://localhost:8080/api'; // 设置基础路径

function request(method, url, data) {
  return new Promise((resolve, reject) => {
    const userInfo = wx.getStorageSync('userinfo'); // 使用 wx.getStorageSync 获取本地存储的用户信息

    // 判断是否存在 userinfo
    const header = {
      'content-type': 'application/json' // 设置请求头为 JSON 格式
    };
    if (userInfo && userInfo.token) {
      header.Token = userInfo.token; // 添加 token 到请求头
    }

    wx.request({
      url: BASE_URL + url, // 拼接完整的请求 URL
      method: method,
      data: data,
      header: header,
      success: (res) => {
        resolve(res.data);
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}


function get(url, params) {
  return request('GET', url, params);
}

function post(url, data) {
  return request('POST', url, data);
}

function del(url, data) {
  return request('DELETE', url, data);
}

export {
  get,
  post,
  del
};