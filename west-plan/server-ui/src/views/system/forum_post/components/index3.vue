<template>
  <div class="forum-home-page">
    <!-- 顶部横幅区 -->
    <div class="forum-banner">
      <div class="banner-content">
        <h1 class="banner-title">云栖技术论坛</h1>
        <p class="banner-desc">分享技术干货 · 交流开发心得 · 结交行业同行</p>
        <el-button
          type="primary"
          size="large"
          icon="el-icon-plus"
          @click="toPostAdd"
          v-if="isLogin"
          class="publish-btn"
        >
          发布新帖
        </el-button>
        <el-button
          type="default"
          size="large"
          @click="toLogin"
          v-else
          class="login-btn"
        >
          登录/注册
        </el-button>
      </div>
    </div>

    <!-- 核心内容区 -->
    <div class="forum-main container">
      <!-- 左侧主内容 -->
      <div class="main-left">
        <!-- 板块导航 -->
        <div class="forum-card board-nav">
          <h3 class="card-title">论坛板块</h3>
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
        <div class="forum-card hot-post" v-if="topHotPostList.length > 0">
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
              <span class="hot-index" :style="{background: index < 3 ? '#165DFF' : '#667085'}">{{ index + 1 }}</span>
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
            <span class="post-count">(共{{ total }}篇)</span>
          </h3>
          <div class="post-item" v-for="post in postList" :key="post.id" @click="toPostDetail(post.id)">
            <!-- 帖子标签 -->
            <div class="post-tags">
              <el-tag type="success" size="mini" v-if="post.isTop">置顶</el-tag>
              <el-tag type="warning" size="mini" v-if="post.isHot">热点</el-tag>
              <el-tag type="info" size="mini" v-if="post.auditStatus === 1">{{ post.boardName }}</el-tag>
            </div>
            <!-- 帖子标题 -->
            <h4 class="post-title">{{ post.postTitle }}</h4>
            <!-- 帖子摘要 -->
            <p class="post-desc" v-html="post.postContent"></p>
            <!-- 帖子元信息 -->
            <div class="post-meta">
              <div class="meta-left">
                <span class="author">{{ post.userName }}</span>
                <span class="time">{{ formatTime(post.createTime) }}</span>
              </div>
              <div class="meta-right">
                <span><i class="el-icon-view"></i> {{ post.viewCount }}</span>
                <span><i class="el-icon-like"></i> {{ post.likeCount }}</span>
                <span><i class="el-icon-chat-dot-round"></i> {{ post.commentCount }}</span>
              </div>
            </div>
          </div>

          <!-- 空数据占位 -->
          <div class="empty-post" v-if="postList.length === 0 && !loading">
            <el-empty description="该板块暂无帖子，快来发布第一篇吧~"></el-empty>
          </div>


        </div>
      </div>

      <!-- 右侧侧边栏 -->
      <div class="main-right">
        <!-- 论坛统计 -->
        <div class="forum-card stat-card">
          <h3 class="card-title">论坛统计</h3>
          <div class="stat-list">
            <div class="stat-item">
              <p class="stat-num">{{ statData.postTotal || 0 }}</p>
              <p class="stat-name">总帖子</p>
            </div>
            <div class="stat-item">
              <p class="stat-num">{{ statData.boardTotal || 0 }}</p>
              <p class="stat-name">总板块</p>
            </div>
            <div class="stat-item">
              <p class="stat-num">{{ statData.commentTotal || 0 }}</p>
              <p class="stat-name">总评论</p>
            </div>
            <div class="stat-item">
              <p class="stat-num">{{ statData.userTotal || 0 }}</p>
              <p class="stat-name">注册用户</p>
            </div>
          </div>
        </div>

        <!-- 热门标签 -->
        <div class="forum-card tag-card">
          <h3 class="card-title">热门标签</h3>
          <div class="tag-list">
            <el-tag
              v-for="(tag, index) in hotTags"
              :key="index"
              type="info"
              effect="plain"
              @click="handleTagSearch(tag)"
            >
              {{ tag }}
            </el-tag>
          </div>
        </div>

        <!-- 活跃用户 -->
        <div class="forum-card user-card">
          <h3 class="card-title">活跃用户</h3>
          <div class="user-list">
            <div class="user-item" v-for="(user, index) in activeUsers" :key="index">
              <el-avatar :src="user.avatar" class="user-avatar"></el-avatar>
              <div class="user-info">
                <p class="user-name">{{ user.userName }}</p>
                <p class="user-post">{{ user.postCount }}篇帖子</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 公告栏 -->
        <div class="forum-card notice-card">
          <h3 class="card-title">
            <i class="el-icon-bell card-icon"></i>
            论坛公告
          </h3>
          <div class="notice-list">
            <div class="notice-item" v-for="(notice, index) in noticeList" :key="index">
              <p class="notice-content" @click="handleNoticeClick(notice)">{{ notice.title }}</p>
              <p class="notice-time">{{ formatTime(notice.createTime, 'MM-DD') }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部版权区 -->
    <div class="forum-footer">
      <div class="container">
        <p>© 2026 云栖技术论坛 版权所有 | 技术支持：Vue2 + Element UI + SpringBoot</p>
        <p class="footer-desc">本论坛仅用于技术交流，请勿发布无关内容</p>
      </div>
    </div>
  </div>
</template>

<script>
 import {  enableList } from "@/api/system/forum_board"
import { topHotList,newList,listForum_post} from "@/api/system/forum_post"
import { formatTime } from '@/utils/utils';

export default {
  name: 'ForumIndex',
  data() {
    return {
      // 板块相关
      boardList: [],
      activeBoardId: null,
      currentBoardName: '全部',
      // 帖子相关
      topHotPostList: [],
      postList: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      // 侧边栏统计/标签/用户/公告（模拟数据，可替换为真实接口）
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
    // 从Vuex获取登录状态
    //...mapGetters(['isLogin'])
     isLogin() { return this.$store.getters.isLogin; },
    userId() { return this.$store.getters.userId; },
    userName() { return this.$store.getters.userName; }
  },
  created() {
    this.initData();
  },
  methods: {
    // 初始化数据
    initData() {
      this.getBoardList();
      this.getTopHotPostList();
      this.getNewPostList();
      this.getPostList();
    },
    // 获取启用的板块列表
    async getBoardList() {
      try {
        const res = await  enableList();
        this.boardList = res.data;
      } catch (error) {
        console.error('获取板块列表失败：', error);
      }
    },
    // 获取置顶热点帖子
    async getTopHotPostList() {
      try {
        const res = await  topHotList();
        this.topHotPostList = res.data;
      } catch (error) {
        console.error('获取置顶热点帖子失败：', error);
      }
    },
    // 获取最新帖子（默认5条）
    async getNewPostList() {
      try {
        const res = await  newList({ limit: 5 });
        this.newPostList = res.data;
      } catch (error) {
        console.error('获取最新帖子失败：', error);
      }
    },
    // 获取板块帖子分页列表
    async getPostList() {
      this.loading = true;
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          boardId: this.activeBoardId
        };
        const res = await listForum_post(params);
        this.postList = res.list;
       // console.log(res);
        this.total = res.total;
        // 更新当前板块名称
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
    // 板块切换
    handleBoardChange(id) {
      this.activeBoardId = id;
      this.pageNum = 1; // 重置页码
      this.getPostList();
    },
    // 跳转到帖子发布页
    toPostAdd() {
      this.$router.push({ path: '/forum_post/add' });
    },
    // 跳转到帖子详情页
    toPostDetail(row) {
      const postId = typeof row === 'object' ? row.id : row;
      this.$router.push({ path: `/forum_post/detail/${postId}` });
    },
    // 时间格式化（复用工具函数）
    formatTime
  }
};
</script>

<style scoped lang="scss">
// 全局样式变量（统一管理色彩、间距）
$primary-color: #165DFF; // 主色调-科技蓝
$secondary-color: #667085; // 次要色调-深灰
$light-color: #C9CDD4; // 浅灰色
$bg-color: #F5F7FA; // 页面背景色
$card-bg: #FFFFFF; // 卡片背景色
$hover-color: #E8F3FF; // 悬浮浅蓝
$shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.04); // 基础阴影
$shadow-hover: 0 4px 16px 0 rgba(0, 0, 0, 0.08); // 悬浮阴影
$radius: 8px; // 统一圆角
$container-width: 1200px; // 容器最大宽度
$spacing: 20px; // 统一模块间距

// 页面全局样式
.forum-home-page {
  width: 100%;
  min-height: 100vh;
  background-color: $bg-color;
  font-family: "Microsoft YaHei", "PingFang SC", "Hiragino Sans GB", sans-serif;
  color: #333;
}

// 容器居中
.container {
  max-width: $container-width;
  margin: 0 auto;
  padding: 0 16px;
}

// 卡片通用样式
.forum-card {
  background: $card-bg;
  border-radius: $radius;
  box-shadow: $shadow;
  padding: 20px;
  margin-bottom: $spacing;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: $shadow-hover;
  }

  .card-title {
    font-size: 16px;
    font-weight: 600;
    color: #333;
    margin: 0 0 16px 0;
    display: flex;
    align-items: center;

    .card-icon {
      color: $primary-color;
      margin-right: 8px;
      font-size: 18px;
    }
  }
}

