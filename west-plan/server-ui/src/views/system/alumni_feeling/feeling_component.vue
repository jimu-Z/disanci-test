<template>
  <div class="alumni-feeling">
    <!-- 模块标题 -->
    <div class="feeling-header">
      <h2 class="feeling-title">
        <i class="el-icon-chat-line-round"></i> 校友感悟
      </h2>
      <p class="feeling-desc">分享西部计划的点滴故事，传递公益的温暖力量</p>
    </div>

    <!-- 感悟列表 -->
    <div class="feeling-list">
      <!-- 单条感悟卡片 -->
      <div
        class="feeling-card"
        v-for="(item ) in feelingList"
        :key="item.id"
      >
        <!-- 作者信息 -->
        <div class="card-header">
          <el-avatar :src="item.author.avatar" class="author-avatar"></el-avatar>
          <div class="author-info">
            <h3 class="author-name">{{ item.author.name }}</h3>
            <p class="author-meta">
              <span>{{ item.serviceRegion }}</span>
              <span class="time">{{ item.createTime }}</span>
            </p>
          </div>
        </div>

        <!-- 感悟内容 -->
        <div class="card-content">
          <!-- 文本内容 -->
          <div
            class="content-text"
            :class="{ 'content-collapse': item.isCollapse && item.content.length > 200 }"
          >
            {{ item.content }}
          </div>
          <!-- 展开/收起按钮 -->
          <div
            class="content-toggle"
            v-if="item.content.length > 200"
            @click="toggleContent(item)"
          >
            {{ item.isCollapse ? '查看全文' : '收起' }}
            <i :class="item.isCollapse ? 'el-icon-arrow-down' : 'el-icon-arrow-up'"></i>
          </div>

          <!-- 配图（多张） -->
          <div class="content-images" v-if="item.images.length">
            <img
              v-for="(img, imgIndex) in item.images"
              :key="imgIndex"
              :src="img"
              :alt="`感悟配图${imgIndex+1}`"
              class="content-img"
              @click="previewImage(item.images, imgIndex)"
            >
          </div>
        </div>

        <!-- 互动区（点赞/评论） -->
        <div class="card-action">
          <!-- 点赞按钮 -->
          <div class="action-item like-btn" @click="toggleLike(item)">
            <i
              class="el-icon-heart"
              :class="{ 'liked': item.isLiked }"
            ></i>
            <span class="count">{{ item.likeCount }}</span>
          </div>

          <!-- 评论按钮 -->
          <div class="action-item comment-btn" @click="toggleComment(item)">
            <i class="el-icon-chat-dot-round"></i>
            <span class="count">{{ item.commentCount }}</span>
          </div>
        </div>

        <!-- 评论区 -->
        <div class="comment-section" v-show="item.showComment">
          <!-- 评论输入框 -->
          <div class="comment-input">
            <el-avatar :src="currentUser.avatar" class="comment-avatar"></el-avatar>
            <el-input
              v-model="item.commentText"
              placeholder="写下你的评论..."
              class="input-main"
              @keyup.enter.native="submitComment(item)"
            ></el-input>
            <el-button
              type="primary"
              size="mini"
              @click="submitComment(item)"
            >
              发布
            </el-button>
          </div>

          <!-- 评论列表 -->
          <div class="comment-list" v-if="item.comments.length">
            <div
              class="comment-item"
              v-for="(comment, cIndex) in item.comments"
              :key="cIndex"
            >
              <!-- 评论作者信息 -->
              <el-avatar :src="comment.author.avatar" class="comment-item-avatar"></el-avatar>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-author">{{ comment.author.name }}</span>
                  <span class="comment-time">{{ comment.createTime }}</span>
                </div>
                <div class="comment-text">{{ comment.content }}</div>

                <!-- 回复区 -->
                <div class="comment-reply">
                  <span class="reply-btn" @click="toggleReply(item, comment)">回复</span>

                  <!-- 回复输入框 -->
                  <div class="reply-input" v-show="comment.showReply">
                    <el-input
                      v-model="comment.replyText"
                      placeholder="回复TA..."
                      size="mini"
                      @keyup.enter.native="submitReply(item, comment)"
                    ></el-input>
                    <el-button
                      size="mini"
                      type="primary"
                      @click="submitReply(item, comment)"
                    >
                      发送
                    </el-button>
                  </div>
                </div>

                <!-- 回复列表 -->
                <div class="reply-list" v-if="comment.replies.length">
                  <div
                    class="reply-item"
                    v-for="(reply, rIndex) in comment.replies"
                    :key="rIndex"
                  >
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

    <!-- 图片预览弹窗 -->
    <el-image-viewer
      v-if="showImageViewer"
      :url-list="previewImages"
      :on-close="closeImageViewer"
    ></el-image-viewer>
  </div>
