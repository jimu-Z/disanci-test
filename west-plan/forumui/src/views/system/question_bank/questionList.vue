<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题库名称" prop="bankName">
        <el-input
          v-model="queryParams.bankName"
          placeholder="请输入题库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属批次" prop="batch">
        <el-input
          v-model="queryParams.batch"
          placeholder="请输入所属批次"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>



    <el-table v-loading="loading" :data="question_bankList"
     @selection-change="handleSelectionChange"
        @row-click="toQuestionDetail"
        row-class-name="cursor-pointer">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="题库名称" align="center" prop="bankName" />
      <el-table-column label="所属批次" align="center" prop="batch" />



      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />

    </el-table>

    <request-state
      :loading="loading"
      :error="Boolean(loadError)"
      :empty="!loadError && question_bankList.length === 0"
      empty-text="暂无题库数据"
      error-text="题库加载失败，请重试"
      @retry="getList"
    />

    <pagination
      v-show="total>0 && !loadError"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listQuestion_bank } from "@/api/system/question_bank"
import RequestState from "@/components/RequestState"

export default {
  name: "Question_bank",
  components: {
    RequestState
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 加载错误信息
      loadError: '',
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 题库信息表格数据
      question_bankList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bankName: null,
        batch: null,
        questionContent: null,
        correctAnswer: null,
        openStartTime: null,
        openEndTime: null,
        status: null,
        sort: null,
      },

    }
  },
  created() {
    this.getList()
  },
  methods: {
     // 跳转到详情页
    toQuestionDetail(row) {
      const postId = typeof row === 'object' ? row.id : row;
      this.$router.push({ path: `/system/question/detail/${postId}` });
    },
    /** 查询题库信息列表 */
    async getList() {
      this.loading = true
      this.loadError = ''
      try {
        const response = await listQuestion_bank(this.queryParams)
        this.question_bankList = response.rows
        this.total = response.total
      } catch (e) {
        this.question_bankList = []
        this.total = 0
        this.loadError = e && e.message ? e.message : 'load_failed'
      } finally {
        this.loading = false
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        bankName: null,
        batch: null,
        questionContent: null,
        correctAnswer: null,
        openStartTime: null,
        openEndTime: null,
        status: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },



    /** 导出按钮操作 */
    handleExport() {
      this.download('system/question_bank/export', {
        ...this.queryParams
      }, `question_bank_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
