<template>
   <div>
      <!-- 顶部编辑按钮 -->
      <div style="margin-bottom: 12px;">
         <el-input style="width: 200px;" placeholder="请输入姓名" v-model="params.name" clearable></el-input>
         <el-input style="width: 200px; margin-left: 5px;" placeholder="请输入电话" v-model="params.phone" clearable></el-input>
         <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
         <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
         <el-button type="primary" style="margin-left: 10px;" @click="add()">新增</el-button>
      </div>

      <!-- 内容区域 -->
      <div>
         <el-table :data="tableData" style="width: 100%">
            <el-table-column fixed width="100" prop="empid" label="用户ID"></el-table-column>
            <el-table-column width="150" prop="empavatar" label="头像">
               <template v-slot="scope">
                  <el-image style="width: 65px; height: 65px; border-radius: 50%;"
                     :src="'http://localhost:8080/api/files/' + scope.row.empavatar"
                     :preview-src-list="['http://localhost:8080/api/files/' + scope.row.empavatar]">
                  </el-image>
               </template>
            </el-table-column>
            <el-table-column width="150" prop="empname" label="姓名"></el-table-column>
            <el-table-column width="150" prop="gender" label="性别"></el-table-column>
            <el-table-column width="150" prop="age" label="年龄"></el-table-column>
            <el-table-column width="150" prop="phone" label="电话"></el-table-column>
            <el-table-column width="150" prop="stationName" label="所属水站"></el-table-column>
            <el-table-column width="150" fixed="right" label="操作">
               <template slot-scope="scope">
                  <!-- scope.row获取当前这一行的数据 -->
                  <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                  <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.empid)">
                     <el-button slot="reference" type="danger" style="margin-left: 5px;">删除</el-button>
                  </el-popconfirm>
               </template>
            </el-table-column>
         </el-table>
      </div>

      <!-- 分页功能 -->
      <div style="margin-top: 15px;">
         <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
            :current-page="params.pageNum" :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize"
            layout="total, sizes, prev, pager, next, jumper" :total="total">
         </el-pagination>
      </div>

      <!-- 弹出框 -->
      <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="35%">
         <el-form :model="form" :rules="employeeRules" ref="employeeForm">
            <el-form-item label="姓名" prop="empname" label-width="23%">
               <el-input v-model="form.empname" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="头像" label-width="23%">
               <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload">
                  <el-button size="small" type="primary">上传头像</el-button>
               </el-upload>
            </el-form-item>
            <el-form-item label="性别" prop="gender" label-width="23%">
               <el-radio v-model="form.gender" label="男">男</el-radio>
               <el-radio v-model="form.gender" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="年龄" prop="age" label-width="23%">
               <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="电话" prop="phone" label-width="23%">
               <el-input v-model.number="form.phone" autocomplete="off" style="width: 90%"></el-input>
            </el-form-item>
            <el-form-item label="所属水站" prop="station_id" label-width="23%">
               <!-- 下拉选择水站，"form.empid"要是自己本张表的id -->
               <el-select v-model="form.station_id" placeholder="请选择" style="width: 90%">
                  <el-option v-for="item in typeObjs" :key="item.sid" :label="item.sname" :value="item.sid">
                  </el-option>
               </el-select>
            </el-form-item>
         </el-form>
         <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submit('employeeForm')">确 定</el-button>
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
         // 水站数组
         typeObjs: [],
         employeeRules: {
            empname: [
               { required: true, message: '请输入员工姓名', trigger: 'blur' }
            ],
            gender: [
               { required: true, message: '请输入员工性别', trigger: 'blur' }
            ],
            age: [
               { required: true, message: '请输入员工年龄', trigger: 'blur' }
            ],
            phone: [
               { required: true, message: '请输入员工电话', trigger: 'blur' },
               { validator: this.validatePhoneNumber, trigger: 'blur' },
            ],
            station_id: [
               { required: true, message: '请输入所属水站', trigger: 'blur' }
            ]
         }
      }
   },
   //页面加载时要做一些事情
   created() {
      this.findBySearch();
      this.getAllStation()
   },
   //定义一些页面上控件发出事件调用的方法
   methods: {
      findBySearch() {
         request.get("/employee/search", {
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
      // 获取所有水站
      getAllStation() {
         request.get("/station").then(res => {
            if (res.code === '200') {
               this.typeObjs = res.data;
               console.log(this.typeObjs)
            } else {
               this.$message.error(res.msg);
            }
         })
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
         this.$refs.employeeForm.resetFields(); // 清空表单校验状态和输入值
         this.form = {};
      },
      handleCancel() {
         // 关闭对话框前重置表单校验状态
         this.$refs.employeeForm.clearValidate();
         // 关闭对话框
         this.dialogFormVisible = false;
      },
      handleClose() {
         // 关闭对话框前重置表单校验状态
         this.$refs.employeeForm.clearValidate();
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
            this.$refs.employeeForm.clearValidate();
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
      submit(employeeForm) {
         this.$refs[employeeForm].validate((valid) => {
            if (valid) {
               request.post('/employee', this.form).then(res => {
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
         });
      },
      // 根据id删除某条数据
      del(empid) {
         request.delete("/employee/" + empid).then(res => {
            if (res.code === '200') {
               this.$message.success("操作成功");
               this.findBySearch();
            } else {
               this.$message.error(res.msg);
            }
         })
      },
      successUpload(res) {
         this.form.empavatar = res.data;
      }
   }
}
</script>
 