<template>
  <div>
    <el-container>
      <el-header style="background-color: #5e5d5d;">
        <img src="@/assets/logo.png" style="width: 30px; position: relative; top: 10px;">
        <span style="font-size: 20px; margin-left: 15px; color: white;">渴了么后台管理系统</span>
        <span class="current-time">{{ currentTime }}</span>


        <el-dropdown style="float: right;">
          <span class="el-dropdown-link" style="color: white; font-size: 13px;">
            <el-image style="width: 35px; height: 35px; border-radius: 50%; top: 15px"
              :src="'http://localhost:8080/api/files/' + user.avatar">
            </el-image>
            {{ user.username }}
            <i class="el-icon-arrow-down el-icon--right "></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>
              <div @click="logout()">退出登录</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow: hidden; min-height: 100vh; background-color: #545c64; width: 250px;">
        <el-menu default-active="/" :unique-opened="true" router background-color="#545c64" text-color="#fff"
          active-text-color="#ffd04b">

          <el-menu-item index="/">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </el-menu-item>

          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/users">用户管理</el-menu-item>
              <el-menu-item index="/ticket">水票管理</el-menu-item>
              <el-menu-item index="/log">日志管理</el-menu-item>
              <el-menu-item index="/address">地址管理</el-menu-item>

            </el-menu-item-group>

          </el-submenu>

          <!-- 水站管理 -->
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-opportunity"></i>
              <span>水站管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/station">水站管理</el-menu-item>
              <el-menu-item index="/employee">水站人员</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <!-- 商品管理 -->
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-opportunity"></i>
              <span>商品管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/good">商品列表</el-menu-item>
              <el-menu-item index="/type">商品分类</el-menu-item>
            </el-menu-item-group>
          </el-submenu>

          <!-- 订单管理 -->
          <el-submenu index="5">
            <template slot="title">
              <i class="el-icon-s-opportunity"></i>
              <span>订单管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/order">订单列表</el-menu-item>
              <!-- <el-menu-item index="5-2">订单派发</el-menu-item>
              <el-menu-item index="5-3">退款审核</el-menu-item> -->
            </el-menu-item-group>
          </el-submenu>
          <!-- 文章资讯 -->
          <el-submenu index="6">
            <template slot="title">
              <i class="el-icon-chat-line-square"></i>
              <span>文章资讯</span>
            </template>
            <el-menu-item-group>
              <!-- <el-menu-item index="6-1">企业资质</el-menu-item> -->
              <el-menu-item index="/help">帮助文档</el-menu-item>
              <el-menu-item index="/about">关于我们</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTime: "",
      user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
    }
  },
  methods: {
    logout() {
      this.$router.push('/login');
      localStorage.removeItem('user');
    }
  },
  /* 加入钩子函数判断是否含有token通过localStorage.getItem('user')获取user对象，
  如果不存在，则说明没有token，此时可以使用this.$router.push('/login')跳转到登录界面。 */
  created() {
    setInterval(() => {
      const date = new Date();
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hour = String(date.getHours()).padStart(2, '0');
      const minute = String(date.getMinutes()).padStart(2, '0');
      const second = String(date.getSeconds()).padStart(2, '0');
      this.currentTime = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
    }, 1000);
  },

}
</script>
<style>
.el-menu {
  border-right: none !important;
}
.current-time {
  font-size: 18px;
  color: #7f7f7f;
  position: absolute;
  top: 65px;
  right: 10px;
}

</style>