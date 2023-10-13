<template>
  <div class="login-container">
    <div
      style="width: 400px; height: 400px; margin: 150px auto; background-color:#fff;opacity: 0.8;box-shadow: rgba(222, 229, 236, 0.2) 0px 8px 24px; border-radius: 10px;">
      <div style="widows: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #7f7f7f;">
        渴了么</div>
      <div style="margin-top: 25px; text-align: center; height: 320px;">
        <el-form :model="user">
          <el-form-item>
            <el-input v-model.trim="user.phone" prefix-icon="el-icon-user-solid" style="width: 80%;"
              placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="user.password" prefix-icon="el-icon-lock" show-password style="width: 80%;"
              placeholder="请输入密码"></el-input>
          </el-form-item>
          <!-- 图形验证码 -->
          <el-form-item>
            <div style="display: flex; justify-content: center;">
              <el-input v-model.trim="user.verCode" prefix-icon="el-icon-folder-checked" style="width: 42%;margin-right: 10px;"
                placeholder="请输入验证码">
              </el-input>
              <!-- 设置获取地址url -->
              <img :src="captchaUrl" @click="clickImg()" width="140px" height="33px" />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%; margin-top: 10px;" type="primary" v-loading.fullscreen.lock="fullscreenLoading"
              @click="login()">登录</el-button>
            <el-link :underline="false" href="http://localhost:7070/register" style="width: 30%; margin-top: 10px;
                        margin-left: 50%;" type="primary">没有账户?去注册</el-link>
          </el-form-item>
        </el-form>
      </div>

    </div>
  </div>
</template>
<script>
import request from '@/utils/request';
export default {
  data() {
    return {
      user: {
        phone: '18888888888',
        password: '123456',
      },
      key: '',
      captchaUrl: '',
      fullscreenLoading: false
    }
  },
  //页面加载的时候，做一些事情，在create里面
  mounted() {
    this.key = Math.random(),
      this.captchaUrl = 'http://localhost:8080/api/captcha?key=' + this.key
  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    login() {
      request.post('/user/login?key=' + this.key, this.user).then(res => {
        if (res.code === '200') {
          console.log(res);
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          setTimeout(() => {
            loading.close();
            //登录成功
            this.$message.success('登录成功');
            //将用户信息保存在localStorage
            localStorage.setItem('user', JSON.stringify(res.data));
            //登录成功后跳转系统首页
            this.$router.push('/');
          }, 1200);
        } else {
          this.$message.error(res.msg);
          //输入的验证码不正确则会刷新验证码
          this.key = Math.random(),
            this.captchaUrl = 'http://localhost:8080/api/captcha?key=' + this.key,
            this.user.verCode = ''
        }
      });
    },
    // 点击图片刷新验证码
    clickImg() {
      this.key = Math.random(),
      this.captchaUrl = 'http://localhost:8080/api/captcha?key=' + this.key
    },

  }
}
</script>
<style scoped>
.login-container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/login-background.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
