<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入商品名称" v-model="params.name" clearable></el-input>
      <el-button type="warning" icon="el-icon-search" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
      <el-button type="primary" icon="el-icon-plus" style="margin-left: 10px;" @click="add()">新增</el-button>
    </div>
    <div>

      <el-table :data="tableData" style="width: 100%">

        <el-table-column prop="ticketname" width="150" label="水票名称"></el-table-column>
        <el-table-column width="150" prop="usecount" label="剩余使用次数"></el-table-column>
        <el-table-column width="150" prop="stationsName" label="水站名称"></el-table-column>
        <el-table-column width="150" prop="userName" label="用户"></el-table-column>
        <el-table-column width="150" prop="money" label="水票价格"></el-table-column>
        <el-table-column width="150" label="操作">
          <template slot-scope="scope">
            <!-- scope.row获取当前这一行的数据 scope.row.ticketid根据id删除-->
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.ticketid)">
              <el-button slot="reference" type="danger" style="margin-left: 5px;">下架</el-button>
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
    <el-dialog title="新增水票" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="ticketRules" ref="ticketForm">
        <el-form-item label="水票名称" prop="ticketname" label-width="23%">
          <el-input v-model="form.ticketname" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="剩余使用次数" prop="usecount" label-width="23%">
          <el-input v-model="form.usecount" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="水票价格" prop="money" label-width="23%">
          <el-input v-model="form.money" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <!-- 在新增商品时循环遍历商品分类的分类名称到选择器中（其中label为展示的属性）-->
        <el-form-item label="水站选择" prop="t_stationid" label-width="23%">
          <!--  v-model="form.type_id"中的type_id为商品表中的字段-->
          <el-select v-model="form.t_stationid" placeholder="请选择" style="width: 90%">
            <el-option v-for="item in typeObjs" :key="item.sid" :label="item.sname" :value="item.sid">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit('ticketForm')">确 定</el-button>
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
      //保存每个实体对象
      form: {},
      //保存分类对象的实体类,多条用List接收
      typeObjs: [],
      ticketRules: {
        ticketname: [
          { required: true, message: '请输入水站名称', trigger: 'blur' }
        ],
        usecount: [
          { required: true, message: '请输入使用次数', trigger: 'blur' },
          { validator: this.validatePositiveInteger, trigger: 'blur' }
        ],
        money: [
          { required: true, message: '请输入金额', trigger: 'blur' },
          { validator: this.validatePositiveNumber, trigger: 'blur' }
        ],
        t_stationid: [
          { required: true, message: '请选择水站', trigger: 'change' }
        ],
      },
    }
  },
  //页面加载时要做一些事情
  created() {
    this.findBySearch();
    this.findTypes();
  },
  //定义一些页面上控件发出事件调用的方法
  methods: {
    //查询全部/分页查询
    findBySearch() {
      request.get("/ticket/search", {
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
    //三表联查时将商品表ticket中的t_stationid、t_userid分别和stations表的sid和userid做映射
    findTypes() {
      request.get('/station').then(res => {
        if (res.code === '200') {
          this.typeObjs = res.data;
          console.log(this.typeObjs);
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
    // 自定义校验方法：判断输入值是否为正数
    validatePositiveNumber(rule, value, callback) {
      if (value && value <= 0) {
        callback(new Error('金额必须是正数'));
      } else {
        callback();
      }
    },
    // 点击新增和修改时将对话框显示出来
    add() {
      this.dialogFormVisible = true;
      this.$refs.ticketForm.resetFields(); // 清空表单校验状态和输入值
      this.form = {};
    },
    handleCancel() {
      // 关闭对话框前重置表单校验状态
      this.$refs.ticketForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    handleClose() {
      // 关闭对话框前重置表单校验状态
      this.$refs.ticketForm.clearValidate();
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
        this.$refs.ticketForm.clearValidate();
      });
      this.dialogFormVisible = true;
    },
    // 将搜索框中的数据清除，然后重新查询
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        gname: '',
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
    submit(ticket) {
      this.$refs[ticket].validate((valid) => {
        if (valid) {
          request.post('/ticket', this.form).then(res => {
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
      request.delete("/ticket/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
  }
}
</script>
