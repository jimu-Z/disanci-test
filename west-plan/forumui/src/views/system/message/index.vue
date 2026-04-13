<template>
  <div class="message-page">
    <div class="container">
      <h2>留言反馈</h2>

      <!-- 留言表单 -->
      <el-card class="form-card">
        <el-form :model="form" label-width="80px">


          <el-form-item label="联系方式">
            <el-input v-model="form.contact" placeholder="手机号/邮箱" />
          </el-form-item>

          <el-form-item label="留言内容">
            <el-input
              type="textarea"
              rows="5"
              v-model="form.content"
              placeholder="请输入您的留言内容"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitMessage">提交留言</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 我的留言列表 -->
      <div class="my-message">
        <h3>我的留言与回复</h3>
        <el-card
          v-for="item in messageList"
          :key="item.id"
          class="item-card"
        >
          <div class="content">留言内容：{{ item.content }}</div>
          <div class="time">留言时间：{{ item.createTime }}</div>

          <!-- 管理员回复 -->
          <div v-if="item.status === 1" class="reply-box">
            <span class="reply-tag">管理员回复：</span>
            <span>{{ item.replyContent }}</span>
            <div class="reply-time">{{ item.replyTime }}</div>
          </div>

          <el-button
            type="text"
            class="del-btn"
            @click="deleteMessage(item.id)"
          >
            删除此留言
          </el-button>
        </el-card>

        <div v-if="messageList.length === 0" class="empty">
          暂无留言记录
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import  { getMyMessage,addMessage, delMessage } from '@/api/system/message'

export default {
  name: 'Message',
  data() {
    return {
      form: {
        nickname: '',
        contact: '',
        content: ''
      },
      messageList: []
    }
  },
  mounted() {
    this.getMyMessage()
  },
  methods: {
    // 获取我的留言
    async getMyMessage() {
      try {
        const res = await getMyMessage()
        this.messageList = Array.isArray(res) ? res : []
      } catch (e) {
        this.messageList = []
        this.$message.error('获取留言失败，请稍后重试')
      }

    },

    // 提交留言
    async submitMessage() {
      if ( !this.form.content) {
        this.$message.warning('昵称和留言内容不能为空')
        return
      }

      try {
        const res = await addMessage(this.form)

        if (res.code === 200) {
          this.$message.success('留言提交成功')
          this.form = { contact: '', content: '' }
          this.getMyMessage()
        } else {
          this.$message.error(res.msg || '留言提交失败')
        }
      } catch (e) {
        this.$message.error('留言提交失败，请稍后重试')
      }
    },

    // 删除留言
    async deleteMessage(id) {
      this.$confirm('确定要删除这条留言吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await delMessage(id)
          if (res.code === 200) {
            this.$message.success('删除成功')
            this.getMyMessage()
          } else {
            this.$message.error(res.msg || '删除失败')
          }
        } catch (e) {
          this.$message.error('删除失败，请稍后重试')
        }
      })
    }
  }
}
</script>

<style scoped>
.message-page {
  width: 100%;
  padding: 30px 0;
  background: #f5f7fa;
}

.container {
  width: 900px;
  margin: 0 auto;
}

.form-card {
  margin-bottom: 30px;
  padding: 20px;
}

.my-message h3 {
  margin-bottom: 15px;
  font-size: 18px;
}

.item-card {
  margin-bottom: 15px;
  padding: 18px;
}

.content {
  font-size: 14px;
  line-height: 1.6;
}

.time {
  color: #999;
  font-size: 12px;
  margin: 6px 0;
}

.reply-box {
  background: #f6f8fa;
  padding: 10px 12px;
  border-radius: 4px;
  margin-top: 10px;
  font-size: 14px;
}

.reply-tag {
  color: #007bff;
  font-weight: 500;
}

.reply-time {
  font-size: 12px;
  color: #999;
  margin-top: 4px;
}

.del-btn {
  color: #f56c6c;
  margin-top: 10px;
  padding-left: 0;
}

.empty {
  text-align: center;
  padding: 40px 0;
  color: #999;
}
</style>
