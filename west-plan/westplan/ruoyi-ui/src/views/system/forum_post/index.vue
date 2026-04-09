<template>
  <div class="forum-container">
    <!-- 顶部横幅区 -->
    <div class="forum-banner">
      <div class="banner-content container">
        <div class="banner-text">
          <h1 class="banner-title">校友论坛</h1>
          <p class="banner-desc">分享工作经验 · 交流生活心得</p>
        </div>
        <el-button
          type="primary"
          size="large"
          icon="el-icon-plus"
          @click="toPostAdd"
          class="publish-btn"
        >
          发布新帖
        </el-button>
      </div>
    </div>

    <!-- 核心内容区 -->
    <div class="forum-main container">
      <!-- 左侧主内容 -->
      <div class="main-left">
        <!-- 板块导航 -->
        <div class="forum-card board-nav">
      
          <div class="board-list">
            <div
              class="board-item"
              :class="{ active: activeBoardId === board.id }"
              v-for="board in boardList"
              :key="board.id"
              @click="handleBoardChange(board.id)"
            >
              <div class="board-icon">
                <i class="el-icon-menu"></i>
              </div>
              <div class="board-info">
                <p class="board-name">{{ board.boardName }}</p>
                <p class="board-count">{{ board.postCount || 0 }}篇帖子</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 置顶热点帖子 -->
        <div class="forum-card hot-post" v-if="topHotPostList&&topHotPostList.length > 0">
          <h3 class="card-title">
            <i class="el-icon-hot-water card-icon"></i>
            置顶 · 热点
          </h3>
          <div class="hot-post-list">
            <div
              class="hot-post-item"
              v-for="(post, index) in topHotPostList"
              :key="post.id"
              @click="toPostDetail(post.id)"
            >
              <span class="hot-index" :style="{ background: index < 3 ? '#FF3366' : '#FF9933' }">{{ index + 1 }}</span>
              <div class="hot-post-info">
                <p class="hot-post-title">{{ post.postTitle }}</p>
                <div class="hot-post-meta">
                  <span>{{ post.userName }}</span>
                  <span>{{ formatTime(post.createTime, 'MM-DD HH:mm') }}</span>
                  <span><i class="el-icon-view"></i> {{ post.viewCount }}</span>
                  <span><i class="el-icon-chat-dot-round"></i> {{ post.commentCount }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 板块帖子列表 -->
        <div class="forum-card post-list">
          <h3 class="card-title">
            <i class="el-icon-document card-icon"></i>
            {{ activeBoardId ? currentBoardName + ' · 帖子' : '最新发布 · 帖子' }}

          </h3>
          <div class="post-item" v-for="post in postList" :key="post.id" @click="toPostDetail(post.id)">
            <!-- 帖子元信息 -->
            <div class="post-meta">
              <div class="meta-left">
                <el-tag type="success" size="mini" v-if="post.isTop">置顶</el-tag>
                <el-tag type="warning" size="mini" v-if="post.isHot">热点</el-tag>
                <span class="post-title">{{ post.postTitle }}</span>
                <span class="author">{{ post.userName }}</span>
                <span class="time">{{ formatTime(post.createTime) }}</span>
              </div>
              <div class="meta-right">
                <span><i class="el-icon-view"></i> {{ post.viewCount }}</span>
                <span><i class="el-icon-like"></i> {{ post.likeCount }}</span>
                <span><i class="el-icon-chat-dot-round"></i> {{ post.commentCount }}</span>
                <el-tag type="info" size="mini" v-if="post.auditStatus === 1">{{ post.boardName }}</el-tag>
              </div>
            </div>
          </div>

          <!-- 空数据占位 -->
          <div class="empty-post" v-if="postList==null||postList.length === 0 && !loading">
            <el-empty description="该板块暂无帖子，快来发布第一篇吧~"></el-empty>
          </div>
        </div>

        <!-- 板块帖子分页列表 -->
        <el-card class="forum-card post-table" shadow="hover">
          <h3 class="card-title">
            <i class="el-icon-data-board card-icon"></i>
            所有帖子
            <span class="text-sm text-gray-500" v-if="activeBoardId">
              {{ currentBoardName }} (共{{ total }}条)
            </span>
          </h3>
          <el-table
            :data="postList"
            border
            stripe
            v-loading="loading"
            @row-click="toPostDetail"
            row-class-name="cursor-pointer"
            header-cell-class-name="table-header"
          >
            <el-table-column prop="postTitle" label="帖子标题" min-width="200" />
            <el-table-column prop="boardName" label="所属板块" width="120" />
            <el-table-column prop="userName" label="发布人" width="100" />
            <el-table-column prop="viewCount" label="浏览量" width="80" align="center" />
            <el-table-column prop="likeCount" label="点赞数" width="80" align="center" />
            <el-table-column prop="commentCount" label="评论数" width="80" align="center" />
            <el-table-column
              prop="createTime"
              label="发布时间"
              width="180"
              :formatter="formatTime"
            />
            <el-table-column label="状态" width="100" align="center">
              <template slot-scope="scope">
                <el-tag
                  :type="scope.row.auditStatus === 1 ? 'success' : scope.row.auditStatus === 0 ? 'warning' : 'danger'"
                >
                  {{ scope.row.auditStatus === 1 ? '已通过' : scope.row.auditStatus === 0 ? '待审核' : '已驳回' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
            :total="total"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="getPostList"
            @current-change="getPostList"
            class="pagination"
          ></el-pagination>
        </el-card>
      </div>
    </div>

    <!-- 底部版权区 -->
    <div class="forum-footer">
      <div class="container">
        <p class="footer-desc">本论坛仅用于校友交流，请勿发布无关内容</p>
      </div>
    </div>
  </div>
</template>

<script>
import { enableList } from "@/api/system/forum_board"
import { topHotList, newList, listForum_post } from "@/api/system/forum_post"
import { formatTime } from '@/utils/utils';
import { mapGetters } from 'vuex'

export default {
  name: 'ForumIndex',
  data() {
    return {
      boardList: [],
      activeBoardId: null,
      currentBoardName: '',
      topHotPostList: [],
      newPostList: [],
      postList: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      statData: {
        postTotal: 1258,
        boardTotal: 16,
        commentTotal: 8965,
        userTotal: 3562
      },
      hotTags: [
        'Vue2', 'SpringBoot', 'MyBatis', 'Java', '前端开发', '后端开发',
        'MySQL', 'Redis', '微服务', 'Element UI', '面试题', '技术分享'
      ],
      activeUsers: [
        { userName: '技术控', avatar: '', postCount: 128 },
        { userName: '前端小能手', avatar: '', postCount: 96 },
        { userName: '后端老司机', avatar: '', postCount: 156 },
        { userName: '架构师之路', avatar: '', postCount: 205 },
        { userName: '编程小白', avatar: '', postCount: 68 }
      ],
      noticeList: [
        { title: '论坛新上线「资源分享」板块，欢迎大家发布', createTime: '2026-02-01' },
        { title: '关于规范论坛发帖内容的公告，禁止广告/灌水', createTime: '2026-01-25' },
        { title: '春节论坛福利活动，发帖赢取技术书籍', createTime: '2026-01-20' }
      ]
    };
  },
  computed: {
    ...mapGetters(['isLogin'])
  },
  created() {
    this.initData();
  },
  methods: {
    initData() {
      this.getBoardList();
      this.getTopHotPostList();
      this.getNewPostList();
      this.getPostList();
    },
    async getBoardList() {
      try {
        const res = await enableList();

        this.boardList = res.data;
      } catch (error) {
        console.error('获取板块列表失败：', error);
      }
    },
    async getTopHotPostList() {
      try {
        const res = await topHotList();

        this.topHotPostList = res.data;
      } catch (error) {
        console.error('获取置顶热点帖子失败：', error);
      }
    },
    async getNewPostList() {
      try {
        const res = await newList({ limit: 5 });

        this.newPostList = res.data;
      } catch (error) {
        console.error('获取最新帖子失败：', error);
      }
    },
    async getPostList() {
      this.loading = true;
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          boardId: this.activeBoardId
        };
        const res = await listForum_post(params);

        this.postList = res.rows;
        this.total = res.total;
        if (this.activeBoardId) {
          const board = this.boardList.find(item => item.id === this.activeBoardId);
          this.currentBoardName = board ? board.boardName : '';
        }
      } catch (error) {
        console.error('获取帖子列表失败：', error);
        this.postList = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },
    handleBoardChange(id) {
      this.activeBoardId = id;
      this.pageNum = 1;
      this.getPostList();
    },
    toPostAdd() {
      this.$router.push({ path: '/system/forum/post/add' });
    },
    toPostDetail(row) {
      const postId = typeof row === 'object' ? row.id : row;
      this.$router.push({ path: `/system/forum/post/detail/${postId}` });
    },
    formatTime
  }
};
</script>


<style scoped lang="scss">
// ==================== 超级艳丽色彩系统 ====================
$bg: linear-gradient(135deg, #FFF5F7 0%, #F0F9FF 35%, #F5F0FF 70%, #FFFBEB 100%);
$banner: linear-gradient(135deg, #FF3366, #9933FF, #33CCFF, #FF9933);
$card1: linear-gradient(135deg, #FFEDED, #FFF9F9);
$card2: linear-gradient(135deg, #EDF7FF, #F6FEFF);
$card3: linear-gradient(135deg, #F0F5FF, #F8F0FF);
$card4: linear-gradient(135deg, #FFF9EB, #FFFBF0);
$c1: #FF3366; $c2: #9933FF; $c3: #33CCFF; $c4: #FF9933; $c5: #33CC66;
$radius: 16px;
$shadow: 0 6px 20px rgba(0,0,0,0.06);
$shadow-hover: 0 12px 32px rgba(0,0,0,0.13);

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

// 页面容器 + 炫彩背景
.forum-container {
  width: 100%;
  min-height: 100vh;
  background: $bg;
  background-attachment: fixed;
  font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
  padding-bottom: 20px;
}

// 容器
.container {
  max-width: 1250px;
  margin: 0 auto;
  padding: 0 20px;
}

// 顶部横幅（四色渐变爆炸）
.forum-banner {
  width: 100%;
  background: $banner;
  background-size: 400% 400%;
  animation: gradient 12s ease infinite;
  padding: 35px 0;
  border-radius: 0 0 30px 30px;
  margin-bottom: 30px;
  color: white;
  box-shadow: 0 8px 24px rgba(255, 51, 102, 0.25);

  @keyframes gradient {
    0% { background-position: 0% 50%; }
    50% { background-position: 100% 50%; }
    100% { background-position: 0% 50%; }
  }

  .banner-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .banner-title {
    font-size: 42px;
    font-weight: 900;
    margin-bottom: 6px;
    text-shadow: 0 2px 10px rgba(0,0,0,0.2);
  }

  .banner-desc {
    font-size: 18px;
    opacity: 0.95;
  }

  .publish-btn {
    background: white !important;
    color: $c1 !important;
    border-radius: 50px;
    font-size: 16px;
    font-weight: bold;
    padding: 12px 32px;
    border: none;
    transition: 0.3s;
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);

    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 8px 20px rgba(255,51,102,0.3);
    }
  }
}

// 主布局
.forum-main {
  display: flex;
  gap: 26px;
}
.main-left { flex: 1; }
.main-right { width: 310px; }

// 卡片样式（多彩渐变卡片 + 悬浮飞升）
.forum-card {
  border-radius: $radius;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: $shadow;
  transition: all 0.35s ease;
  position: relative;
  overflow: hidden;

  &:hover {
    transform: translateY(-6px);
    box-shadow: $shadow-hover;
  }

  .card-title {
    font-size: 19px;
    font-weight: 800;
    margin-bottom: 20px;
    display: flex;
    align-items: center;

    .card-icon {
      margin-right: 10px;
      font-size: 20px;
      font-weight: bold;
    }
  }
}

// 每个卡片不同颜色
.board-nav { background: $card1; .card-icon { color: $c1; } }
.hot-post { background: $card2; .card-icon { color: $c3; } }
.post-list { background: $card3; .card-icon { color: $c2; } }
.post-table { background: #fff; .card-icon { color: #555; } }
.stat-card { background: $card4; .card-icon { color: $c4; } }
.tag-card { background: $card1; .card-icon { color: $c1; } }
.user-card { background: $card2; .card-icon { color: $c3; } }
.notice-card { background: $card3; .card-icon { color: $c2; } }

// 板块导航
.board-item {
  flex: 1;
  min-width: 145px;
  height: 85px;
  border-radius: $radius;
  display: flex;
  align-items: center;
  padding: 0 18px;
  cursor: pointer;
  border: 1px solid #FFD6DF;
  transition: 0.3s;

  &:hover, &.active {
    border-color: $c1;
    background: linear-gradient(90deg, #FFEDED, #FFFBFC);
  }

  .board-icon {
    width: 42px;
    height: 42px;
    border-radius: 50%;
    background: linear-gradient(135deg, $c1, $c4);
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 12px;
    font-size: 20px;
  }
}
.board-list { display: flex; flex-wrap: wrap; gap: 14px; }
.board-name { font-weight: 700; font-size: 15px; margin-bottom: 4px; }
.board-count { font-size: 12px; color: #888; }

// 热点
.hot-index {
  width: 26px;
  height: 26px;
  border-radius: 6px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  font-weight: bold;
}
.hot-post-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #eee;
  cursor: pointer;

  &:hover { padding-left: 6px; }
}

// 帖子
.post-item {
  padding: 18px 0;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;

  &:hover {
    padding-left: 8px;
  }
}
.post-title {
  font-size: 18px;
  font-weight: 700;
  margin: 8px 0 12px;
  color: #222;
  transition: 0.2s;

  &:hover { color: $c2; }
}
.meta-left {
  display: flex;
  align-items: center;
  gap: 10px;
}
.meta-right {
  display: flex;
  align-items: center;
  gap: 12px;
}
.post-desc {
  font-size: 15px;
  color: #666;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

// 表格头部彩色
.table-header {
  background: linear-gradient(90deg, #F0F5FF, #EDF7FF) !important;
  color: #222;
  font-weight: bold;
}

// 统计数字彩色渐变
.stat-num {
  font-size: 28px;
  font-weight: 900;
  background: linear-gradient(135deg, $c1, $c2, $c3, $c4);
  -webkit-background-clip: text;
  color: transparent;
}
.stat-list {
  display: flex;
  flex-wrap: wrap;
  text-align: center;
}
.stat-item { width: 50%; padding: 10px 0; }
.stat-name { font-size: 13px; color: #666; margin-top: 4px; }

// 多彩标签
.colorful-tag {
  border-radius: 30px;
  font-weight: 500;
  cursor: pointer;

  &:nth-child(5n+1) { border-color: $c1; color: $c1; }
  &:nth-child(5n+2) { border-color: $c2; color: $c2; }
  &:nth-child(5n+3) { border-color: $c3; color: $c3; }
  &:nth-child(5n+4) { border-color: $c4; color: $c4; }
  &:nth-child(5n+5) { border-color: $c5; color: $c5; }

  &:hover {
    opacity: 0.8;
  }
}
.tag-list { display: flex; flex-wrap: wrap; gap: 8px; }

// 用户
.user-avatar {
  border: 2px solid #D6E4FF;
}
.user-item {
  display: flex;
  align-items: center;
  padding: 12px 0;
}

// 公告
.notice-content:hover { color: $c2 !important; }

// 分页
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

// 底部
.forum-footer {
  background: linear-gradient(90deg, #222, #444);
  color: white;
  padding: 36px 0;
  border-radius: 30px 30px 0 0;
  text-align: center;
  margin-top: 40px;
  box-shadow: 0 -4px 12px rgba(0,0,0,0.1);

  p {
    margin: 6px 0;
    opacity: 0.85;
  }
}

// 响应式
@media (max-width: 992px) {
  .forum-main { flex-direction: column; }
  .main-right { width: 100%; }
}
</style>
