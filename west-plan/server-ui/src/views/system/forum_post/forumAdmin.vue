<template>
  <div class="audit-container">
    <!-- 筛选区域 -->
    <div class="search-box">
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="标题">
          <el-input v-model="queryParams.title" placeholder="请输入帖子标题" clearable @keyup.enter.native="getList"/>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="queryParams.auditStatus" placeholder="请选择" clearable @change="getList">
            <el-option label="待审核" :value="0"/>
            <el-option label="已通过" :value="1"/>
            <el-option label="已驳回" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="handleDateChange"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="getList">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作栏 -->
    <div class="tool-box">
      <el-button type="success" icon="el-icon-check" @click="batchAudit(1)">批量通过</el-button>
      <el-button type="danger" icon="el-icon-close" @click="batchAudit(2)">批量驳回</el-button>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="list" border @selection-change="handleSelectionChange" stripe>
      <el-table-column type="selection" width="55"/>
      <el-table-column label="帖子标题" prop="postTitle" min-width="200"/>
      <el-table-column label="浏览/点赞/评论" prop="viewCount" width="180">
        <template slot-scope="scope">
          {{scope.row.viewCount}} / {{scope.row.likeCount}} / {{scope.row.commentCount}}
        </template>
      </el-table-column>
      <el-table-column label="审核状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.auditStatus === 0 ? 'warning' : scope.row.auditStatus === 1 ? 'success' : 'danger'">
            {{ scope.row.auditStatus === 0 ? '待审核' : scope.row.auditStatus === 1 ? '已通过' : '已驳回' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="180"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="text" icon="el-icon-view" @click="viewDetail(scope.row)">查看</el-button>
          <el-button type="text" icon="el-icon-check" style="color:#67C23A" @click="audit(scope.row.id,1)">通过</el-button>
          <el-button type="text" icon="el-icon-close" style="color:#F56C6C" @click="audit(scope.row.id,2)">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum" :page-sizes="[10,20,50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total"/>

    <!-- 审核驳回弹窗 -->
    <el-dialog title="审核驳回" :visible.sync="auditDialog" width="400px">
      <el-form :model="auditForm">
        <el-form-item label="驳回原因">
          <el-input v-model="auditForm.auditRemark" type="textarea" rows="3" placeholder="请输入驳回原因"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="auditDialog = false">取消</el-button>
        <el-button type="primary" @click="submitAudit">确认</el-button>
      </div>
    </el-dialog>

    <!-- 详情弹窗 -->
    <el-dialog title="帖子详情" :visible.sync="detailDialog" width="60%">
      <div v-html="detailContent" style="padding:10px;"></div>
    </el-dialog>
  </div>
</template>

<script>
import { listForum_post, auditPost, batchAuditPost } from '@/api/system/forum_post'
export default {
  name: 'PostAudit',
  data() {
    return {
      loading: false,
      list: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      queryParams: { title: '', auditStatus: 0, startTime: '', endTime: '' },
      dateRange: [],
      selectedIds: [],
      auditDialog: false,
      detailDialog: false,
      detailContent: '',
      auditForm: { id: null, ids: [], auditStatus: 1, auditRemark: '' }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true
      listForum_post({
        ...this.queryParams,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }).then(res => {

        this.list = res.rows
        this.total = res.total
        this.loading = false
      }).catch(() => {
        this.loading = false
        this.$message.error('查询失败')
      })
    },

    // 时间处理
    handleDateChange(val) {
      if (val && val.length === 2) {
        this.queryParams.startTime = val[0]
        this.queryParams.endTime = val[1]
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
    },

    // 重置查询
    resetQuery() {
      this.dateRange = []
      this.queryParams = { title: '', auditStatus: '', startTime: '', endTime: '' }
      this.pageNum = 1
      this.getList()
    },

    // 分页条数改变
    handleSizeChange(val) {
      this.pageSize = val
      this.getList()
    },

    // 分页页码改变
    handleCurrentChange(val) {
      this.pageNum = val
      this.getList()
    },

    // 多选事件
    handleSelectionChange(val) {
      this.selectedIds = val.map(item => item.id)
    },

    // 单条审核
    audit(id, status) {
      this.auditForm.id = id
      this.auditForm.ids = []
      this.auditForm.auditStatus = status
      this.auditForm.auditStatusRmark = ''

      if (status === 2) {
        this.auditDialog = true
      } else {
        auditPost(this.auditForm).then(() => {
          this.$message.success('操作成功')
          this.getList()
        }).catch(() => {
          this.$message.error('操作失败')
        })
      }
    },

    // 提交审核（驳回专用）
    submitAudit() {
      if (!this.auditForm.remark && this.auditForm.status === 2) {
        return this.$message.warning('请输入驳回原因')
      }

      // 判断是单条还是批量
      let req = this.auditForm.ids && this.auditForm.ids.length > 0
        ? batchAuditPost(this.auditForm)
        : auditPost(this.auditForm)

      req.then(() => {
        this.$message.success('操作成功')
        this.auditDialog = false
        this.getList()
      }).catch(() => {
        this.$message.error('操作失败')
      })
    },

    // 批量审核
    batchAudit(status) {
      if (this.selectedIds.length === 0) {
        return this.$message.warning('请选择数据')
      }
      this.auditForm.id = null
      this.auditForm.ids = []
      this.auditForm.auditStatus = status
      this.auditForm.auditStatusRmark = ''
      this.auditForm.ids = this.selectedIds

      if (status === 2) {
        this.auditDialog = true
      } else {
        batchAuditPost({
          ids: this.selectedIds,
          auditStatus: status
        }).then(() => {
          this.$message.success('批量操作成功')
          this.getList()
        }).catch(() => {
          this.$message.error('操作失败')
        })
      }
    },

    // 查看详情
    viewDetail(row) {
      this.detailContent = `
        <p><strong>标题：</strong>${row.postTitle}</p>
        <p><strong>内容：</strong>${row.postContent}</p>
        <p><strong>审核状态：</strong>${row.auditStatus === 0 ? '待审核' : row.auditStatus === 1 ? '已通过' : '已驳回'}</p>
        <p><strong>创建时间：</strong>${row.createTime}</p>
      `
      this.detailDialog = true
    }
  }
}
</script>

<style scoped>
.audit-container { padding: 20px; background: #f5f7fa; }
.search-box { background: #fff; padding: 15px; border-radius: 4px; margin-bottom: 15px; }
.tool-box { margin-bottom: 15px; }
</style>
