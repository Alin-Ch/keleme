<template>
    <div>
        <div style="margin-bottom: 12px;">
            <el-input style="width: 200px;" placeholder="请输入用户名称" v-model="params.name" clearable></el-input>
            <el-button type="warning" style="margin-left: 10px;" @click="findBySearch()">查询</el-button>
            <el-button type="warning" style="margin-left: 10px;" @click="reset()">清空</el-button>

            <!-- 批量删除 -->
            <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
                <el-button slot="reference" type="danger" style="margin-left: 10px;">批量删除
                    <i class="el-icon-delete el-icon--right"></i>
                </el-button>
            </el-popconfirm>
        </div>
        <div>
            <el-table :data="tableData" style="width: 100%" ref="table" @selection-change="handleSelectionChange"
                :row-key="getRowKeys">
                <el-table-column type="selection" width="55" :reserve-selection="true"></el-table-column>
                <el-table-column fixed prop="orderid" width="160" label="订单id"></el-table-column>
                <el-table-column width="160" prop="ordernum" label="订单编号"></el-table-column>
                <el-table-column width="150" prop="createtime" label="订单创建时间"></el-table-column>
                <el-table-column width="150" prop="money" label="订单价格"></el-table-column>
                <el-table-column width="150" prop="userName" label="用户名"></el-table-column>
                <el-table-column width="150" prop="orderUName" label="收货人"></el-table-column>
                <el-table-column width="150" prop="addressName" label="收货地址"></el-table-column>
                <el-table-column width="150" prop="phone" label="手机号"></el-table-column>
                <el-table-column width="150" prop="remark" label="订单备注"></el-table-column>
                <el-table-column width="150" prop="statu" label="订单状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.statu === 1">待付款</span>
                        <span v-else-if="scope.row.statu === 2">已付款</span>
                        <span v-else-if="scope.row.statu === 0">订单取消</span>
                        <span v-else-if="scope.row.statu === 3">配送中</span>
                        <span v-else-if="scope.row.statu === 4">已完成</span>
                    </template>
                </el-table-column>

                <el-table-column width="150" fixed="right" label="操作">
                    <template slot-scope="scope">
                        <!-- scope.row获取当前这一行的数据 -->
                        <el-button type="primary" @click="check(scope.row.orderid)">查看</el-button>
                        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.orderid)">
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

        <!-- dialog -->
        <el-dialog title="订单详情" :visible.sync="dialogTableVisible">
            <el-table :data="orderInfo">
                <el-table-column property="name" label="商品" width="150"></el-table-column>
                <el-table-column property="newPrice" label="单价" width="200"></el-table-column>
                <el-table-column property="number" label="数量"></el-table-column>
                <el-table-column property="price" label="金额"></el-table-column>
            </el-table>
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
            //保存每个实体对象
            form: {},
            //保存分类对象的实体类,多条用List接收
            typeObjs1: [],
            typeObjs2: [],
            typeObjs3: [],
            dialogTableVisible: false,
            orderInfo: [],
            // 添加订单的表单,接收用户勾选的结果
            multipleSelection: [],
        }
    },
    //页面加载时要做一些事情
    created() {
        this.findBySearch();
        this.findTypes1();
        this.findTypes2();
        this.findTypes3();
    },
    //定义一些页面上控件发出事件调用的方法
    methods: {
        //查询全部/分页查询
        findBySearch() {
            request.get("/order/search", {
                params: this.params
            }).then(res => {
                console.log(res);
                if (res.code === '200') {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        findTypes1() {
            request.get('/user').then(res => {
                if (res.code === '200') {
                    this.typeObjs1 = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        findTypes2() {
            request.get('/address').then(res => {
                if (res.code === '200') {
                    this.typeObjs2 = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        findTypes3() {
            request.get('/good').then(res => {
                if (res.code === '200') {
                    this.typeObjs3 = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        // 将搜索框中的数据清除，然后重新查询
        reset() {
            this.params = {
                pageNum: 1,
                pageSize: 5,
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
        // 根据id删除某条数据
        del(id) {
            request.delete("/order/" + id).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功");
                    this.findBySearch();
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        check(orderid) {
            console.log(orderid);
            request.get("/orderDetail/" + orderid).then(res => {
                if (res.code === '200') {
                    this.orderInfo = res.data;
                    this.dialogTableVisible = true;
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
            request.put('/order/delBatch', this.multipleSelection).then(res => {
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
    }
}
</script>
  