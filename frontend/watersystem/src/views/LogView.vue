<template>
  <div>
    <div style="margin-bottom: 12px;">
      <el-input style="width: 200px;" placeholder="请输入操作内容" v-model="params.name" clearable>
      </el-input>
      <el-input style="width: 200px; margin-left: 5px;" placeholder="请输入操作人" v-model="params.someoneName" clearable>
      </el-input>
      <el-button style="margin-left: 10px;" type="warning" @click="findBySearch()">查询</el-button>
      <el-button style="margin-left: 10px;" type="primary" @click="reset()">清空</el-button>
      <!-- 批量删除 -->
      <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
        <el-button slot="reference" type="danger" style="margin-left: 10px;">批量删除
          <i class="el-icon-delete el-icon--right"></i>
        </el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" stripe style="width: 100%" ref="table" @selection-change="handleSelectionChange"
        :row-key="getRowKeys">
        <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
      <el-table-column prop="name" label="操作内容">
      </el-table-column>
      <el-table-column prop="time" label="操作时间">
      </el-table-column>
      <el-table-column prop="someoneName" label="操作人">
      </el-table-column>
      <el-table-column prop="ip" label="ip">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
            <el-button slot="reference" type="danger" style="margin-left: 5px;">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 15px;">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="params.pageNum"
        :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

  </div>
</template>
  
<script>
import request from '@/utils/request';
export default {
  data() {
    return {
      params: {
        name: '',
        someoneName: '',
        pageNum: 1,
        pageSize: 5
      },
      tableData: [],
      total: 0,
      dialogFormVisible: false,
      form: {},
      // 添加分类的表单,接收用户勾选的结果
      multipleSelection: [],
    }
  },
  //页面加载的时候，做一些事情，在create里面
  created() {
    this.findBySearch();
  },
  //定义一些页面上控件触发的事件调用的方法
  methods: {
    findBySearch() {
      request.get('/log/search', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        someoneName: ''
      }
      this.findBySearch();
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findBySearch();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findBySearch();
    },
    submit() {
      request.post('/log', this.form).then(res => {
        if (res.code === '200') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    del(id) {
      request.delete('/log/' + id).then(res => {
        if (res.code === '200') {
          this.$message({
            message: '操作成功',
            type: 'success'
          });
          this.findBySearch();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    // 批量删除
    delBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请选择要删除的数据");
        return;
      }
      request.put('/log/delBatch', this.multipleSelection).then(res => {
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
      return row.id;
    },
  },

}
</script>
  