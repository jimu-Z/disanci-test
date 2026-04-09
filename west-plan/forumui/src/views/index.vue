<template>
  <div class="home-container">
    <!-- 顶部欢迎区域 -->
    <div class="welcome-banner">
      <div class="title-box">
        <h1>德院XI望</h1>
        <p>奉献 · 友爱 · 互助 · 进步</p>
      </div>
    </div>

    <!-- 核心功能入口 -->
    <div class="function-section">
      <h2 class="section-title">平台核心功能</h2>
      <div class="function-grid">
        <div class="function-item" v-for="(item, idx) in funcList" :key="idx" @click="goPage(item.path)">
          <div class="func-icon" :style="{background: item.bgColor}">
            <i :class="item.icon"></i>
          </div>
          <h4>{{ item.name }}</h4>
          <p>{{ item.desc }}</p>
        </div>
      </div>
    </div>

    <!-- 统计数据卡片 -->
    <div class="function-section">
      <h2 class="section-title">平台数据</h2>
      <div class="stat-grid">
        <div class="stat-card" v-for="(stat, idx) in statList" :key="idx">
          <div class="stat-icon" :style="{background: stat.bg}">
            <i :class="stat.icon"></i>
          </div>
          <div class="stat-info">
            <h3>{{ stat.value }}</h3>
            <p>{{ stat.label }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="function-section">
      <h2 class="section-title">数据分析</h2>
      <div class="charts-row">
        <div class="chart-box">
          <div id="yearChart" style="height: 350px;"></div>
        </div>
        <div class="chart-box">
          <div id="areaChart" style="height: 350px;"></div>
        </div>
      </div>
    </div>

    <!-- 好友社交模块：好友列表 + 好友留言 -->
    <div class="social-modules">
      <!-- 好友列表模块 -->
      <div class="function-section friend-list-section">
        <div class="section-header">
          <h2 class="section-title">好友列表</h2>
          <el-tabs v-model="activeFriendTab" class="friend-tabs">
            <el-tab-pane name="myFriends">
              <span slot="label">
                我的好友 ({{ myFriendsList.length }})
              </span>
            </el-tab-pane>
            <el-tab-pane name="sentRequests">
              <span slot="label">
                我发出的申请 ({{ sentRequestsList.length }})
              </span>
            </el-tab-pane>
            <el-tab-pane name="receivedRequests">
              <span slot="label">
                我收到的申请 ({{ receivedRequestsList.length }})
                <span v-if="pendingReceivedCount > 0" class="red-dot"></span>
              </span>
            </el-tab-pane>
          </el-tabs>
        </div>

        <!-- 我的好友列表 -->
        <div v-show="activeFriendTab === 'myFriends'" class="friend-content">
          <div v-if="myFriendsList.length === 0" class="empty-placeholder">
            <i class="el-icon-user-solid"></i>
            <p>暂无好友，去添加吧~</p>
          </div>
          <div v-else class="friend-grid">
            <div class="friend-card" v-for="friend in myFriendsList" :key="friend.id">
              <div class="friend-avatar">
                <i class="el-icon-user"></i>
              </div>
              <div class="friend-info">
                <h4>{{ friend.name }}</h4>
                <p>{{ friend.school || '德州学院校友' }}</p>
              </div>
              <div class="friend-actions">
                <el-button type="text" @click="sendMessageToFriend(friend)">留言</el-button>
                <el-button type="text" class="delete-btn" @click="removeFriend(friend)">删除</el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 我发出的申请列表 -->
        <div v-show="activeFriendTab === 'sentRequests'" class="friend-content">
          <div v-if="sentRequestsList.length === 0" class="empty-placeholder">
            <i class="el-icon-message"></i>
            <p>暂无发出的申请</p>
          </div>
          <div v-else class="request-list">
            <div class="request-item" v-for="req in sentRequestsList" :key="req.id">
              <div class="request-avatar"><i class="el-icon-user"></i></div>
              <div class="request-info">
                <span class="request-name">{{ req.toName }}</span>
                <span class="request-status" :class="req.status">{{ getStatusText(req.status) }}</span>
              </div>
              <div class="request-actions">
                <el-button v-if="req.status === 'pending'" size="mini" type="danger" plain @click="cancelRequest(req)">撤销</el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 我收到的申请列表 -->
        <div v-show="activeFriendTab === 'receivedRequests'" class="friend-content">
          <div v-if="receivedRequestsList.length === 0" class="empty-placeholder">
            <i class="el-icon-message"></i>
            <p>暂无收到的申请</p>
          </div>
          <div v-else class="request-list">
            <div class="request-item" v-for="req in receivedRequestsList" :key="req.id">
              <div class="request-avatar"><i class="el-icon-user"></i></div>
              <div class="request-info">
                <span class="request-name">{{ req.fromName }}</span>
                <span class="request-status" :class="req.status">{{ getStatusText(req.status) }}</span>
              </div>
              <div class="request-actions" v-if="req.status === 'pending'">
                <el-button size="mini" type="success" plain @click="acceptRequest(req)">接受</el-button>
                <el-button size="mini" type="danger" plain @click="rejectRequest(req)">拒绝</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 好友留言模块 -->
      <div class="function-section message-section">
        <div class="section-header">
          <h2 class="section-title">好友留言</h2>
          <el-tabs v-model="activeMsgTab" class="msg-tabs">
            <el-tab-pane name="receivedMsgs">
              <span slot="label">
                我收到的留言 ({{ receivedMessages.length }})
                <span v-if="unreadMessagesCount > 0" class="red-dot"></span>
              </span>
            </el-tab-pane>
            <el-tab-pane label="发留言" name="sendMsg"></el-tab-pane>
          </el-tabs>
        </div>

        <!-- 我收到的留言列表（含回复功能） -->
        <div v-show="activeMsgTab === 'receivedMsgs'" class="message-content">
          <div v-if="receivedMessages.length === 0" class="empty-placeholder">
            <i class="el-icon-chat-dot-round"></i>
            <p>暂无收到的留言</p>
          </div>
          <div v-else class="message-list">
            <div class="message-card" v-for="msg in receivedMessages" :key="msg.id">
              <div class="msg-header">
                <div class="msg-sender">
                  <i class="el-icon-user"></i>
                  <span>{{ msg.fromName }}</span>
                </div>
                <span class="msg-time">{{ msg.time }}</span>
              </div>
              <div class="msg-body">{{ msg.content }}</div>
              <div class="msg-reply" v-if="msg.reply">
                <div class="reply-label">我的回复：</div>
                <div class="reply-content">{{ msg.reply }}</div>
              </div>
              <div class="msg-actions" v-if="!msg.reply">
                <el-input v-model="msg.replyInput" placeholder="写下回复..." size="small" class="reply-input"></el-input>
                <el-button type="primary" size="small" @click="submitReply(msg)">回复</el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 发留言 -->
        <div v-show="activeMsgTab === 'sendMsg'" class="message-content">
          <div class="send-message-form">
            <el-form label-width="80px">
              <el-form-item label="选择好友">
                <el-select v-model="sendMsgForm.toUserId" placeholder="请选择好友" filterable class="friend-select">
                  <el-option
                    v-for="friend in myFriendsList"
                    :key="friend.id"
                    :label="friend.name"
                    :value="friend.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="留言内容">
                <el-input
                  type="textarea"
                  v-model="sendMsgForm.content"
                  :rows="4"
                  placeholder="请输入留言内容...">
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="sendMessage" :disabled="!sendMsgForm.toUserId || !sendMsgForm.content">发送留言</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </div>

    <!-- 最新动态 - 嵌入新闻组件 -->
    <div class="function-section">
      <h2 class="section-title">最新动态</h2>
      <NoticeIndex />
    </div>

  </div>
</template>

<script>
import * as echarts from 'echarts'
import NoticeIndex from '@/views/system/notice/noticeList'

export default {
  name: 'HomeIndex',
  components: {
    NoticeIndex
  },
  data() {
    return {
      // 当前登录用户ID（模拟，实际应从store或接口获取）
      currentUserId: 1,
      currentUserName: '当前用户',

      // 好友相关列表
      myFriendsList: [],
      sentRequestsList: [],
      receivedRequestsList: [],
      activeFriendTab: 'myFriends',

      // 留言相关
      receivedMessages: [],
      activeMsgTab: 'receivedMsgs',
      sendMsgForm: {
        toUserId: '',
        content: ''
      },

      // 统计数据
      statList: [
        { label: '注册校友', value: '3,682', bg: 'linear-gradient(135deg,#667eea 0%,#764ba2 100%)', icon: 'el-icon-user-solid' },
        { label: '报名人数', value: '1,259', bg: 'linear-gradient(135deg,#FF9A9E 0%,#FAD0C4 100%)', icon: 'el-icon-edit' },
        { label: '服务地区', value: '28', bg: 'linear-gradient(135deg,#FFECD2 0%,#FCB69F 100%)', icon: 'el-icon-position' },
        { label: '互动帖子', value: '8,965', bg: 'linear-gradient(135deg,#84FAB0 0%,#8FD3F4 100%)', icon: 'el-icon-chat-dot-round' }
      ],

      // 核心功能列表
      funcList: [
        { name: '我的桌面', desc: '个人空间', icon: 'el-icon-user-solid', bgColor: '#4361ee', path: '/system/alumni' },
        { name: '校友录', desc: '校友信息查询', icon: 'el-icon-user', bgColor: '#38bdf8', path: '/system/admission/search' },
        { name: '通知公告', desc: '校园通知实时查看', icon: 'el-icon-bell', bgColor: '#fb923c', path: '/system/notice/list/1' },
        { name: '政策解读', desc: '重要政策详细说明', icon: 'el-icon-document', bgColor: '#8b5cf6', path: '/system/notice/list/2' },
        { name: '个人风采', desc: '展示个人优秀事迹', icon: 'el-icon-star-on', bgColor: '#ec4899', path: '/system/feeling' },
        { name: '互动论坛', desc: '校友交流与经验分享', icon: 'el-icon-chat-dot-round', bgColor: '#06b6d4', path: '/system/forum' },
        { name: '我的留言', desc: '给母校留言寄语', icon: 'el-icon-chat-line-round', bgColor: '#f43f5e', path: '/system/message' },
        { name: '题库练习', desc: '西部计划专项题库刷题', icon: 'el-icon-s-data', bgColor: '#10b981', path: '/system/question' },
      ],

      yearChart: null,
      areaChart: null,
      resizeTimer: null
    }
  },
  computed: {
    // 计算待处理的好友申请数量（状态为pending）
    pendingReceivedCount() {
      return this.receivedRequestsList.filter(req => req.status === 'pending').length
    },
    // 计算未回复的留言数量（reply为空字符串表示未回复）
    unreadMessagesCount() {
      return this.receivedMessages.filter(msg => !msg.reply).length
    }
  },
  mounted() {
    // 延迟初始化，确保DOM已渲染
    this.$nextTick(() => {
      this.initCharts()
    })
    this.loadFriendData()
    this.loadMessageData()

    // 添加控制台输出，方便调试
    console.log('模拟数据加载完成')
    console.log('好友列表:', this.myFriendsList)
    console.log('留言列表:', this.receivedMessages)
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize)
    if (this.yearChart) {
      this.yearChart.dispose()
      this.yearChart = null
    }
    if (this.areaChart) {
      this.areaChart.dispose()
      this.areaChart = null
    }
  },
  methods: {
    // 加载模拟的好友数据
    loadFriendData() {
      // 模拟数据 - 实际开发中替换为API调用
      this.myFriendsList = [
        { id: 2, name: '李明', school: '德州学院 2020届', status: 'friend' },
        { id: 3, name: '王芳', school: '德州学院 2021届', status: 'friend' },
        { id: 4, name: '张伟', school: '德州学院 2019届', status: 'friend' },
        { id: 5, name: '赵丽颖', school: '德州学院 2018届', status: 'friend' }
      ]

      this.sentRequestsList = [
        { id: 101, toId: 6, toName: '陈思琪', status: 'pending' },
        { id: 102, toId: 7, toName: '刘洋', status: 'accepted' },
        { id: 103, toId: 8, toName: '孙浩', status: 'rejected' }
      ]

      this.receivedRequestsList = [
        { id: 201, fromId: 9, fromName: '周杰', status: 'pending' },
        { id: 202, fromId: 10, fromName: '吴迪', status: 'pending' },
        { id: 203, fromId: 11, fromName: '郑爽', status: 'pending' }
      ]
    },

    // 加载留言数据
    loadMessageData() {
      this.receivedMessages = [
        {
          id: 1,
          fromId: 2,
          fromName: '李明',
          content: '最近在准备西部计划的考试，有没有什么复习建议？',
          time: '2024-12-15 10:30',
          reply: '',
          replyInput: ''
        },
        {
          id: 2,
          fromId: 3,
          fromName: '王芳',
          content: '看到你分享的志愿服务经历很受鼓舞，希望能向你学习！',
          time: '2024-12-14 15:20',
          reply: '谢谢鼓励，互相学习，一起加油！',
          replyInput: ''
        },
        {
          id: 3,
          fromId: 4,
          fromName: '张伟',
          content: '咱们校友会最近有什么活动吗？',
          time: '2024-12-13 09:15',
          reply: '',
          replyInput: ''
        }
      ]
    },

    getStatusText(status) {
      const map = {
        pending: '待处理',
        accepted: '已接受',
        rejected: '已拒绝'
      }
      return map[status] || status
    },

    // 发送留言给好友
    sendMessageToFriend(friend) {
      this.sendMsgForm.toUserId = friend.id
      this.activeMsgTab = 'sendMsg'
      this.$message.info(`准备给 ${friend.name} 发送留言`)
    },

    // 提交发送留言
    sendMessage() {
      const friend = this.myFriendsList.find(f => f.id === this.sendMsgForm.toUserId)
      if (!friend) {
        this.$message.warning('请选择好友')
        return
      }

      // 模拟发送留言
      const newMsg = {
        id: Date.now(),
        fromId: this.currentUserId,
        fromName: this.currentUserName,
        toId: this.sendMsgForm.toUserId,
        toName: friend.name,
        content: this.sendMsgForm.content,
        time: new Date().toLocaleString(),
        reply: ''
      }

      this.$message.success(`已向 ${friend.name} 发送留言`)
      console.log('发送的留言:', newMsg)

      this.sendMsgForm.content = ''
      this.sendMsgForm.toUserId = ''
      this.activeMsgTab = 'receivedMsgs'
    },

    // 提交回复
    submitReply(msg) {
      if (!msg.replyInput || !msg.replyInput.trim()) {
        this.$message.warning('请输入回复内容')
        return
      }
      msg.reply = msg.replyInput
      msg.replyInput = ''
      this.$message.success('回复成功')
      console.log('回复内容:', msg.reply)
    },

    // 删除好友
    removeFriend(friend) {
      this.$confirm(`确定要删除好友 ${friend.name} 吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.myFriendsList.findIndex(f => f.id === friend.id)
        if (index !== -1) {
          this.myFriendsList.splice(index, 1)
          this.$message.success('删除成功')
        }
      }).catch(() => {})
    },

    // 撤销发出的申请
    cancelRequest(req) {
      this.$confirm('确定要撤销该好友申请吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.sentRequestsList.findIndex(r => r.id === req.id)
        if (index !== -1) {
          this.sentRequestsList.splice(index, 1)
          this.$message.success('已撤销申请')
        }
      }).catch(() => {})
    },

    // 接受好友申请
    acceptRequest(req) {
      const index = this.receivedRequestsList.findIndex(r => r.id === req.id)
      if (index !== -1) {
        // 将申请状态改为已接受，并从列表中移除（或标记为已处理）
        this.receivedRequestsList.splice(index, 1)
        const newFriend = {
          id: req.fromId,
          name: req.fromName,
          school: '德州学院校友'
        }
        this.myFriendsList.push(newFriend)
        this.$message.success(`已接受 ${req.fromName} 的好友申请`)
      }
    },

    // 拒绝好友申请
    rejectRequest(req) {
      this.$confirm('确定要拒绝该好友申请吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const index = this.receivedRequestsList.findIndex(r => r.id === req.id)
        if (index !== -1) {
          this.receivedRequestsList.splice(index, 1)
          this.$message.success('已拒绝申请')
        }
      }).catch(() => {})
    },

    initCharts() {
      // 确保DOM元素存在
      const yearChartDom = document.getElementById('yearChart')
      const areaChartDom = document.getElementById('areaChart')

      if (yearChartDom && areaChartDom) {
        this.yearChart = echarts.init(yearChartDom)
        this.yearChart.setOption({
          color: ['#667eea', '#67C23A'],
          tooltip: { trigger: 'axis' },
          grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
          xAxis: { type: 'category', data: ['1月', '2月', '3月', '4月', '5月', '6月'] },
          yAxis: { type: 'value' },
          series: [
            { name: '报名人数', type: 'line', smooth: true, data: [120, 182, 231, 254, 290, 320] },
            { name: '录取人数', type: 'bar', data: [80, 150, 190, 210, 240, 270] }
          ]
        })

        this.areaChart = echarts.init(areaChartDom)
        this.areaChart.setOption({
          color: ['#FF9A9E', '#FFCE54', '#84FAB0', '#8FD3F4', '#FCB69F'],
          tooltip: { trigger: 'item' },
          legend: { orient: 'vertical', left: 'left' },
          series: [
            {
              name: '服务地区',
              type: 'pie',
              radius: ['40%', '70%'],
              data: [
                { value: 1024, name: '新疆' },
                { value: 835, name: '四川' },
                { value: 682, name: '云南' },
                { value: 456, name: '西藏' },
                { value: 320, name: '甘肃' }
              ]
            }
          ]
        })

        window.addEventListener('resize', this.handleResize)
      }
    },

    handleResize() {
      if (this.resizeTimer) clearTimeout(this.resizeTimer)
      this.resizeTimer = setTimeout(() => {
        this.yearChart?.resize()
        this.areaChart?.resize()
      }, 100)
    },

    goPage(path) {
      this.$router.push(path).catch(() => {})
    }
  }
}
</script>

<style scoped>
/* 全局页面容器：渐变背景 + 柔和光影 */
.home-container {
  padding: 24px;
  background: linear-gradient(135deg, #f0f4ff 0%, #f8faff 100%);
  min-height: 100vh;
}

/* 顶部 Banner：高级渐变 + 精致阴影 */
.welcome-banner {
  height: 220px;
  border-radius: 20px;
  background: linear-gradient(135deg, #4361ee 0%, #3b82f6 50%, #8b5cf6 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 32px;
  color: #fff;
  text-align: center;
  box-shadow: 0 12px 30px rgba(67, 97, 238, 0.25);
  position: relative;
  overflow: hidden;
}

.title-box h1 {
  font-size: 38px;
  font-weight: bold;
  margin: 0 0 10px 0;
  letter-spacing: 1px;
}

.title-box p {
  font-size: 17px;
  opacity: 0.92;
  margin: 0;
  letter-spacing: 0.5px;
}

/* 模块标题 */
.section-title {
  font-size: 22px;
  margin: 0 0 24px 10px;
  color: #1e293b;
  font-weight: 600;
  border-left: 5px solid #4361ee;
  padding-left: 12px;
}

/* 统计数据卡片 */
.stat-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 54px;
  height: 54px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-icon i {
  font-size: 28px;
  color: white;
}

.stat-info h3 {
  margin: 0 0 5px 0;
  font-size: 24px;
  font-weight: bold;
  color: #1e293b;
}

.stat-info p {
  margin: 0;
  font-size: 14px;
  color: #64748b;
}

/* 图表区域 */
.charts-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
}

.chart-box {
  background: white;
  border-radius: 16px;
  padding: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

/* 社交模块双栏布局 */
.social-modules {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  margin-bottom: 40px;
  margin-top: 40px;
}

.friend-list-section,
.message-section {
  background: #ffffff;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.04);
  margin-bottom: 0;
}

.section-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 16px;
}

.section-header .section-title {
  margin-bottom: 0;
}

.friend-tabs,
.msg-tabs {
  margin-right: 10px;
}

/* 红点样式 */
.red-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  background-color: #f56c6c;
  border-radius: 50%;
  margin-left: 6px;
  vertical-align: middle;
  position: relative;
  top: -1px;
}

.friend-content,
.message-content {
  min-height: 400px;
  max-height: 450px;
  overflow-y: auto;
}

/* 好友卡片网格 */
.friend-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 16px;
}

.friend-card {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f8fafc;
  border-radius: 16px;
  transition: all 0.2s;
  border: 1px solid #e2e8f0;
}

.friend-card:hover {
  background: #f1f5f9;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.friend-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  flex-shrink: 0;
}

.friend-avatar i {
  font-size: 24px;
  color: white;
}

.friend-info {
  flex: 1;
  min-width: 0;
}

.friend-info h4 {
  margin: 0 0 4px 0;
  font-size: 15px;
  font-weight: 600;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.friend-info p {
  margin: 0;
  font-size: 12px;
  color: #64748b;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.friend-actions {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.friend-actions .el-button {
  padding: 4px 8px;
  font-size: 12px;
}

.delete-btn {
  color: #f43f5e;
}

/* 申请列表样式 */
.request-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.request-item {
  display: flex;
  align-items: center;
  padding: 12px;
  background: #f8fafc;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
}

.request-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #cbd5e1;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  flex-shrink: 0;
}

.request-avatar i {
  font-size: 20px;
  color: #475569;
}

.request-info {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.request-name {
  font-weight: 500;
  font-size: 14px;
}

.request-status {
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 20px;
}

.request-status.pending {
  background: #fef3c7;
  color: #d97706;
}

.request-status.accepted {
  background: #d1fae5;
  color: #059669;
}

.request-status.rejected {
  background: #fee2e2;
  color: #dc2626;
}

/* 留言卡片样式 */
.message-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.message-card {
  background: #f8fafc;
  border-radius: 16px;
  padding: 16px;
  border: 1px solid #e2e8f0;
}

.msg-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.msg-sender {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
}

.msg-sender i {
  color: #4361ee;
}

.msg-time {
  font-size: 12px;
  color: #94a3b8;
}

.msg-body {
  margin-bottom: 12px;
  padding: 8px 12px;
  background: white;
  border-radius: 12px;
  line-height: 1.5;
  color: #334155;
}

.msg-reply {
  background: #eef2ff;
  border-radius: 12px;
  padding: 10px 12px;
  margin-bottom: 12px;
}

.reply-label {
  font-size: 12px;
  color: #4361ee;
  margin-bottom: 4px;
  font-weight: 500;
}

.reply-content {
  font-size: 14px;
  color: #1e293b;
}

.msg-actions {
  display: flex;
  gap: 12px;
  align-items: center;
}

.reply-input {
  flex: 1;
}

/* 发留言表单 */
.send-message-form {
  padding: 8px 0;
}

.friend-select {
  width: 100%;
}

/* 空状态占位符 */
.empty-placeholder {
  text-align: center;
  padding: 60px 20px;
  color: #94a3b8;
}

.empty-placeholder i {
  font-size: 64px;
  margin-bottom: 16px;
  opacity: 0.5;
}

.empty-placeholder p {
  margin: 0;
  font-size: 14px;
}

/* 功能宫格 */
.function-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 22px;
  margin-bottom: 20px;
}

.function-item {
  background: #ffffff;
  border-radius: 18px;
  padding: 30px 20px;
  text-align: center;
  cursor: pointer;
  transition: all 0.32s ease;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.8);
}

.function-item:hover {
  transform: translateY(-8px);
  box-shadow: 0 14px 30px rgba(0, 0, 0, 0.09);
}

.function-item .func-icon {
  width: 66px;
  height: 66px;
  border-radius: 50%;
  margin: 0 auto 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 6px 14px rgba(0, 0, 0, 0.12);
}

.function-item .func-icon i {
  font-size: 28px;
  color: #fff;
}

.function-item h4 {
  font-size: 17px;
  font-weight: 600;
  margin: 0 0 8px 0;
  color: #1e293b;
}

.function-item p {
  font-size: 13px;
  color: #64748b;
  margin: 0;
}

/* 响应式适配 */
@media (max-width: 1200px) {
  .social-modules {
    grid-template-columns: 1fr;
    gap: 24px;
  }

  .stat-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 992px) {
  .function-grid {
    grid-template-columns: repeat(3, 1fr);
  }

  .charts-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .function-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .stat-grid {
    grid-template-columns: 1fr;
  }

  .welcome-banner {
    height: 180px;
  }

  .title-box h1 {
    font-size: 28px;
  }

  .section-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
}
</style>
