<template>
  <div class="home-container">
    <!-- 顶部欢迎区域 -->
    <div class="welcome-banner">
      <div class="title-box">
        <h1>西部计划校友共建平台</h1>
        <p>奉献 · 友爱 · 互助 · 进步</p>
      </div>
    </div>

    <!-- 数据统计卡片 -->
    <el-row :gutter="20" class="stat-row">
      <el-col :xs="24" :sm="12" :lg="6" v-for="item in statList" :key="item.label">
        <div class="stat-card" :style="{background: item.bg}">
          <div class="card-left">
            <p class="label">{{ item.label }}</p>
            <h2 class="value">{{ item.value }}</h2>
          </div>
          <i :class="item.icon"></i>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :xs="24" :lg="12">
        <div class="chart-box">
          <h3>年度报名人数趋势</h3>
          <div id="yearChart" class="chart-item"></div>
        </div>
      </el-col>
      <el-col :xs="24" :lg="12">
        <div class="chart-box">
          <h3>服务地区分布</h3>
          <div id="areaChart" class="chart-item"></div>
        </div>
      </el-col>
    </el-row>

    <!-- 核心功能入口 -->
    <div class="function-section">
      <h2 class="section-title">平台核心功能</h2>
      <div class="function-grid">
        <div class="function-item" v-for="item in funcList" :key="item.path" @click="goPage(item.path)">
          <div class="func-icon" :style="{background: item.color}">
            <i :class="item.icon"></i>
          </div>
          <h4>{{ item.name }}</h4>
          <p>{{ item.desc }}</p>
        </div>
      </div>
    </div>

    <!-- ===================== 【最新动态 - 嵌入新闻组件】 ===================== -->
    <div class="function-section">
      <h2 class="section-title">最新动态</h2>
      <NoticeIndex />
    </div>
    <!-- ===================================================================== -->

  </div>
</template>

<script>
import * as echarts from 'echarts'
// 引入新闻分类组件
import NoticeIndex from '@/views/system/notice/noticeList'

export default {
  name: 'HomeIndex',
  components: {
    NoticeIndex
  },
  data() {
    return {
      statList: [
        { label: '注册校友', value: '3,682', bg: 'linear-gradient(135deg,#667eea 0%,#764ba2 100%)', icon: 'el-icon-user-solid' },
        { label: '报名人数', value: '1,259', bg: 'linear-gradient(135deg,#FF9A9E 0%,#FAD0C4 100%)', icon: 'el-icon-edit' },
        { label: '服务地区', value: '28', bg: 'linear-gradient(135deg,#FFECD2 0%,#FCB69F 100%)', icon: 'el-icon-position' },
        { label: '互动帖子', value: '8,965', bg: 'linear-gradient(135deg,#84FAB0 0%,#8FD3F4 100%)', icon: 'el-icon-chat-dot-round' }
      ],
      funcList: [
        { name: '报名管理', desc: '西部计划报名', icon: 'el-icon-document', color: '#67C23A', path: '/system/apply' },
        { name: '录取管理', desc: '录取信息', icon: 'el-icon-user', color: '#409EFF', path: '/system/admission' },
        { name: '校友查询', desc: '校友查询', icon: 'el-icon-book', color: '#E6A23C', path: '/system/admission/search' },
        { name: '留言管理', desc: '给管理员留言', icon: 'el-icon-chat-line', color: '#ff88bb', path: '/system/message' },
        { name: '互动论坛', desc: '经验交流分享', icon: 'el-icon-chat-line', color: '#F56C6C', path: '/system/forum' },
        { name: '帖子审核', desc: '审核帖子', icon: 'el-icon-chat-line', color: '#056C60', path: '/system/forum/post/audit' },
        { name: '校友风采', desc: '校友荣誉信息', icon: 'el-icon-s-data', color: '#722ED1', path: '/system/feeling' },
        { name: '通知公告', desc: '政策与消息', icon: 'el-icon-bell', color: '#FF6B81', path: '/system/notice/list' }
      ],
      yearChart: null,
      areaChart: null,
      resizeTimer: null
    }
  },
  mounted() {
    this.initCharts()
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
    initCharts() {
      this.yearChart = echarts.init(document.getElementById('yearChart'))
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

      this.areaChart = echarts.init(document.getElementById('areaChart'))
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
.home-container {
  padding: 20px;
  background: #f5f7fa;
  min-height: 100vh;
}

.welcome-banner {
  height: 200px;
  border-radius: 16px;
  background: linear-gradient(135deg, #4158D0 0%, #C850C0 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: #fff;
  text-align: center;
  box-shadow: 0 8px 20px rgba(65,88,208,0.2);
}
.title-box h1 {
  font-size: 32px;
  margin: 0 0 8px 0;
}
.title-box p {
  font-size: 16px;
  opacity: 0.9;
  margin: 0;
}

.stat-row {
  margin-bottom: 20px;
}
.stat-card {
  height: 110px;
  border-radius: 16px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
  box-shadow: 0 6px 15px rgba(0,0,0,0.1);
}
.stat-card .card-left .label {
  font-size: 14px;
  opacity: 0.9;
  margin: 0 0 6px 0;
}
.stat-card .card-left .value {
  font-size: 28px;
  font-weight: bold;
  margin: 0;
}
.stat-card i {
  font-size: 40px;
  opacity: 0.8;
}

.chart-row {
  margin-bottom: 20px;
}
.chart-box {
  background: #fff;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}
.chart-box h3 {
  margin: 0 0 15px 0;
  font-size: 16px;
  color: #333;
}
.chart-item {
  width: 100%;
  height: 300px;
}

.function-section {
  margin-bottom: 20px;
}
.section-title {
  font-size: 20px;
  margin: 0 0 20px 10px;
  color: #333;
  border-left: 4px solid #4158D0;
  padding-left: 10px;
}
.function-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}
.function-item {
  background: #fff;
  border-radius: 16px;
  padding: 25px 20px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}
.function-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.1);
}
.function-item .func-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin: 0 auto 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.function-item .func-icon i {
  font-size: 26px;
  color: #fff;
}
.function-item h4 {
  font-size: 16px;
  margin: 0 0 6px 0;
  color: #333;
}
.function-item p {
  font-size: 12px;
  color: #999;
  margin: 0;
}

@media (max-width: 768px) {
  .function-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  .welcome-banner {
    height: 160px;
  }
  .title-box h1 {
    font-size: 24px;
  }
}
</style>
