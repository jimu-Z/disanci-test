 <template>
  <div class="message-admin-page">
    <!-- 搜索栏 -->
    <div class="search-box">
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="70px"
        class="search-form"
      >
        <el-form-item label="学号" prop="userId">
          <el-input
            v-model="queryParams.userId"
            placeholder="请输入学号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="审核状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable>
            <el-option label="已审核" value="1"></el-option>
            <el-option label="未审核" value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="留言内容" prop="content">
          <el-input
            v-model="queryParams.content"
            placeholder="请输入部分内容"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item class="btn-box">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">
            搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 主体容器 -->
    <div class="container">
      <div class="page-header">
        <h2>💬 留言管理后台</h2>
        <p class="subtitle">Manage Messages</p>
      </div>

      <!-- 表格 -->
      <div class="table-card">
        <el-table
          border
          :data="messageList"
          style="width: 100%"
          header-cell-class-name="table-header"
          row-class-name="table-row"
        >
          <el-table-column prop="id" label="ID" width="70" />
          <el-table-column prop="nickname" label="用户昵称" width="130" />
          <el-table-column prop="content" label="留言内容" min-width="280" />
          <el-table-column prop="contact" label="联系方式" width="150" />
          <el-table-column prop="createTime" label="留言时间" width="180" />
           <el-table-column prop="replyContent" label="回复内容" min-width="280" />
          <el-table-column prop="replyTime" label="回复时间" width="180" />
          <el-table-column label="回复状态" width="120">
            <template slot-scope="{ row }">
              <el-tag :type="row.status === 0 ? 'warning' : 'success'" effect="dark">
                {{ row.status === 0 ? '未回复' : '已回复' }}
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="220">
            <template slot-scope="{ row }">
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="openReply(row)"
              >
                回复
              </el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="deleteMessage(row.id)"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- 回复弹窗 -->
    <el-dialog
      title="管理员回复留言"
      :visible.sync="replyDialogVisible"
      width="580px"
      center
      class="reply-dialog"
    >
      <el-input
        type="textarea"
        rows="6"
        v-model="replyForm.replyContent"
        placeholder="请输入回复内容"
        class="reply-textarea"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReply">确认回复</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getAdminList,replyMessage,delMessage} from '@/api/system/message'

export default {
  name: 'MessageAdmin',
  data() {
    return {
           // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
       // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        status: null,
        content: null,

      },
      messageList: [],
      replyDialogVisible: false,
      replyForm: {
        id: '',
        replyContent: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
       /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 获取全部留言
    async getList() {
      const res = await getAdminList(this.queryParams)



        this.messageList =  res.rows
        this.total = res.total

    },

    // 打开回复框
    openReply(row) {
      this.replyForm.id = row.id
      this.replyForm.replyContent = row.replyContent || ''
      this.replyDialogVisible = true
    },

    // 提交回复
    async submitReply() {
      if (!this.replyForm.replyContent) {
        this.$message.warning('请输入回复内容')
        return
      }

      const res = await  replyMessage(this.replyForm)
      if (res.code === 200) {
        this.$message.success('回复成功')
        this.replyDialogVisible = false
        this.getAdminList()
      } else {
        this.$message.error('回复失败')
      }
    },

    // 删除留言
    async deleteMessage(id) {
      this.$confirm('确定删除该留言？', '提示', {
        type: 'warning'
      }).then(async () => {
        const res = await delMessage(id)
        if (res.code === 200) {
          this.$message.success('删除成功')
          this.getList()
        }
      })
    }
  }
}
</script>

<style scoped>
/* 页面整体 */
.message-admin-page {
  padding: 25px 30px;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4eaf5 100%);
  min-height: 100vh;
}

/* 搜索框 */
.search-box {
  background: #ffffff;
  border-radius: 12px;
  padding: 18px 20px;
  margin-bottom: 25px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.06);
}

.search-form {
  margin: 0;
}

.btn-box {
  margin-left: 10px;
}

/* 容器 */
.container {
  max-width: 1400px;
  margin: 0 auto;
}

/* 页面标题 */
.page-header {
  margin-bottom: 20px;
  display: flex;
  align-items: baseline;
  gap: 15px;
}

.page-header h2 {
  font-size: 24px;
  font-weight: bold;
  color: #2c3e50;
  margin: 0;
}

.subtitle {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0;
}

/* 表格卡片 */
.table-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 3px 15px rgba(0, 0, 0, 0.07);
  margin-bottom: 20px;
}

/* 表格头部 */
.table-header {
  background: linear-gradient(90deg, #409eff, #56a6ff) !important;
  color: #fff !important;
  font-weight: bold;
}

/* 表格行 */
.table-row {
  transition: all 0.2s;
}

.table-row:hover {
  background: #f8f9ff !important;
}

/* 弹窗 */
.reply-dialog ::v-deep .el-dialog {
  border-radius: 12px;
  overflow: hidden;
}

.reply-textarea {
  border-radius: 6px;
}

.dialog-footer {
  text-align: right;
}

/* 分页 */
::v-deep .el-pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
