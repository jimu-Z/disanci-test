<template>
  <div class="forum-container">
    <!-- 帖子信息卡片 -->
    <el-card class="forum-card" shadow="hover" v-loading="loading">
      <div class="mb-4">
        <h2 class="m-0 text-2xl font-bold card-title">{{ postInfo.postTitle }}</h2>
        <div class="forum-post-meta mt-3">
          <span>作者：{{ postInfo.userName }}</span>
          <span>板块：{{ postInfo.boardName }}</span>
          <span>{{ formatTime(postInfo.createTime,'YYYY-MM-DD' ) }}</span>
        </div>
        <!-- 话题标签 -->
       <div class="mt-2" v-if="postInfo.postTags">
  <el-tag
    v-for="(tag, index) in postInfo.postTags.split(',')"
    :key="index"
    type="info"
    size="small"

  >

  <span v-if="tag" > {{ tag }}</span>

  </el-tag>
</div>
        <!-- 状态标签 -->
        <div class="mt-2">
          <el-tag v-if="postInfo.isTop" type="success">置顶</el-tag>
          <el-tag v-if="postInfo.isHot" type="warning">热点</el-tag>
          <el-tag
            :type="postInfo.auditStatus === 1 ? 'success' : postInfo.auditStatus === 0 ? 'warning' : 'danger'"
          >
            {{ postInfo.auditStatus === 1 ? '已审核' : postInfo.auditStatus === 0 ? '待审核' : '已驳回' }}
          </el-tag>
          <el-tag :type="postInfo.isPublic === 1 ? 'primary' : 'default'">
            {{ postInfo.isPublic === 1 ? '公开' : '私密' }}
          </el-tag>
        </div>
      </div>

      <!-- 帖子富文本内容 -->
      <div class="post-content mt-6" v-html="postInfo.postContent"></div>

      <!-- 附件显示 -->
      <div class="mt-6" v-if="attachList.length > 0">

        <div class="forum-attach">
          <div
            class="forum-attach-item"
            v-for="(url, index) in attachList"
            :key="index"
            @click="downloadFile(url)"
          >

            <img :src="parsePicture(url)" class="picture" />
          </div>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="mt-6 flex gap-3">
        <el-button
          type="default"
          icon="el-icon-like"

        >
           <span>浏览：{{ postInfo.viewCount }}</span>

          <span>评论：{{ postInfo.commentCount }}</span>
        </el-button>
        <el-button
          type="default"
          icon="el-icon-like"
          @click="handleLike"
          :disabled="!isLogin"
          :loading="likeLoading"
        >
          点赞 ({{ postInfo.likeCount }})
        </el-button>
        <el-button
          type="default"
          icon="el-icon-star-off"
          @click="handleCollect"
          :disabled="!isLogin"
          :loading="collectLoading"
        >
          收藏 ({{ postInfo.collectCount || 0 }})
        </el-button>
        <el-button
          type="default"
          icon="el-icon-edit"
          @click="toPostEdit"
          v-if="isLogin && postInfo.userId === id"
        >
          修改帖子
        </el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          @click="handleDeletePost"
          v-if="isLogin && (postInfo.userId === id   )"
          v-hasPermi="['system:post:remove']"
        >
          删除帖子
        </el-button>

         <el-button type="default" icon="el-icon-back" @click="toBack()">返回</el-button>
      </div>
    </el-card>

    <!-- 评论区 -->
    <el-card class="forum-card" shadow="hover" mt-4>
      <div class="flex items-center justify-between mb-4">
        <h3 class="m-0">评论区 ({{ commentTotal }})</h3>
      </div>

      <!-- 发布评论 -->
      <el-form
        ref="commentFormRef"
        :model="commentForm"
        label-width="0"
        v-if="isLogin"
        class="mb-6"
      >
        <el-form-item prop="commentContent">
          <el-input
            v-model="commentForm.commentContent"
            placeholder="请输入评论内容（1-200个字）"
            type="textarea"
            rows="3"
            maxlength="200"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item align="right">
          <el-button type="primary" @click="handleAddComment" :loading="commentLoading">
            发布评论
          </el-button>
        </el-form-item>
      </el-form>
      <div class="text-center py-4" v-else>
        <el-button type="primary" @click="toLogin">登录后可发表评论</el-button>
      </div>

      <!-- 评论树形列表（递归组件） -->
      <div v-if="commentTree.length > 0">
        <comment-item
          v-for="comment in commentTree"
          :key="comment.id"
          :comment="comment"
          :is-login="isLogin"
          :user-id="id"
          @like="handleCommentLike"
          @reply="handleReply"
          @delete="handleDeleteComment"
        ></comment-item>
      </div>
      <el-empty v-else description="暂无评论，快来抢沙发吧~"></el-empty>
    </el-card>

    <!-- 回复评论弹窗（Vue2使用弹窗替代内联回复，更适配） -->
    <el-dialog
      title="回复评论"
      :visible.sync="replyDialogVisible"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="replyFormRef"
        :model="replyForm"
        label-width="0"
      >
        <el-form-item prop="commentContent">
          <el-input
            v-model="replyForm.commentContent"
            :placeholder="`回复 @${replyUserName}：`"
            type="textarea"
            rows="3"
            maxlength="200"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="replyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddReply" :loading="replyLoading">
          发布回复
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getForum_post,like,collect,delForum_post} from '@/api/system/forum_post';
import {getTreeByPostId,addForum_comment,commentLike} from '@/api/system/forum_comment';
import { mapGetters } from 'vuex';
//import { mapGetters } from 'vuex';
import {
  formatTime,

  splitAttachUrl,
  getFileName,
  countCommentTotal
} from '@/utils/utils';