// 顶部横幅
.forum-banner {
  width: 100%;
  height: 280px;
  background: linear-gradient(135deg, $primary-color 0%, #4080FF 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  margin-bottom: $spacing;
  position: relative;
  overflow: hidden;

  &::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='100' height='100' viewBox='0 0 100 100'%3E%3Cpath d='M11 18c3.866 0 7-3.134 7-7s-3.134-7-7-7-7 3.134-7 7 3.134 7 7 7zm48 25c3.866 0 7-3.134 7-7s-3.134-7-7-7-7 3.134-7 7 3.134 7 7 7zm-43-7c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zm63 31c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zM34 90c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zm56-76c1.657 0 3-1.343 3-3s-1.343-3-3-3-3 1.343-3 3 1.343 3 3 3zM12 86c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm28-65c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm23-11c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm-6 60c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm29 22c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zM32 63c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm57-13c2.76 0 5-2.24 5-5s-2.24-5-5-5-5 2.24-5 5 2.24 5 5 5zm-9-21c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM60 91c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM35 41c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2zM12 60c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2z' fill='%23ffffff' fill-opacity='0.1' fill-rule='evenodd'/%3E%3C/svg%3E");
    opacity: 0.2;
  }

  .banner-content {
    text-align: center;
    z-index: 2;
  }

  .banner-title {
    font-size: 40px;
    font-weight: 700;
    margin: 0 0 16px 0;
    letter-spacing: 2px;
  }

  .banner-desc {
    font-size: 18px;
    margin: 0 0 32px 0;
    opacity: 0.9;
  }

  .publish-btn {
    background: #fff;
    color: $primary-color;
    border: none;
    padding: 10px 30px;
    font-size: 16px;
    &:hover {
      background: #f8f9fa;
      color: $primary-color;
    }
  }

  .login-btn {
    background: transparent;
    color: #fff;
    border: 1px solid #fff;
    padding: 10px 30px;
    font-size: 16px;
    margin-left: 16px;
    &:hover {
      background: rgba(255, 255, 255, 0.1);
      border-color: #fff;
      color: #fff;
    }
  }
}

