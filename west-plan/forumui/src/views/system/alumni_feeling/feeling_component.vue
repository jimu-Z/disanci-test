<template>
  <div class="alumni-feeling">
    <div class="feeling-header">
      <h2 class="feeling-title">
        <i class="el-icon-user"></i> 个人风采
      </h2>
      <p class="feeling-desc">记录西部计划志愿者的成长瞬间，展现青春风采</p>
    </div>

    <div class="feeling-list">
      <div class="feeling-card" v-for="(item) in feelingList" :key="item.id" @click="goDetail(item)">
        <div class="card-banner">
          <span class="category-tag" :style="{backgroundColor: getCategoryColor(item.category)}">
            {{ getCategoryName(item.category) }}
          </span>
          <img :src="item.coverImg" :alt="item.title" class="banner-img" />
        </div>

        <div class="card-content">
          <h3 class="card-title">{{ item.title }}</h3>
          <!-- 自动过滤 HTML -->
          <p class="card-desc">{{ stripHtml(item.content && item.content.length > 80 ? item.content.slice(0,80)+'...' : item.content) }}</p>

          <div class="card-footer">
            <div class="author-info">
              <el-avatar :src="item.avatar" class="author-avatar"></el-avatar>
              <span class="author-name">{{ item.author }}</span>
            </div>
            <div class="action-info">
              <span class="time">{{ item.createTime }}</span>
              <div class="action-item">
                <i class="el-icon-heart" :class="{ 'liked': item.isLiked }"></i>
                <span class="count">{{ item.likeCount }}</span>
              </div>
              <div class="action-item">
                <i class="el-icon-chat-dot-round"></i>
                <span class="count">{{ item.commentCount }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog
      :title="currentItem.title"
      :visible.sync="detailVisible"
      width="800px"
      :before-close="closeDetail"
      class="feeling-detail-dialog"
    >
      <div class="detail-content">
        <div class="detail-header">
          <el-avatar :src="currentItem.avatar" class="author-avatar"></el-avatar>
          <div class="author-info">
            <h3 class="author-name">{{ currentItem.author }}</h3>
            <p class="author-meta">
              <span class="time">{{ currentItem.createTime }}</span>
            </p>
          </div>
        </div>

        <!-- 正文自动过滤 HTML -->
        <div class="detail-text">{{ stripHtml(currentItem.content) }}</div>

        <div class="detail-images" v-if="currentItem.picture && currentItem.picture.length > 0">
          <img v-for="(img, imgIndex) in currentItem.picture" :key="imgIndex" :src="img" :alt="`配图${imgIndex + 1}`"
            class="detail-img" @click="previewImage(currentItem.picture, imgIndex)">
        </div>

        <div class="detail-action">
          <div class="action-item like-btn" @click="toggleLike(currentItem)">
            <i class="el-icon-heart" :class="{ 'liked': currentItem.isLiked }"></i>
            <span class="count">{{ currentItem.likeCount }}</span>
          </div>
          <div class="action-item comment-btn" @click="toggleComment(currentItem)">
            <i class="el-icon-chat-dot-round"></i>
            <span class="count">{{ currentItem.commentCount }}</span>
          </div>
        </div>

        <div class="comment-section" v-show="currentItem.showComment">
          <div class="comment-input">
            <el-avatar :src="userAvatar" class="comment-avatar"></el-avatar>
            <el-input v-model="currentItem.commentText" placeholder="写下你的评论..." class="input-main"
              @keyup.enter.native="submitComment(currentItem)"></el-input>
            <el-button type="primary" size="mini" @click="submitComment(currentItem)">发布</el-button>
          </div>

          <div class="comment-list" v-if="currentItem.comments && currentItem.comments.length">
            <div class="comment-item" v-for="(comment, cIndex) in currentItem.comments" :key="cIndex">
              <el-avatar :src="comment.author.avatar" class="comment-item-avatar"></el-avatar>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-author">{{ comment.author.name }}</span>
                  <span class="comment-time">{{ comment.createTime }}</span>
                </div>
                <div class="comment-text">{{ stripHtml(comment.content) }}</div>

                <div class="comment-reply">
                  <span class="reply-btn" @click="toggleReply(currentItem, comment)">回复</span>
                  <div class="reply-input" v-show="comment.showReply">
                    <el-input v-model="comment.replyText" placeholder="回复TA..." size="mini"
                      @keyup.enter.native="submitReply(currentItem, comment)"></el-input>
                    <el-button size="mini" type="primary" @click="submitReply(currentItem, comment)">发送</el-button>
                  </div>
                </div>

                <div class="reply-list" v-if="comment.replies && comment.replies.length">
                  <div class="reply-item" v-for="(reply, rIndex) in comment.replies" :key="rIndex">
                    <span class="reply-author">{{ reply.author.name }}：</span>
                    <span class="reply-text">{{ stripHtml(reply.content) }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>

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
      detailVisible: false,
      currentItem: {},
      showImageViewer: false,
      previewImages: []
    };
  },
  computed: {
    isLogin() { return this.$store.getters.isLogin; },
    userId() { return this.$store.getters.id; },
    userAvatar() { return  this.$store.getters.avatar; },
    userName() { return this.$store.getters.nickName; }
  },
  created() {
    this.getList();
  },
  methods: {
    // 过滤 HTML 标签
    stripHtml(html) {
      if (!html) return '';
      return html.replace(/<[^>]+>/g, '').replace(/\s+/g, ' ').trim();
    },

    getList() {
      this.loading = true
      feelingWithUserList(this.queryParams).then(response => {
        this.feelingList = response.rows.map(item => {
          return {
            ...item,
            avatar: item.avatar ? process.env.VUE_APP_BASE_API + item.avatar : '',
            picture: item.picture ? item.picture.split(',').map(p => process.env.VUE_APP_BASE_API + p.trim()) : [],
            coverImg: item.picture
              ? process.env.VUE_APP_BASE_API + item.picture.split(',')[0].trim()
              : 'https://picsum.photos/600/300',
            isCollapse: true,
            showComment: false,
            commentText: '',
            isLiked: false,
            comments: item.comments ? item.comments.map(c => ({
              ...c,
              author: { ...c.author, avatar: c.author?.avatar ? process.env.VUE_APP_BASE_API + c.author.avatar : '' },
              showReply: false, replyText: '', replies: c.replies || []
            })) : []
          }
        })
        this.total = response.total
        this.loading = false
      })
    },
    getCategoryName(category) {
      const categoryMap = {'1':'志愿故事','2':'成长感悟','3':'工作纪实','4':'生活日常'}
      return categoryMap[category] || '个人风采'
    },
    getCategoryColor(category) {
      const colorMap = {'1':'#E63946','2':'#457B9D','3':'#2A9D8F','4':'#F4A261'}
      return colorMap[category] || '#667085'
    },
    goDetail(item) { this.currentItem = JSON.parse(JSON.stringify(item)); this.detailVisible = true; },
    closeDetail() { this.detailVisible = false; this.currentItem = {}; },
    toggleComment(item) { item.showComment = !item.showComment; },
    toggleLike(item) {
      item.isLiked ? item.likeCount-- : item.likeCount++;
      item.isLiked = !item.isLiked;
    },
    submitComment(item) {
      if (!item.commentText.trim()) return this.$message.warning('内容不能为空');
      item.comments.unshift({
        author: {avatar:this.userAvatar,name:this.userName},
        createTime: new Date().toLocaleString().replace(/\//g,'-'),
        content: item.commentText.trim(), showReply:false, replyText:'', replies:[]
      });
      item.commentCount++; item.commentText='';
    },
    toggleReply(item, comment) { comment.showReply = !comment.showReply; },
    submitReply(item, comment) {
      if (!comment.replyText.trim()) return this.$message.warning('回复不能为空');
      comment.replies.push({author:{name:this.userName},content:comment.replyText.trim()});
      comment.replyText=''; comment.showReply=false;
    },
    previewImage(images, index) { this.previewImages = images; this.showImageViewer = true; },
    closeImageViewer() { this.showImageViewer = false; this.previewImages = []; }
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
  grid-template-columns: repeat(auto-fill, minmax(500px, 1fr));
  gap: 24px;
}

.feeling-card {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
  cursor: pointer;
}

.feeling-card:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  transform: translateY(-5px);
}