// 评论递归组件
import CommentItem from './components/CommentItem.vue';

export default {
  name: 'ForumPostDetail',
  components: {
    CommentItem
  },
  data() {
    return {
      // 帖子ID
      postId: null,
      // 加载状态
      loading: false,
      likeLoading: false,
      collectLoading: false,
      commentLoading: false,
      replyLoading: false,
      // 帖子信息
      postInfo: {},
      attachList: [],
      // 评论相关
      commentTree: [],
      commentTotal: 0,
      commentForm: {
        postId: null,
        parentId: 0,
        commentContent: ''
      },
      // 回复相关
      replyDialogVisible: false,
      replyForm: {
        postId: null,
        parentId: 0,
        commentContent: ''
      },
      replyUserName: ''
    };
  },
  computed: {
    // 从Vuex获取登录状态、用户ID、权限
    ...mapGetters(['isLogin', 'id', 'permissions']),
    // 权限判断方法
    hasPermi: function() {
      return hasPermi;
    }
  },
  created() {
    // 获取路由参数
    this.postId = this.$route.params.id;
    this.commentForm.postId = this.postId;
    this.replyForm.postId = this.postId;
    // 初始化数据
    this.getPostDetail();
  },
  methods: {
      parsePicture(pictureStr) {
      if (!pictureStr) return"";
      // 拆分逗号分隔的URL，过滤空字符串（避免末尾逗号导致的空元素）
      return    process.env.VUE_APP_BASE_API + pictureStr  ;
    },

    // 获取帖子详情
    async getPostDetail() {
      this.loading = true;
      try {
        const res = await getForum_post(this.postId);
        this.postInfo = res.data;
        // 拆分附件地址为数组
        this.attachList = splitAttachUrl(this.postInfo.postAttach);
        // 获取评论树形列表
        this.getCommentTree();
      } catch (error) {
        console.error('获取帖子详情失败：', error);
        this.$message.error('帖子不存在或已被删除');
        this.$router.back();
      } finally {
        this.loading = false;
      }
    },
    // 获取评论树形列表
    async getCommentTree() {
      try {
        const res = await  getTreeByPostId(this.postId);
        this.commentTree = res.data;
        // 统计评论总数
        this.commentTotal = countCommentTotal(this.commentTree);
      } catch (error) {
        console.error('获取评论列表失败：', error);
        this.commentTree = [];
        this.commentTotal = 0;
      }
    },
    // 附件下载
    downloadFile(url) {
      window.open(url, '_blank');
    },
    // 帖子点赞
    async handleLike() {
      this.likeLoading = true;
      try {
        await  like(this.postId);
        this.$message.success('操作成功');
        this.getPostDetail(); // 刷新帖子信息
      } catch (error) {
        console.error('点赞失败：', error);
      } finally {
        this.likeLoading = false;
      }
    },
    // 帖子收藏
    async handleCollect() {
      this.collectLoading = true;
      try {
        await  collect(this.postId);
        this.$message.success('操作成功');
        this.getPostDetail(); // 刷新帖子信息
      } catch (error) {
        console.error('收藏失败：', error);
      } finally {
        this.collectLoading = false;
      }
    },
    // 跳转到修改帖子页
    toPostEdit() {
      this.$router.push({ path: `/system/forum/post/edit/${this.postId}` });
    },
    // 删除帖子
    async handleDeletePost() {
      try {
        await this.$confirm(
          '确定要删除该帖子吗？删除后将无法恢复',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        );
        await delForum_post(this.postId);
        this.$message.success('帖子删除成功');
        this.$router.push({ path: '/system/forum' });
      } catch (error) {
        this.$message.info('已取消删除');
      }
    },
    // 发布评论
    async handleAddComment() {
      if (!this.commentForm.commentContent.trim()) {
        this.$message.warning('请输入评论内容');
        return;
      }
      this.commentLoading = true;
      try {
        await  addForum_comment(this.commentForm);
        this.$message.success('评论发布成功');
        this.commentForm.commentContent = '';
        this.getCommentTree(); // 刷新评论
        this.getPostDetail(); // 刷新帖子评论数
      } catch (error) {
        console.error('发布评论失败：', error);
      } finally {
        this.commentLoading = false;
      }
    },
    // 回复评论（打开弹窗）
    handleReply(commentId, userName) {
      this.replyForm.parentId = commentId;
      this.replyUserName = userName;
      this.replyForm.commentContent = '';
      this.replyDialogVisible = true;
    },
    // 发布回复
    async handleAddReply() {
      if (!this.replyForm.commentContent.trim()) {
        this.$message.warning('请输入回复内容');
        return;
      }
      this.replyLoading = true;
      try {
        await addForum_comment(this.replyForm);
        this.$message.success('回复发布成功');
        this.replyDialogVisible = false;
        this.getCommentTree(); // 刷新评论
        this.getPostDetail(); // 刷新帖子评论数
      } catch (error) {
        console.error('发布回复失败：', error);
      } finally {
        this.replyLoading = false;
      }
    },
    // 评论点赞
    async handleCommentLike(commentId) {
      try {
        await  commentLike(commentId);
        this.$message.success('点赞成功');
        this.getCommentTree(); // 刷新评论
      } catch (error) {
        console.error('评论点赞失败：', error);
      }
    },
    // 删除评论
    async handleDeleteComment(commentId) {
      try {
        await this.$confirm(
          '确定要删除该评论吗？删除后子评论也会被删除',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        );
        await delForum_comment(commentId);
        this.$message.success('评论删除成功');
        this.getCommentTree(); // 刷新评论
        this.getPostDetail(); // 刷新帖子评论数
      } catch (error) {
        this.$message.info('已取消删除');
      }
    },
    // 跳转到登录页
    toLogin() {
      this.$router.push({
        path: '/login',
        query: { redirect: this.$route.fullPath }
      });
    },

    toBack() {
      this.$router.go(-1);
    },
    // 工具函数复用
    formatTime,
    getFileName
  }
}
</script>

