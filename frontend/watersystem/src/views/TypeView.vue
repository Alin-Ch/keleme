<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入分类名称" v-model="params.name" clearable></el-input>
      <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
      <el-button type="primary" style="margin-left: 10px;" @click="add()">新增</el-button>
      <!-- 批量删除 -->
      <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
        <el-button slot="reference" type="danger" style="margin-left: 10px;">批量删除
          <i class="el-icon-delete el-icon--right"></i>
        </el-button>
      </el-popconfirm>
      <!-- 导出报表 -->
      <el-button type="success" style="margin-left: 10px;" @click="exp()">导出报表
        <i class="el-icon-download el-icon--right"></i>
      </el-button>
      <!-- 批量导入 -->
      <el-upload action="http://localhost:8080/api/type/upload" style="display: inline-block; margin-left: 10px;"
        :on-success="successUpload" :show-file-list="false">
        <el-button size="small" type="primary">批量导入
          <i class="el-icon-upload el-icon--right"></i>
        </el-button>
      </el-upload>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%" ref="table" @selection-change="handleSelectionChange"
        :row-key="getRowKeys">
        <el-table-column type="selection" width="55" :reserve-selection="true">
        </el-table-column>
        <el-table-column width="150" prop="typeid" label="分类编号"></el-table-column>
        <el-table-column width="150" prop="typeName" label="分类名称"></el-table-column>
        <el-table-column width="450" prop="typeDescription" label="分类描述"></el-table-column>
        <el-table-column width="150" label="操作">
          <template slot-scope="scope">
            <!-- scope.row获取当前这一行的数据 -->
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.typeid)">
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
      <el-form :model="form" :rules="typeRules" ref="typeForm">
        <el-form-item label="分类名称" prop="typeName" label-width="25%">
          <el-input v-model="form.typeName" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="分类描述" prop="typeDescription" label-width="25%">
          <el-input v-model="form.typeDescription" type="textarea" autocomplete="off" style="width: 90%">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit('typeForm')">确 定</el-button>
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
      // 添加分类的表单,接收用户勾选的结果
      multipleSelection: [],
      typeRules: {
        typeName: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
        typeDescription: [{ required: true, message: '请输入分类描述', trigger: 'blur' }]
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
      request.get("/type/search", {
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
    // 点击新增和修改时将对话框显示出来
    add() {
      this.dialogFormVisible = true;
      this.$refs.typeForm.resetFields(); // 清空表单校验状态和输入值
      this.form = {};
    },
    handleCancel() {
      // 关闭对话框前重置表单校验状态
      this.$refs.typeForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    handleClose() {
      // 关闭对话框前重置表单校验状态
      this.$refs.typeForm.clearValidate();
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
        this.$refs.typeForm.clearValidate();
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
    // 将数据往后台传
    submit(typeForm) {
      this.$refs[typeForm].validate((valid) => {
        if (valid) {
          // 表单校验成功
          // 发送请求
          request.post('/type', this.form).then(res => {
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
      request.delete("/type/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    // 批量删除
    delBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请选择要删除的数据");
        return;
      }
      request.put('/type/delBatch', this.multipleSelection).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    //	当选择项发生变化时会触发该事件,val为选中的哪行数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //批量删除时跳页勾选多个保存勾选记录
    getRowKeys(row) {
      return row.typeid;
    },
    //批量导出报表
    exp() {
      let user = localStorage.getItem('user');
      location.href = 'http://localhost:8080/api/type/export?token=' + JSON.parse(user).token;
    },
    // 批量导入
    successUpload(res) {
      if (res.code === '200') {
        this.$message.success("导入成功");
        this.findBySearch();
      } else {
        this.$message.error(res.msg);
      }
    },
  }
}
</script>
