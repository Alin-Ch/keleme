<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入水站名称" v-model="params.name" clearable></el-input>
      <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
      <el-button type="primary" style="margin-left: 10px;" @click="add()">新增</el-button>
    </div>
    <div>

      <el-table :data="tableData" style="width: 100%">
        <el-table-column fixed prop="sname" width="150" label="水站名称"></el-table-column>
        <el-table-column width="150" prop="address" label="水站地址"></el-table-column>
        <el-table-column width="150" prop="manager" label="负责人"></el-table-column>
        <el-table-column width="150" prop="telephone" label="负责人电话"></el-table-column>
        <el-table-column width="150" prop="weidu" label="纬度"></el-table-column>
        <el-table-column width="150" prop="jingdu" label="经度"></el-table-column>
        <el-table-column width="150" prop="distribution" label="配送距离"></el-table-column>

        <el-table-column width="150" fixed="right" label="操作">
          <template slot-scope="scope">
            <!-- scope.row获取当前这一行的数据 -->
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.sid)">
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
    <el-dialog title="新建水站信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="stationRules" ref="stationForm">
        <el-form-item label="水站名称" prop="sname" label-width="20%">
          <el-input v-model="form.sname" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="配送距离(m)" prop="distribution" label-width="20%">
          <el-input v-model="form.distribution" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="负责人" prop="manager" label-width="20%">
          <el-input v-model="form.manager" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="telephone" label-width="20%">
          <el-input v-model="form.telephone" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <!-- 引入地图页面 -->
        <div style="height: 320px;margin-left: 60px; overflow: hidden;">
          <MapSearchView @send-data="handleData"></MapSearchView>
        </div>
        <!-- <el-input type="textarea" :rows="2" placeholder="获取经纬度的内容" v-model="textarea">
        </el-input>
        <el-form-item label="纬度" label-width="25%">
          <el-input v-model="form.weidu" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="经度" label-width="25%">
          <el-input v-model="form.jingdu" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="详细地址" label-width="25%">
          <el-input v-model="form.detailaddress" autocomplete="off" style="width: 90%"></el-input>
        </el-form-item> -->
        <el-form-item label="水站地址" prop="address" label-width="20%">
          <el-input v-model="form.address" type="textarea" autocomplete="off" style="width: 90%" maxlength="30"
            show-word-limit>
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit('stationForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import request from '@/utils/request';
import MapSearchView from '@/views/MapSearchView.vue'
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
      textarea: '',
      //水站表单校验规则
      stationRules: {
        sname: [
          { required: true, message: '请输入水站名称', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入负责人联系方式', trigger: 'blur' },
          { validator: this.validatePhoneNumber, trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ],
        distribution: [
          { required: true, message: '请输入水站配送范围', trigger: 'blur' },
          { validator: this.validatePositiveInteger, trigger: 'blur' },
        ],
        manager: [
          { required: true, message: '请输入负责人姓名', trigger: 'blur' }
        ],
      }
    }

  },
  components: {
    MapSearchView
  },
  //页面加载时要做一些事情
  created() {
    this.findBySearch();
    // this.findTypes();
  },
  //定义一些页面上控件发出事件调用的方法
  methods: {
    handleData(data) {
      if (data.info) {
        this.params.receivedData = data;
        console.log('子组件传递的纬度和经度及详细地址信息为：', data.lat, data.lng, data.info); // 获取地图经纬度和详细地址信息
        this.textarea = `纬度和经度及详细地址信息为: lat: ${data.lat}, lng: ${data.lng}, info: ${data.info}`;

        this.form.weidu = data.lat;
        this.form.jingdu = data.lng;
        this.form.detailaddress = data.info;
        //将地址信息赋值给address
        this.form.address = data.info;
      }
    },
    //查询全部/分页查询
    findBySearch() {
      var params = {
        name: this.params.name,
        phone: this.params.phone,
        pageNum: this.params.pageNum,
        pageSize: this.params.pageSize,
      }
      request.get("/station/search", {
        params: params
      }).then(res => {
        if (res.code === '200') {
          console.log(res);
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
      this.$refs.stationForm.resetFields(); // 清空表单校验状态和输入值
      this.form = {};
    },
    //表单校验只能输入正整数
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
    handleCancel() {
      // 关闭对话框前重置表单校验状态
      this.$refs.stationForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    handleClose() {
      // 关闭对话框前重置表单校验状态
      this.$refs.stationForm.clearValidate();
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
        this.$refs.stationForm.clearValidate();
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
    submit(stationForm) {
      this.$refs[stationForm].validate((valid) => {
        if (valid) {
          request.post('/station', this.form).then(res => {
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
      request.delete("/station/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    successUpload(res) {
      this.form.cover = res.data;
    },

  }
}
</script>
