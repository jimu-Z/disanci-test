<template>
  <!-- 新闻详情页整体容器 -->
  <div class="news-detail-container">
    <div class="news-detail-wrapper">
      <!-- 标题 -->
      <h1 class="news-title">{{ questionData.bankName }}</h1>

      <!-- 新闻信息栏：时间 + 作者 -->
      <div class="news-info">
        <span>{{ questionData.batch }}</span>

      </div>

      <!-- 新闻封面图 -->
      <div class="news-cover" v-if="questionData.coverImg">
        <img :src="questionData.coverImg" alt="封面" />
      </div>

      <!-- 新闻正文内容 -->
      <div class="news-content">
        <div >
          {{ questionData.questionContent }}
        </div>
      </div>
       <!-- 答案 -->
      <hr/>
      <div class="news-title">答案</div>
      <div class="news-content">
        <div >
          {{ questionData.correctAnswer }}
        </div>
      </div>

      <!-- 返回按钮 -->
      <div class="back-btn">
        <button @click="$router.back()">返回列表</button>
      </div>
    </div>
  </div>
</template>

<script >
import { mapGetters } from 'vuex';
import { hasPermi } from '@/utils/permission'
import { getQuestion_bank } from '@/api/system/question_bank'
export default {
  name: 'ForumIndex',
  data() {
    return {
       // 帖子ID
      questionId: null,
      questionData: null,
// 模拟新闻数据（实际项目中从接口获取）
 newsData : {
  title: "2026年全国科技创新大会召开，部署人工智能与新能源发展新战略",
  publishTime: "2026-03-13 14:30",
  author: "科技日报记者 张三",
  coverImg: "https://picsum.photos/800/450", // 随机示例图
  content: [
    "3月13日，2026年全国科技创新大会在北京隆重召开。会议围绕人工智能、新能源、生物医药等重点领域，明确了未来五年国家科技发展规划与战略方向。",
    "会议指出，人工智能已成为全球科技竞争的核心领域，我国将持续加大基础研究投入，突破大模型、机器人控制、自主学习等关键技术，推动人工智能与制造业、农业、医疗、交通等行业深度融合。",
    "在新能源方面，会议强调加快光伏、储能、氢能技术迭代，构建清洁低碳、安全高效的现代能源体系，力争2030年非化石能源消费比重进一步提升。",
    "科技部相关负责人表示，本次大会发布的政策将大幅减轻科研人员负担，强化企业科技创新主体地位，鼓励产学研协同创新，让科技成果更好服务经济社会高质量发展。",
    "来自全国高校、科研院所、科技企业的代表共计800余人参加本次会议，多位院士专家围绕前沿技术作专题报告。"
  ]
}
    }
  },
  created() {
    // 获取帖子ID
    this.questionId = this.$route.params.id;
    // 获取帖子详情
    this.getQuestionDetail();
  },
  methods: {
    // 获取新闻详情
    async getQuestionDetail() {
      try {
        const res = await getQuestion_bank(this.questionId);
        this.questionData = res.data;
      } catch (error) {
        console.error(error);
      }
    }
  },
  computed: {
    ...mapGetters(['isLogin', 'id', 'permissions']),
    // 权限判断方法
    hasPermi: function() {
      return hasPermi;
    }
  }

}
</script>

<style scoped>
/* 全局样式重置 + 页面背景 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Microsoft YaHei", sans-serif;
}

.news-detail-container {
  width: 100%;
  min-height: 100vh;
  background-color: #f5f7fa; /* 柔和背景色 */
  padding: 30px 15px;
}

/* 内容容器：居中 + 卡片样式 */
.news-detail-wrapper {
  max-width: 800px;
  margin: 0 auto;
  background: #ffffff;
  padding: 45px 55px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

/* 标题样式 */
.news-title {
  font-size: 28px;
  color: #2c3e50;
  line-height: 1.4;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
}

/* 信息栏：时间、作者 */
.news-info {
  display: flex;
  justify-content: center;
  gap: 30px;
  color: #7f8c8d;
  font-size: 15px;
  margin-bottom: 30px;
}

/* 新闻封面 */
.news-cover {
  width: 100%;
  margin-bottom: 35px;
  border-radius: 10px;
  overflow: hidden;
}

.news-cover img {
  width: 100%;
  height: auto;
  display: block;
  object-fit: cover;
}

/* 正文样式 */
.news-content {
  color: #34495e;
  font-size: 17px;
  line-height: 1.8;
}

.paragraph {
  margin-bottom: 22px;
  text-align: justify;
}

/* 返回按钮 */
.back-btn {
  margin-top: 40px;
  text-align: center;
}

.back-btn button {
  padding: 12px 30px;
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.back-btn button:hover {
  background-color: #2980b9;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .news-detail-wrapper {
    padding: 25px 20px;
  }

  .news-title {
    font-size: 22px;
  }

  .news-info {
    flex-direction: column;
    gap: 8px;
    text-align: center;
  }
}
</style>
