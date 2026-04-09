<template>
  <div class="alumni-feeling">
    <div class="feeling-header">
      <h2 class="feeling-title">
        <i class="el-icon-chat-line-round"></i> 校友感悟
      </h2>
      <p class="feeling-desc">分享西部计划的点滴故事，传递公益的温暖力量</p>
    </div>

    <div class="feeling-list">
      <div class="feeling-card" v-for="(item) in feelingList" :key="item.id">
        <div class="card-header">
          <el-avatar :src="item.avatar" class="author-avatar"></el-avatar>
          <div class="author-info">
            <h3 class="author-name">{{ item.author }}</h3>
            <p class="author-meta">
              <span class="time">{{ item.createTime }}</span>
            </p>
          </div>
        </div>

        <div class="card-content">
          <div class="content-text"
            :class="{ 'content-collapse': item.isCollapse && item.content && item.content.length > 200 }">
            {{ item.content }}
          </div>
          <div class="content-toggle" v-if="item.content && item.content.length > 200" @click="toggleContent(item)">
            {{ item.isCollapse ? '查看全文' : '收起' }}
            <i :class="item.isCollapse ? 'el-icon-arrow-down' : 'el-icon-arrow-up'"></i>
          </div>

          <div class="content-images" v-if="item.picture && item.picture.length > 0">
            <img v-for="(img, imgIndex) in item.picture" :key="imgIndex" :src="img" :alt="`感悟配图${imgIndex + 1}`"
              class="content-img" @click="previewImage(item.picture, imgIndex)">
          </div>
        </div>

        <div class="card-action">
          <div class="action-item like-btn" @click="toggleLike(item)">
            <i class="el-icon-heart" :class="{ 'liked': item.isLiked }"></i>
            <span class="count">{{ item.likeCount }}</span>
          </div>
          <div class="action-item comment-btn" @click="toggleComment(item)">
            <i class="el-icon-chat-dot-round"></i>
            <span class="count">{{ item.commentCount }}</span>
          </div>
        </div>

        <div class="comment-section" v-show="item.showComment">
          <div class="comment-input">
            <el-avatar :src="userAvatar" class="comment-avatar"></el-avatar>
            <el-input v-model="item.commentText" placeholder="写下你的评论..." class="input-main"
              @keyup.enter.native="submitComment(item)"></el-input>
            <el-button type="primary" size="mini" @click="submitComment(item)">
              发布
            </el-button>
          </div>

          <div class="comment-list" v-if="item.comments && item.comments.length">
            <div class="comment-item" v-for="(comment, cIndex) in item.comments" :key="cIndex">
              <el-avatar :src="comment.author.avatar" class="comment-item-avatar"></el-avatar>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-author">{{ comment.author.name }}</span>
                  <span class="comment-time">{{ comment.createTime }}</span>
                </div>
                <div class="comment-text">{{ comment.content }}</div>

                <div class="comment-reply">
                  <span class="reply-btn" @click="toggleReply(item, comment)">回复</span>
                  <div class="reply-input" v-show="comment.showReply">
                    <el-input v-model="comment.replyText" placeholder="回复TA..." size="mini"
                      @keyup.enter.native="submitReply(item, comment)"></el-input>
                    <el-button size="mini" type="primary" @click="submitReply(item, comment)">
                      发送
                    </el-button>
                  </div>
                </div>

                <div class="reply-list" v-if="comment.replies && comment.replies.length">
                  <div class="reply-item" v-for="(reply, rIndex) in reply.list" :key="rIndex">
                    <span class="reply-author">{{ reply.author.name }}：</span>
                    <span class="reply-text">{{ reply.content }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-image-viewer v-if="showImageViewer" :url-list="previewImages" :on-close="closeImageViewer"></el-image-viewer>
  </div>
</template>

<script>
import { feelingWithUserList } from '@/api/system/alumni_feeling';
export default {
  name: 'AlumniFeeling',
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        category: null,
        title: null,
        content: null,
        isPublic: true,
      },
      feelingList: [],
      showImageViewer: false,
      previewImages: []
    };
  },
  computed: {
    isLogin() { return this.$store.getters.isLogin; },
    userId() { return this.$store.getters.id; },
    userAvatar() { return   this.$store.getters.avatar; },
    userName() { return this.$store.getters.nickName; }
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {

      this.loading = true
      feelingWithUserList(this.queryParams).then(response => {
        this.feelingList = response.rows.map(item => {
          // 统一处理所有图片路径 + 初始化状态
          return {
            ...item,
            avatar: item.avatar ? process.env.VUE_APP_BASE_API + item.avatar : '',
            // 配图统一加前缀
            picture: item.picture ? item.picture.split(',').map(p => process.env.VUE_APP_BASE_API + p) : [],
            isCollapse: true,
            showComment: false,
            commentText: '',
            isLiked: false,
            comments: item.comments ? item.comments.map(c => ({
              ...c,
              author: {
                ...c.author,
                avatar: c.author?.avatar ? process.env.VUE_APP_BASE_API + c.author.avatar : ''
              },
              showReply: false,
              replyText: '',
              replies: c.replies || []
            })) : []
          }
        })
        this.total = response.total
        this.loading = false
      })
    },
    toggleContent(item) {
      item.isCollapse = !item.isCollapse;
    },
    toggleLike(item) {
      if (item.isLiked) {
        item.likeCount--;
        item.isLiked = false;
        this.$message.info('已取消点赞');
      } else {
        item.likeCount++;
        item.isLiked = true;
        this.$message.success('点赞成功');
      }
    },
    toggleComment(item) {
      item.showComment = !item.showComment;
    },
    submitComment(item) {
      if (!item.commentText.trim()) {
        this.$message.warning('评论内容不能为空');
        return;
      }
      const newComment = {
        author: {
          avatar: this.userAvatar,
          name: this.userName
        },
        createTime: new Date().toLocaleString('zh-CN', {
          year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'
        }).replace(/\//g, '-'),
        content: item.commentText.trim(),
        showReply: false,
        replyText: '',
        replies: []
      };
      item.comments.unshift(newComment);
      item.commentCount++;
      item.commentText = '';
      this.$message.success('评论发布成功');
    },
    toggleReply(item, comment) {
      comment.showReply = !comment.showReply;
    },
    submitReply(item, comment) {
      if (!comment.replyText.trim()) {
        this.$message.warning('回复内容不能为空');
        return;
      }
      const newReply = {
        author: { name: this.userName },
        content: comment.replyText.trim()
      };
      comment.replies.push(newReply);
      comment.replyText = '';
      comment.showReply = false;
      this.$message.success('回复成功');
    },
    previewImage(images, index) {
      this.previewImages = images;
      this.showImageViewer = true;
    },
    closeImageViewer() {
      this.showImageViewer = false;
      this.previewImages = [];
    }
  }
};
</script>