.card-banner {
  position: relative;
  width: 100%;
  height: 260px;
  overflow: hidden;
}

.category-tag {
  position: absolute;
  top: 16px;
  left: 16px;
  z-index: 2;
  padding: 4px 12px;
  border-radius: 12px;
  color: #fff;
  font-size: 14px;
  font-weight: 500;
}

.banner-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.feeling-card:hover .banner-img {
  transform: scale(1.05);
}

.card-content {
  padding: 20px;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 12px 0;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-desc {
  font-size: 14px;
  color: #667085;
  line-height: 1.6;
  margin: 0 0 16px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 12px;
  border-top: 1px solid #f0f0f0;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.author-avatar {
  width: 32px;
  height: 32px;
}

.author-name {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.action-info {
  display: flex;
  align-items: center;
  gap: 16px;
}

.time {
  font-size: 13px;
  color: #999;
}

.action-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #667085;
}

.count {
  font-size: 13px;
}

.feeling-detail-dialog >>> .el-dialog__body {
  padding: 0 20px 20px;
}

.detail-content {
  padding: 0;
}

.detail-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.detail-header .author-avatar {
  width: 50px;
  height: 50px;
  margin-right: 15px;
}

.detail-header .author-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 5px 0;
}

.detail-header .author-meta {
  font-size: 14px;
  color: #667085;
  margin: 0;
}

.detail-text {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  margin-bottom: 20px;
}

.detail-images {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.detail-img {
  width: calc(33.33% - 7px);
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.detail-img:hover {
  transform: scale(1.05);
}

.detail-action {
  display: flex;
  gap: 20px;
  padding: 10px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 15px;
}

.detail-action .action-item {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
  font-size: 16px;
  color: #667085;
  transition: all 0.2s ease;
}

.detail-action .action-item:hover {
  color: #E63946;
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

@media (max-width: 1024px) {
  .feeling-list {
    grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  }
}

@media (max-width: 768px) {
  .feeling-list { grid-template-columns: 1fr; }
  .card-banner { height: 220px; }
  .feeling-title { font-size: 24px; }
  .detail-img { width: calc(50% - 5px); height: 120px; }
}

@media (max-width: 480px) {
  .card-banner { height: 180px; }
  .detail-img { width: 100%; height: 180px; }
  .card-footer { flex-direction: column; align-items: flex-start; gap: 8px; }
}
</style>

<style>
.liked {
  color: #E63946 !important;
  animation: pulse 0.5s ease;
}
</style>
