<template>
  <div class="forum-container">
    <!-- 板块导航卡片 -->
    <el-card class="forum-card" shadow="hover">
      <div class="flex items-center justify-between mb-4">
        <h3 class="m-0">论坛板块</h3>
      </div>
      <el-scrollbar wrap-class="flex gap-3">
        <el-button
          v-for="board in boardList"
          :key="board.id"
          :type="activeBoardId === board.id ? 'primary' : 'default'"
          @click="handleBoardChange(board.id)"
        >
          {{ board.boardName }}
        </el-button>
      </el-scrollbar>
    </el-card>

    <!-- 置顶热点帖子 -->
    <el-card class="forum-card" shadow="hover" v-if="topHotPostList.length > 0">
      <h3 class="m-0 mb-4">置顶热点</h3>
      <el-timeline>
        <el-timeline-item
          v-for="post in topHotPostList"
          :key="post.id"
          :timestamp="post.createTime"
          timestamp-format="YYYY-MM-DD HH:mm"
        >
          <div @click="toPostDetail(post.id)" class="forum-post-title">{{ post.postTitle }}</div>
          <div class="forum-post-meta">
            <span>作者：{{ post.userName }}</span>
            <span>板块：{{ post.boardName }}</span>
            <span>浏览：{{ post.viewCount }}</span>
            <span>点赞：{{ post.likeCount }}</span>
            <span>评论：{{ post.commentCount }}</span>
          </div>
          <div class="text-ellipsis-2 text-gray-600" v-html="post.postContent"></div>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <!-- 最新发布帖子 -->
    <el-card class="forum-card" shadow="hover">
      <div class="flex items-center justify-between mb-4">
        <h3 class="m-0">最新发布</h3>
        <el-button type="primary" icon="el-icon-plus" @click="toPostAdd" v-if="isLogin">
          发布帖子
        </el-button>
      </div>
      <el-list v-if="newPostList.length > 0" border>
        <el-list-item
          v-for="post in newPostList"
          :key="post.id"
          @click="toPostDetail(post.id)"
          class="cursor-pointer hover:bg-gray-50"
        >
          <template slot="header">
            <div class="flex items-center justify-between">
              <h4 class="m-0 forum-post-title">{{ post.postTitle }}</h4>
              <el-tag type="success" v-if="post.isTop">置顶</el-tag>
              <el-tag type="warning" v-if="post.isHot">热点</el-tag>
            </div>
          </template>
          <div class="forum-post-meta">
            <span>作者：{{ post.userName }}</span>
            <span>板块：{{ post.boardName }}</span>
            <span>{{ formatTime(post.createTime) }}</span>
          </div>
          <div class="text-ellipsis-2 text-gray-600" v-html="post.postContent"></div>
          <template slot="extra">
            <div class="flex gap-4 text-sm text-gray-500">
              <span><i class="el-icon-view"></i> {{ post.viewCount }}</span>
              <span><i class="el-icon-like"></i> {{ post.likeCount }}</span>
              <span><i class="el-icon-chat-dot-round"></i> {{ post.commentCount }}</span>
            </div>
          </template>
        </el-list-item>
      </el-list>
      <el-empty v-else description="暂无最新帖子"></el-empty>
    </el-card>

    <!-- 板块帖子分页列表 -->
    <el-card class="forum-card" shadow="hover">
      <h3 class="m-0 mb-4">
        板块帖子
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
        class="mt-4 flex justify-center"
      ></el-pagination>
    </el-card>
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
      currentBoardName: '',
      // 帖子相关
      topHotPostList: [],
      newPostList: [],
      postList: [],
      // 分页参数
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0
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
 @import "../../../assets/css/forum.css";
// 适配Vue2深度选择器
::v-deep .el-timeline-item__content {
  padding: 0;
}
</style>