<style scoped>
.alumni-feeling,
.alumni-feeling * {
  box-sizing: border-box;
}

.alumni-feeling {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #f8f9fa;
  min-height: 100vh;
}

.feeling-header {
  text-align: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 2px solid #E63946;
}

.feeling-title {
  font-size: 28px;
  color: #E63946;
  margin: 0 0 10px 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.feeling-title i {
  font-size: 32px;
  margin-right: 10px;
}

.feeling-desc {
  font-size: 16px;
  color: #667085;
  margin: 0;
}

.feeling-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));
  gap: 20px;
}

.feeling-card {
  background: #fff;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.feeling-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  transform: translateY(-5px);
}

.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.author-avatar {
  width: 50px;
  height: 50px;
  margin-right: 15px;
}

.author-info {
  flex: 1;
}

.author-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 5px 0;
}

.author-meta {
  font-size: 14px;
  color: #667085;
  margin: 0;
  display: flex;
  gap: 15px;
}

.card-content {
  margin-bottom: 20px;
}

.content-text {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  margin-bottom: 10px;
}

.content-collapse {
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.content-toggle {
  font-size: 14px;
  color: #457B9D;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 5px;
}

.content-toggle:hover {
  color: #E63946;
}

.content-images {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 15px;
}

.content-img {
  width: calc(33.33% - 7px);
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.content-img:hover {
  transform: scale(1.05);
}

.card-action {
  display: flex;
  gap: 20px;
  padding: 10px 0;
  border-top: 1px solid #f0f0f0;
  margin-bottom: 15px;
}

.action-item {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
  font-size: 16px;
  color: #667085;
  transition: all 0.2s ease;
}

.action-item:hover {
  color: #E63946;
}

.count {
  font-size: 14px;
}

.comment-section {
  border-top: 1px solid #f0f0f0;
  padding-top: 15px;
}

.comment-input {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.comment-avatar {
  width: 40px;
  height: 40px;
}

.input-main {
  flex: 1;
}

.comment-list {
  margin-left: 10px;
}

.comment-item {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.comment-item-avatar {
  width: 36px;
  height: 36px;
  flex-shrink: 0;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 5px;
}

.comment-author {
  font-size: 14px;
  font-weight: 600;
  color: #333;
}

.comment-time {
  font-size: 12px;
  color: #999;
}

.comment-text {
  font-size: 14px;
  line-height: 1.6;
  color: #333;
  margin-bottom: 8px;
}

.comment-reply {
  display: flex;
  align-items: center;
  gap: 10px;
}

.reply-btn {
  font-size: 12px;
  color: #457B9D;
  cursor: pointer;
}

.reply-btn:hover {
  color: #E63946;
}

.reply-input {
  display: flex;
  gap: 10px;
  margin-top: 10px;
  width: 100%;
}

.reply-list {
  margin-top: 10px;
  padding-left: 15px;
  border-left: 2px solid #f0f0f0;
}

.reply-item {
  font-size: 13px;
  line-height: 1.6;
  margin-bottom: 5px;
}

.reply-author {
  color: #457B9D;
  font-weight: 600;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}

@media (max-width: 768px) {
  .feeling-list { grid-template-columns: 1fr; }
  .feeling-card { padding: 15px; }
  .content-img { width: calc(50% - 5px); height: 120px; }
  .feeling-title { font-size: 24px; }
}

@media (max-width: 480px) {
  .content-img { width: 100%; height: 180px; }
}
</style>

/* 全局样式：修复点赞变红 */
<style>
.liked {
  color: #E63946 !important;
}
</style>
