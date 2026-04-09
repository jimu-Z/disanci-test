<template>
  <div class="news-list-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <h2 class="page-title">最新资讯</h2>
      <p class="page-desc">实时掌握行业动态，一手资讯抢先看</p>
    </div>

    <!-- 新闻列表 -->
    <div class="news-list">
      <div
        class="news-item"
        v-for="item in newsList"
        :key="item.id"
        @click="goToDetail(item.id)"
      >
        <!-- 新闻封面图 -->
        <div class="news-img">
          <img :src="item.imgUrl" alt="新闻封面" />
          <!-- 新闻类型标签 -->
          <span class="news-tag" :style="{ backgroundColor: item.tagColor }">
            {{ item.tag }}
          </span>
        </div>

        <!-- 新闻内容 -->
        <div class="news-content">
          <h3 class="news-title">{{ item.title }}</h3>
          <p class="news-desc">{{ item.desc }}</p>
          <div class="news-footer">
            <span class="news-time">{{ item.time }}</span>
            <span class="news-author">作者：{{ item.author }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 无数据提示 -->
    <div class="empty-data" v-if="newsList.length === 0">
      暂无新闻数据
    </div>
  </div>
</template>

<script>
export default {
  name: 'NewsList',
  data() {
    return {
      // 新闻列表模拟数据
      newsList: [
        {
          id: 1,
          title: '2026年科技博览会圆满落幕，创新成果惊艳全场',
          desc: '本次博览会汇聚全球2000+科技企业，展示人工智能、新能源、生物医药等领域前沿技术，签约金额突破百亿',
          imgUrl: 'https://picsum.photos/seed/news1/400/225',
          tag: '科技',
          tagColor: '#FF6B6B',
          time: '2026-03-22',
          author: '科技日报'
        },
        {
          id: 2,
          title: '全国春季旅游旺季开启，热门景区预约量暴涨300%',
          desc: '随着气温回升，全国各大景区迎来春游高峰，文旅部门推出多项优惠政策，助力旅游市场持续升温',
          imgUrl: 'https://picsum.photos/seed/news2/400/225',
          tag: '旅游',
          tagColor: '#4ECDC4',
          time: '2026-03-21',
          author: '旅游网'
        },
        {
          id: 3,
          title: '教育部发布新政策，全面推进素质教育改革',
          desc: '教育部最新通知，将艺术、体育、劳动教育纳入学生综合素质评价体系，促进学生全面发展',
          imgUrl: 'https://picsum.photos/seed/news3/400/225',
          tag: '教育',
          tagColor: '#FFD166',
          time: '2026-03-20',
          author: '教育在线'
        },
        {
          id: 4,
          title: '新能源汽车销量持续攀升，市场占有率突破50%',
          desc: '2026年第一季度新能源汽车销量同比增长80%，续航技术革新、价格下探成为核心驱动力',
          imgUrl: 'https://picsum.photos/seed/news4/400/225',
          tag: '汽车',
          tagColor: '#06D6A0',
          time: '2026-03-19',
          author: '汽车之家'
        },
        {
          id: 5,
          title: '健康中国行动推进，全民健康素养水平稳步提升',
          desc: '国家卫健委发布数据，我国居民健康素养水平较去年提升5个百分点，健康生活理念深入人心',
          imgUrl: 'https://picsum.photos/seed/news5/400/225',
          tag: '健康',
          tagColor: '#118AB2',
          time: '2026-03-18',
          author: '健康日报'
        }
      ]
    };
  },
  methods: {
    // 跳转到新闻详情页
    goToDetail(id) {
      this.$router.push({
        path: '/noticeDetail',
        query: { id } // 传递新闻id
      });
    }
  }
};
</script>

<style scoped>
/* 页面容器 */
.news-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
  background-color: #f8f9fa;
  min-height: 100vh;
}

/* 页面头部 */
.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-title {
  font-size: 32px;
  color: #2d3436;
  margin-bottom: 10px;
  font-weight: bold;
  position: relative;
  display: inline-block;
}

.page-title::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 4px;
  background: linear-gradient(90deg, #FF6B6B, #4ECDC4);
  border-radius: 2px;
}

.page-desc {
  font-size: 16px;
  color: #636e72;
  margin-top: 15px;
}

/* 新闻列表 */
.news-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(550px, 1fr));
  gap: 25px;
}

/* 新闻卡片 */
.news-item {
  background: #ffffff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
}

.news-item:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

/* 新闻图片 */
.news-img {
  position: relative;
  width: 100%;
  height: 225px;
}

.news-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.news-tag {
  position: absolute;
  top: 15px;
  left: 15px;
  padding: 5px 12px;
  border-radius: 20px;
  color: #fff;
  font-size: 14px;
  font-weight: 500;
}

/* 新闻内容 */
.news-content {
  padding: 20px;
  flex: 1;
}

.news-title {
  font-size: 20px;
  color: #2d3436;
  font-weight: 600;
  margin-bottom: 12px;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-desc {
  font-size: 14px;
  color: #636e72;
  line-height: 1.6;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-footer {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  color: #b2bec3;
}

/* 无数据样式 */
.empty-data {
  text-align: center;
  padding: 60px 0;
  color: #999;
  font-size: 16px;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .news-list {
    grid-template-columns: 1fr;
  }

  .page-title {
    font-size: 26px;
  }

  .news-title {
    font-size: 18px;
  }
}
</style>
