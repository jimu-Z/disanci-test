<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发布人ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入发布人ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内容ID" prop="contentId">
        <el-input
          v-model="queryParams.contentId"
          placeholder="请输入内容ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词审核" prop="keywordAudit">
        <el-input
          v-model="queryParams.keywordAudit"
          placeholder="请输入关键词审核"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="AI审核" prop="aiAudit">
        <el-input
          v-model="queryParams.aiAudit"
          placeholder="请输入AI审核"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最终结果" prop="auditResult">
        <el-input
          v-model="queryParams.auditResult"
          placeholder="请输入最终结果"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:content_audit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:content_audit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:content_audit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:content_audit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="content_auditList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="发布人ID" align="center" prop="userId" />
      <el-table-column label="内容类型" align="center" prop="contentType" />
      <el-table-column label="内容ID" align="center" prop="contentId" />
      <el-table-column label="审核内容" align="center" prop="contentContent" />
      <el-table-column label="关键词审核" align="center" prop="keywordAudit" />
      <el-table-column label="AI审核" align="center" prop="aiAudit" />
      <el-table-column label="最终结果" align="center" prop="auditResult" />
      <el-table-column label="审核失败原因" align="center" prop="auditMsg" />
      <el-table-column label="报警状态" align="center" prop="alarmStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:content_audit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:content_audit:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改内容审核记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发布人ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入发布人ID" />
        </el-form-item>
        <el-form-item label="内容ID" prop="contentId">
          <el-input v-model="form.contentId" placeholder="请输入内容ID" />
        </el-form-item>
        <el-form-item label="审核内容">
          <editor v-model="form.contentContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="关键词审核" prop="keywordAudit">
          <el-input v-model="form.keywordAudit" placeholder="请输入关键词审核" />
        </el-form-item>
        <el-form-item label="AI审核" prop="aiAudit">
          <el-input v-model="form.aiAudit" placeholder="请输入AI审核" />
        </el-form-item>
        <el-form-item label="最终结果" prop="auditResult">
          <el-input v-model="form.auditResult" placeholder="请输入最终结果" />
        </el-form-item>
        <el-form-item label="审核失败原因" prop="auditMsg">
          <el-input v-model="form.auditMsg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listContent_audit, getContent_audit, delContent_audit, addContent_audit, updateContent_audit } from "@/api/system/content_audit"

export default {
  name: "Content_audit",
  data() {
    return {
      // 遮罩层
      loading: true,
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
      // 内容审核记录表格数据
      content_auditList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        contentType: null,
        contentId: null,
        contentContent: null,
        keywordAudit: null,
        aiAudit: null,
        auditResult: null,
        auditMsg: null,
        alarmStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "发布人ID不能为空", trigger: "blur" }
        ],
        contentType: [
          { required: true, message: "内容类型不能为空", trigger: "change" }
        ],
        contentId: [
          { required: true, message: "内容ID不能为空", trigger: "blur" }
        ],
        keywordAudit: [
          { required: true, message: "关键词审核不能为空", trigger: "blur" }
        ],
        aiAudit: [
          { required: true, message: "AI审核不能为空", trigger: "blur" }
        ],
        auditResult: [
          { required: true, message: "最终结果不能为空", trigger: "blur" }
        ],
        alarmStatus: [
          { required: true, message: "报警状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询内容审核记录列表 */
    getList() {
      this.loading = true
      listContent_audit(this.queryParams).then(response => {
        this.content_auditList = response.rows
        this.total = response.total
        this.loading = false
      })
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
        userId: null,
        contentType: null,
        contentId: null,
        contentContent: null,
        keywordAudit: null,
        aiAudit: null,
        auditResult: null,
        auditMsg: null,
        alarmStatus: null,
        createTime: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加内容审核记录"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getContent_audit(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改内容审核记录"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateContent_audit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addContent_audit(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除内容审核记录编号为"' + ids + '"的数据项？').then(function() {
        return delContent_audit(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/content_audit/export', {
        ...this.queryParams
      }, `content_audit_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