</template>

<script>
export default {
  name: 'AlumniFeeling',
  data() {
    return {
      // 当前登录用户
      currentUser: {
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
        name: '西部计划管理员'
      },
      // 感悟列表数据
      feelingList: [
        {
          id: 1,
          author: {
            avatar: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
            name: '张明',
            serviceRegion: '新疆维吾尔自治区喀什地区'
          },
          serviceRegion: '新疆喀什',
          content: '在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。在西部计划的这一年，我深刻体会到了基层工作的不易与价值。每天和当地的老乡打交道，教孩子们读书写字，帮村里解决实际问题，虽然辛苦，但看到他们的笑容，一切都值得。西部的风景很美，人的心灵更美，这段经历会成为我人生中最宝贵的财富。',
          images: [
            'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef379461f7670636538jpeg.jpeg',
            'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
          ],
          createTime: '2026-02-18 15:30',
          isCollapse: true, // 是否折叠内容
          isLiked: false, // 是否点赞
          likeCount: 128, // 点赞数
          commentCount: 26, // 评论数
          showComment: false, // 是否显示评论区
          commentText: '', // 评论输入框内容
          comments: [
            {
              author: {
                avatar: 'https://fuss10.elemecdn.com/9/bb/e2788e094e7499997469230740cc9ajpeg.jpeg',
                name: '李华'
              },
              createTime: '2026-02-18 16:00',
              content: '向你致敬！我也是西部计划志愿者，在西藏服务，感同身受～',
              showReply: false, // 是否显示回复框
              replyText: '', // 回复输入框内容
              replies: [
                {
                  author: { name: '张明' },
                  content: '谢谢！西藏的条件更艰苦，你也辛苦了～'
                }
              ]
            }
          ]
        },
        {
          id: 2,
          author: {
            avatar: 'https://fuss10.elemecdn.com/c/63/419f91f7601e18527390217935851jpeg.jpeg',
            name: '王丽'
          },
          serviceRegion: '四川省凉山彝族自治州',
          content: '作为一名西部计划的支教老师，我在凉山的乡村小学已经服务了两年。这里的孩子们求知欲很强，但教育资源相对匮乏。我和其他志愿者一起，不仅教文化课，还开设了美术、音乐等兴趣课，希望能给孩子们打开更广阔的世界。看到孩子们从羞涩不敢说话，到现在能大方地用普通话交流，我觉得所有的付出都有了回报。西部计划不仅是帮助别人，更是自我成长的过程。',
          images: [],
          createTime: '2026-02-15 10:20',
          isCollapse: true,
          isLiked: true,
          likeCount: 256,
          commentCount: 48,
          showComment: false,
          commentText: '',
          comments: []
        }
      ],
      // 图片预览相关
      showImageViewer: false,
      previewImages: []
    };
  },
  methods: {
    /**
     * 展开/收起感悟内容
     */
    toggleContent(item) {
      item.isCollapse = !item.isCollapse;
    },
    /**
     * 点赞/取消点赞
     */
    toggleLike(item) {
      if (item.isLiked) {
        // 取消点赞
        item.likeCount--;
        item.isLiked = false;
        this.$message.info('已取消点赞');
      } else {
        // 点赞
        item.likeCount++;
        item.isLiked = true;
        this.$message.success('点赞成功');
      }
      // 实际项目中调用点赞接口
      // this.$api.alumniFeeling.like({ id: item.id, isLike: !item.isLiked });
    },
    /**
     * 展开/收起评论区
     */
    toggleComment(item) {
      item.showComment = !item.showComment;
    },
    /**
     * 提交评论
     */
    submitComment(item) {
      if (!item.commentText.trim()) {
        this.$message.warning('评论内容不能为空');
        return;
      }
      // 构造评论数据
      const newComment = {
        author: {
          avatar: this.currentUser.avatar,
          name: this.currentUser.name
        },
        createTime: new Date().toLocaleString('zh-CN', {
          year: 'numeric',
          month: '2-digit',
          day: '2-digit',
          hour: '2-digit',
          minute: '2-digit'
        }).replace(/\//g, '-'),
        content: item.commentText.trim(),
        showReply: false,
        replyText: '',
        replies: []
      };
      // 添加到评论列表
      item.comments.unshift(newComment);
      // 更新评论数
      item.commentCount++;
      // 清空输入框
      item.commentText = '';
      this.$message.success('评论发布成功');
      // 实际项目中调用发布评论接口
      // this.$api.alumniFeeling.addComment({
      //   feelingId: item.id,
      //   content: newComment.content
      // });
    },
    /**
     * 展开/收起回复框
     */
    toggleReply(item, comment) {
      comment.showReply = !comment.showReply;
    },
    /**
     * 提交回复
     */
    submitReply(item, comment) {
      if (!comment.replyText.trim()) {
        this.$message.warning('回复内容不能为空');
        return;
      }
      // 构造回复数据
      const newReply = {
        author: {
          name: this.currentUser.name
        },
        content: comment.replyText.trim()
      };
      // 添加到回复列表
      comment.replies.push(newReply);
      // 清空输入框
      comment.replyText = '';
      // 关闭回复框
      comment.showReply = false;
      this.$message.success('回复成功');
      // 实际项目中调用发布回复接口
      // this.$api.alumniFeeling.addReply({
      //   commentId: comment.id,
      //   content: newReply.content
      // });
    },
    /**
     * 预览图片
     */
    previewImage(images, index) {
      this.previewImages = images;
      this.showImageViewer = true;
      // Element UI ImageViewer 支持指定初始索引
      setTimeout(() => {
        const viewer = document.querySelector('.el-image-viewer__wrapper');
        if (viewer) {
          viewer.setAttribute('data-index', index);
        }
      }, 0);
    },
    /**
     * 关闭图片预览
     */
    closeImageViewer() {
      this.showImageViewer = false;
      this.previewImages = [];
    }
  }
};
</script>

<style scoped>
/* 全局盒模型 */
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

/* 模块标题 */
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

/* 感悟列表 */
.feeling-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(600px, 1fr));
  gap: 20px;
}

/* 单条感悟卡片 */
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

/* 作者信息 */
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

/* 感悟内容 */
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

/* 配图 */
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

/* 互动区 */
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

.like-btn .liked {
  color: #E63946;
  animation: pulse 0.5s ease;
}

.count {
  font-size: 14px;
}

/* 评论区 */
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

/* 评论列表 */
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

/* 回复区 */
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

/* 动画 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}

/* 响应式适配 */
@media (max-width: 768px) {
  .feeling-list {
    grid-template-columns: 1fr;
  }

  .feeling-card {
    padding: 15px;
  }

  .content-img {
    width: calc(50% - 5px);
    height: 120px;
  }

  .card-action {
    gap: 15px;
  }

  .feeling-title {
    font-size: 24px;
  }

  .content-text {
    font-size: 15px;
  }
}

@media (max-width: 480px) {
  .content-img {
    width: 100%;
    height: 180px;
  }

  .author-meta {
    flex-direction: column;
    gap: 5px;
  }
}
</style>
