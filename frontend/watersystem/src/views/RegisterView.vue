<template>
  <div>
    <div
      style="width: 400px; height: 430px; margin: 150px auto; background-color: rgba(122, 153, 206, 0.5); border-radius: 10px;">
      <div style="widows: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #4a5ed0;">
        欢迎注册</div>
      <div style="margin-top: 25px; text-align: center; height: 320px;">
        <el-form :model="user">
          <el-form-item>
            <el-input v-model="user.phone" prefix-icon="el-icon-mobile-phone" style="width: 80%;"
              placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="user.username" prefix-icon="el-icon-user-solid" style="width: 80%;"
              placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="user.password" prefix-icon="el-icon-lock" show-password style="width: 80%;"
              placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <!-- clearable可清空选择的内容 -->
            <el-select v-model="user.role" clearable placeholder="请选择" style="width: 80%">
              <el-option label="商家 " value="ROLE_BOSS">
              </el-option>
              <el-option label="客户 " value="ROLE_CONSUMER">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 80%; margin-top: 10px;" type="primary" v-loading.fullscreen.lock="fullscreenLoading"
              @click="register()">注册</el-button>
            <el-link :underline="false" href="http://localhost:7070/login" style="width: 30%; margin-top: 10px;
                      margin-left: 65%;" type="primary">返回登录</el-link>
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
      user: {},
      fullscreenLoading: false
    }
  },
  //页面加载的时候，做一些事情，在create里面
  created() {

  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    register() {
      request.post('/user/register', this.user).then(res => {
        if (res.code === '200') {
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          setTimeout(() => {
            loading.close();
            //登录成功
            this.$message({
              message: '注册成功',
              type: 'success'
            });
            // 注册成功后跳转登录页面
            this.$router.push('/login');
          }, 1500);
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    }
  }
};
</script>
