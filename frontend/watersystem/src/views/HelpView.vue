<template>
    <div>
        <div class="title">渴了么系统使用帮助</div>
        <el-collapse accordion v-model="activeNames" @change="handleChange">
            <el-collapse-item title="后端" name="1">
                <div>所需环境：JDK1.8、Maven3.6.3、MySQL8</div>
                <div>1、在Navicat中新建数据库water，然后运行本项目提供的数据库water.sql</div>
                <div>2、环境搭建好之后，直接在IDEA导入本项目后端，等待Maven加载完毕依赖，即可开启后端</div>
            </el-collapse-item>
            <el-collapse-item title="后台管理系统" name="2">
                <div>所需环境：Node18.17.0</div>
                <div>1、npm install （如果安装失败，请配置淘宝镜像或者安装cnpm，这里推荐用cnpm）</div>
                <div>2、npm run serve</div>
                <div>3、如果用户头像或者商品图片无法显示，就是数据库信息与后端files文件夹下的图片不对应，自行上传图片即可。</div>
            </el-collapse-item>
            <el-collapse-item title="前端小程序会员端" name="3">
                <div>所需环境：微信开发者工具</div>
                <div>1、在微信开发者工具中导入本项目的小程序会员端，可进行二次开发或者直接打包上线。（注意：如果打包上线，要将utils文件夹下的req.js中的 "BASE_URL" 改成你自己服务器的公网地址）
                </div>
                <div>2、项目中涉及图片的位置，图片路径都是以"http://localhost:8080/api/files/"开头，如部署上线，请挨个将其改为服务器公网地址</div>
            </el-collapse-item>
            <el-collapse-item title="前端小程序骑手端" name="4">
                <div>所需环境：微信开发者工具</div>
                <div>1、在微信开发者工具中导入本项目的小程序骑手端，可进行二次开发或者直接打包上线。</div>
                <div>2、本项目较为简单，二开用户可根据自己需求更改。</div>
            </el-collapse-item>
        </el-collapse>
    </div>
</template>
<script>
export default {
    data() {
        return {
            activeNames: ['1']
        }
    },
    methods: {
        handleChange(val) {
            console.log(val);
        }
    },
};
</script>
<style lang='scss' scoped>
.title {
    font-weight: bold;
    font-size: 30px;
    color: #7f7f7f;
    width: 100%;
    text-align: center;
    margin-bottom: 20px;
}
</style>