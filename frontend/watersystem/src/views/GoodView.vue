<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入商品名称" v-model="params.name" clearable></el-input>
      <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>
      <el-button type="primary" style="margin-left: 10px;" @click="add()">新增</el-button>
    </div>
    <div>

      <el-table :data="tableData" style="width: 100%">

        <el-table-column fixed prop="goodname" width="150" label="商品名称"></el-table-column>
        <el-table-column prop="cover" width="150" label="商品图片">
          <template v-slot="scope">
            <el-image style="width: 60px; height: 60px; " :src="'http://localhost:8080/api/files/' + scope.row.cover"
              :preview-src-list="['http://localhost:8080/api/files/' + scope.row.cover]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column width="150" prop="size" label="商品规格"></el-table-column>
        <el-table-column width="150" prop="oldprice" label="商品原价格"></el-table-column>
        <el-table-column width="150" prop="newprice" label="商品新价格"></el-table-column>
        <el-table-column width="150" prop="typeName" label="商品类别"></el-table-column>
        <el-table-column width="150" prop="stationsName" label="水站"></el-table-column>
        <el-table-column width="150" prop="quantity" label="库存"></el-table-column>
        <el-table-column width="150" prop="sold" label="已售数量"></el-table-column>
        <el-table-column width="150" prop="description" label="描述"></el-table-column>

        <el-table-column width="150" fixed="right" label="操作">
          <template slot-scope="scope">
            <!-- scope.row获取当前这一行的数据 -->
            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.goodid)">
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
    <el-dialog title="请填写商品信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form :model="form" :rules="goodRules" ref="goodForm">
        <el-form-item label="商品名称" prop="goodname" label-width="26%">
          <el-input v-model="form.goodname" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <!-- 上传图片/封面 -->
        <el-form-item label="商品图片" label-width="26%">
          <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload">
            <el-button size="small" type="primary">上传封面</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品规格" prop="size" label-width="26%">
          <el-input v-model="form.size" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="商品原价格(元)" prop="oldprice" label-width="26%">
          <el-input v-model="form.oldprice" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="商品新价格(元)" prop="newprice" label-width="26%">
          <el-input v-model="form.newprice" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="库存" prop="quantity" label-width="26%">
          <el-input v-model="form.quantity" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="水站厂商" prop="station_id" label-width="26%">
          <!--  v-model="form.type_id"中的type_id为商品表中的字段-->
          <el-select v-model="form.station_id" placeholder="请选择" style="width: 90%">
            <el-option v-for="item in typeObjs2" :key="item.sid" :label="item.sname" :value="item.sid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品已售数量(桶)" prop="sold" label-width="26%">
          <el-input v-model.number="form.sold" autocomplete="off" style="width: 60%"></el-input>
        </el-form-item>
        <!-- 在新增商品时循环遍历商品分类的分类名称到选择器中（其中label为展示的属性）-->
        <el-form-item label="商品类别" prop="type_id" label-width="26%">
          <!--  v-model="form.type_id"中的type_id为商品表中的字段-->
          <el-select v-model="form.type_id" placeholder="请选择" style="width: 90%">
            <el-option v-for="item in typeObjs1" :key="item.typeid" :label="item.typeName" :value="item.typeid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" prop="description" label-width="26%">
          <el-input v-model="form.description" type="textarea" autocomplete="off" style="width: 90%" maxlength="20"
            show-word-limit>
          </el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit('goodForm')">确 定</el-button>
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
      typeObjs1: [],
      typeObjs2: [],
      //表单校验
      goodRules: {
        goodname: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        size: [
          { required: true, message: '请输入商品规格', trigger: 'blur' }
        ],
        // 商品原价格
        oldprice: [
          { required: true, message: '请输入商品原价格', trigger: 'blur' },
          { validator: this.validatePrice, trigger: 'blur' }
        ],
        // 商品新价格
        newprice: [
          { required: true, message: '请输入商品现价格', trigger: 'blur' },
          { validator: this.validatePrice, trigger: 'blur' }
        ],
        station_id: [
          { required: true, message: '请选择水站厂商', trigger: 'blur' }
        ],
        sold: [
          { required: true, message: '请输入商品销量', trigger: 'blur' }
        ],
        type_id: [
          { required: true, message: '请选择商品类型', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入商品描述', trigger: 'blur' }
        ],
        quantity: [
          { required: true, message: '请输入正整数', trigger: 'blur' },
          { pattern: /^[1-9]\d*$/, message: '请输入正整数', trigger: 'blur' }
        ],
      }
    }
  },
  //页面加载时要做一些事情
  created() {
    this.findBySearch();
    // 商品表和分类表的关联查询
    this.findTypes1();
    // 商品表和水站表的关联查询
    this.findTypes2();
  },
  //定义一些页面上控件发出事件调用的方法
  methods: {
    //查询全部/分页查询
    findBySearch() {
      request.get("/good/search", {
        params: this.params
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
    //三表联查时将商品表good中的type_id、station_id分别与type表的typeid和sid做映射
    findTypes1() {
      request.get('/type').then(res => {
        if (res.code === '200') {
          this.typeObjs1 = res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    findTypes2() {
      request.get('/station').then(res => {
        if (res.code === '200') {
          this.typeObjs2 = res.data;
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    // 自定义校验方法：校验价格不能为空，价格格式正确，小数位数为两位
    validatePrice(rule, value, callback) {
      const moneyRegex = /^\d+(\.\d{0,2})?$/; // 金额格式正则表达式，允许保留两位小数
      if (!value) {
        callback(new Error('价格不能为空'));
      } else if (!moneyRegex.test(value)) {
        callback(new Error('请输入正确格式的价格'));
      } else {
        callback();
      }
    },
    // 点击新增和修改时将对话框显示出来
    add() {
      this.dialogFormVisible = true;
      this.$refs.goodForm.resetFields(); // 清空表单校验状态和输入值
      this.form = {};
    },
    handleCancel() {
      // 关闭对话框前重置表单校验状态
      this.$refs.goodForm.clearValidate();
      // 关闭对话框
      this.dialogFormVisible = false;
    },
    handleClose() {
      // 关闭对话框前重置表单校验状态
      this.$refs.goodForm.clearValidate();
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
        this.$refs.goodForm.clearValidate();
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
    submit(goodForm) {
      this.$refs[goodForm].validate(valid => {
        if (valid) {
          // 表单校验成功
          // 发送请求
          request.post('/good', this.form).then(res => {
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
      request.delete("/good/" + id).then(res => {
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
    }
  }
}
</script>