// 核心内容区
.forum-main {
  display: flex;
  gap: $spacing;
  padding-bottom: 40px;

  .main-left {
    flex: 1; // 左侧占满剩余宽度
  }

  .main-right {
    width: 280px; // 右侧固定宽度，保证布局稳定
  }
}

// 板块导航
.board-nav {
  .board-list {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
  }

  .board-item {
    width: calc(33.333% - 8px);
    height: 80px;
    border: 1px solid #e5e7eb;
    border-radius: $radius;
    display: flex;
    align-items: center;
    padding: 0 16px;
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover, &.active {
      border-color: $primary-color;
      background: $hover-color;
    }

    .board-icon {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background: rgba(22, 93, 255, 0.1);
      color: $primary-color;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 12px;
      font-size: 20px;
    }

    .board-info {
      .board-name {
        font-size: 14px;
        font-weight: 600;
        color: #333;
        margin: 0 0 4px 0;
      }

      .board-count {
        font-size: 12px;
        color: $secondary-color;
        margin: 0;
      }
    }
  }
}

// 置顶热点帖子
.hot-post {
  .hot-post-list {
    .hot-post-item {
      display: flex;
      align-items: center;
      padding: 12px 0;
      border-bottom: 1px solid #f0f0f0;
      cursor: pointer;
      transition: all 0.2s ease;

      &:last-child {
        border-bottom: none;
      }

      &:hover {
        background: $bg-color;
        padding-left: 8px;
        border-radius: 4px;
      }

      .hot-index {
        width: 24px;
        height: 24px;
        border-radius: 4px;
        background: $secondary-color;
        color: #fff;
        font-size: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 12px;
        flex-shrink: 0;
      }

      .hot-post-info {
        flex: 1;

        .hot-post-title {
          font-size: 14px;
          color: #333;
          margin: 0 0 4px 0;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .hot-post-meta {
          font-size: 12px;
          color: $secondary-color;
          display: flex;
          gap: 16px;

          i {
            font-size: 10px;
            margin-right: 2px;
          }
        }
      }
    }
  }
}

// 帖子列表
.post-list {
  .post-count {
    font-size: 12px;
    color: $secondary-color;
    font-weight: 400;
    margin-left: 8px;
  }

  .post-item {
    padding: 20px 0;
    border-bottom: 1px solid #f0f0f0;
    cursor: pointer;
    transition: all 0.3s ease;

    &:last-child {
      border-bottom: none;
    }

    &:hover {
      padding-left: 8px;
      background: $bg-color;
      border-radius: 4px;
    }

    .post-tags {
      margin-bottom: 8px;

      .el-tag {
        margin-right: 4px;
      }
    }

    .post-title {
      font-size: 16px;
      font-weight: 600;
      color: #333;
      margin: 0 0 8px 0;
      transition: color 0.3s ease;

      &:hover {
        color: $primary-color;
      }
    }

    .post-desc {
      font-size: 14px;
      color: $secondary-color;
      line-height: 1.6;
      margin: 0 0 12px 0;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .post-meta {
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      color: $light-color;

      .meta-left {
        display: flex;
        gap: 16px;

        .author {
          color: $primary-color;
          font-weight: 500;
        }
      }

      .meta-right {
        display: flex;
        gap: 16px;

        i {
          margin-right: 4px;
        }
      }
    }
  }

  .empty-post {
    padding: 40px 0;
    text-align: center;
  }

  .pagination {
    margin-top: 20px;
    text-align: center;
  }
}

// 右侧统计卡片
.stat-card {
  .stat-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    text-align: center;

    .stat-item {
      width: 50%;
      padding: 8px 0;

      .stat-num {
        font-size: 24px;
        font-weight: 700;
        color: $primary-color;
        margin: 0 0 4px 0;
      }

      .stat-name {
        font-size: 12px;
        color: $secondary-color;
        margin: 0;
      }
    }
  }
}

// 热门标签
.tag-card {
  .tag-list {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;

    .el-tag {
      cursor: pointer;
      &:hover {
        color: $primary-color;
        border-color: $primary-color;
      }
    }
  }
}

// 活跃用户
.user-card {
  .user-list {
    .user-item {
      display: flex;
      align-items: center;
      padding: 12px 0;
      border-bottom: 1px solid #f5f5f5;

      &:last-child {
        border-bottom: none;
      }

      .user-avatar {
        width: 40px;
        height: 40px;
        margin-right: 12px;
      }

      .user-info {
        .user-name {
          font-size: 14px;
          color: #333;
          margin: 0 0 4px 0;
          font-weight: 500;
        }

        .user-post {
          font-size: 12px;
          color: $secondary-color;
          margin: 0;
        }
      }
    }
  }
}

// 公告栏
.notice-card {
  .notice-list {
    .notice-item {
      padding: 10px 0;
      border-bottom: 1px solid #f5f5f5;
      cursor: pointer;

      &:last-child {
        border-bottom: none;
      }

      &:hover .notice-content {
        color: $primary-color;
      }

      .notice-content {
        font-size: 14px;
        color: #333;
        margin: 0 0 4px 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        transition: color 0.3s ease;
      }

      .notice-time {
        font-size: 12px;
        color: $light-color;
        margin: 0;
        text-align: right;
      }
    }
  }
}

// 底部版权
.forum-footer {
  width: 100%;
  background: #333;
  color: #ccc;
  padding: 30px 0;
  margin-top: 40px;

  .container {
    text-align: center;

    p {
      margin: 0 0 8px 0;
      font-size: 14px;
    }

    .footer-desc {
      font-size: 12px;
      color: #999;
      margin: 0;
    }
  }
}

// 响应式适配（平板端）
@media (max-width: 992px) {
  .forum-main {
    flex-direction: column;
  }

  .main-right {
    width: 100%;
  }

  .board-nav .board-item {
    width: calc(50% - 6px);
  }

  .forum-banner .banner-title {
    font-size: 32px;
  }
}
</style