<style scoped lang="scss">
// 全局样式变量（统一管理色彩、间距）
$primary-color: #165DFF; // 主色调-科技蓝
$secondary-color: #667085; // 次要色调-深灰
$light-color: #C9CDD4; // 浅灰色
$bg-color: #F5F7FA; // 页面背景色
$card-bg: linear-gradient(145deg, #e6e6e6, #ffffff); // 卡片背景色
$hover-color: #E8F3FF; // 悬浮浅蓝
$shadow: 8px 8px 16px #d9d9d9,    -8px -8px 16px #ffffff; // 基础阴影
$box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
$shadow-hover: 0 4px 16px 0 rgba(0, 0, 0, 0.08); // 悬浮阴影
$radius: 10px; // 统一圆角
$container-width: 1200px; // 容器最大宽度
$spacing: 20px; // 统一模块间距
 // 卡片通用样式
 .forum-container{
  width: 90%;

 }
.forum-card {
  background: $card-bg;
  border-radius: $radius;
  box-shadow: $shadow;
  padding: 20px;

  margin-bottom: $spacing;
  transition: all 0.3s ease;
  span{
    margin-right: 8px;
  }

  &:hover {
    box-shadow: $shadow-hover;
  }

  .card-title {
    font-size: 20px;

    font-weight: 800;
    color: #333;
    text-align: center;


    align-items: center;

    .card-icon {
      color: $primary-color;
      margin-right: 8px;
      font-size: 18px;
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
//论坛标题左浮动
.forum-title{
 float: left;
 margin-right: 20px;


}




.forum-post-meta  {
    padding: 20px  ;
    border-radius: 4px;
    border-bottom: 1px solid #ffff;
    background-color: #fff;
    cursor: pointer;
    transition: all 0.3s ease;
    &:hover {
      padding-left: 8px;
      background: $bg-color;
      border-radius: 4px;
    }
  }
.picture {
  width:200px;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
  margin: 20px;
  display: flex;
}
.post-content {
  line-height: 1.8;
  font-size: 14px;
    background: #ffffff;
  min-height: 100px;
  padding: 20px;
  border-radius: 4px;
  box-shadow: $box-shadow;



}
::v-deep .el-avatar {
  width: 40px;
  height: 40px;
}
</style>
