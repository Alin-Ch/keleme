<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入姓名" v-model="params.name" clearable></el-input>
      <el-input style="width: 200px; margin-left: 5px;" placeholder="请输入电话" v-model="params.phone" clearable></el-input>
      <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
      <el-button type="primary" style="margin-left: 10px;" @click="add()">新增</el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="avatar" label="头像">
          <template v-slot="scope">
            <el-image style="width: 65px; height: 65px; border-radius: 50%;"
              :src="'http://localhost:8080/api/files/' + scope.row.avatar"
              :preview-src-list="['http://localhost:8080/api/files/' + scope.row.avatar]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="姓名"></el-table-column>
        <el-table-column prop="gender" label="性别"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="role" label="角色"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- scope.row获取当前这一行的数据 -->
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.userid)">
              <el-button slot="reference" type="danger" style="margin-left: 5px;">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页功能 -->
    <div style="margin-top: 15px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="params.pageNum"
        :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <!-- 弹出框 -->
    <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :rules="userRules" ref="userForm">
        <el-form-item label="姓名" prop="username" label-width="15%">
          <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="头像" label-width="15%">
          <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload">
            <el-button size="small" type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="性别" prop="gender" label-width="15%">
          <el-radio v-model="form.gender" label="男">男</el-radio>
          <el-radio v-model="form.gender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="电话" prop="phone" label-width="15%">
          <el-input v-model.number="form.phone" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role" label-width="15%">
          <!--  v-model="form.type_id"中的type_id为商品表中的字段-->
          <el-select v-model="form.role" placeholder="请选择" style="width: 90%">
            <el-option label="商家" value="ROLE_BOSS"></el-option>
            <el-option label="消费者" value="ROLE_CONSUMER"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit('userForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import request from '@/utils/request';
export default {
  data() {
    return {
      params: {
        // 按条件查询的参数name和phone
        name: '',
        phone: '',
        // 当前页码
        pageNum: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      tableData: [],
      //总条数
      total: 0,
      dialogFormVisible: false,
      form: {},
      userRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在2到5个字', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: this.validatePhoneNumber, trigger: 'blur' },
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' }
        ]
      }
    }
  },
  //页面加载时要做一些事情
  created() {
    this.findBySearch();
  },
  //定义一些页面上控件发出事件调用的方法
  methods: {
    findBySearch() {
      request.get("/user/search", {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    //表单正则校验正整数规则
    validatePositiveInteger(rule, value, callback) {
      const reg = /^[1-9]\d*$/;
      if (!reg.test(value)) {
        callback(new Error('请输入正整数'));
      } else {
        callback();
      }
    },
    //表单正则校验手机号规则
    validatePhoneNumber(rule, value, callback) {
      const reg = /^1[345789]\d{9}$/;
      if (!reg.test(value)) {
        callback(new Error('请输入正确的手机号'));
      } else {
        callback();
      }
    },
    // 点击新增和修改时将对话框显示出来
    add() {
      this.dialogFormVisible = true;
      this.$refs.userForm.resetFields(); // 清空表单校验状态和输入值
      this.form = {};
    },
    handleCancel() {
      // 关闭对话框前重置表单校验状态
      this.$refs.userForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    handleClose() {
      // 关闭对话框前重置表单校验状态
      this.$refs.userForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    // 重置表单为原始正确的内容
    resetForm() {
      // 将当前表单对象的值重置为原始正确的内容
      this.form = Object.assign({}, this.originalForm);
    },
    // 提交表单获取这一行的数据,并打开表单，数据的回显
    edit(obj) {
      // 备份原始正确的表单数据
      this.originalForm = Object.assign({}, obj);
      // 将表单数据赋值为编辑对象的数据
      this.form = Object.assign({}, obj);
      // 清除不正确的校验状态
      this.$nextTick(() => {
        this.$refs.userForm.clearValidate();
      });
      this.dialogFormVisible = true;
    },
    // 将搜索框中的数据清除，然后重新查询
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        phone: '',
      }
      this.findBySearch();
    },
    // 分页功能
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    // 将新增或者修改的数据传回后台
    submit(userFrom) {
      this.$refs[userFrom].validate((valid) => {
        if (valid) {
          // 校验通过后发送请求
          request.post('/user', this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("操作成功");
              // 操作成功后关闭对话框
              this.dialogFormVisible = false;
              this.findBySearch();
            } else {
              this.$message.error(res.msg);
            }
          })
        } else {
          this.$message.error('请填写完整信息');
        }
      })
    },
    // 根据id删除某条数据
    del(id) {
      request.delete("/user/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    successUpload(res) {
      this.form.avatar = res.data;
    }
  }
}
</script>
