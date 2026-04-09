<template>
  <div class="my-desktop-page">
    <!-- 顶部面包屑导航 -->
    <div class="page-header">

      <h2 class="page-title">我的桌面</h2>
    </div>

    <!-- 核心内容区 -->
    <div class="content-wrapper">
      <!-- 左侧个人信息栏 -->
      <div class="left-sidebar">
        <!-- 个人基础信息卡片 -->
        <el-card class="info-card" shadow="hover">
          <div class="avatar-wrapper">
            <el-avatar :src="avatar" class="user-avatar"></el-avatar>

          </div>
          <div class="user-basic-info">
            <h3 class="user-name">{{ userInfo.userName }}</h3>
            <p class="user-identity">{{ userInfo.major }}</p>

          </div>
          <!-- 数据统计 -->
          <div class="data-statistics">
            <div class="stat-item" v-if="postCount>0">
              <span class="stat-num">{{ postCount }}</span>
              <span class="stat-label">发布帖子</span>
            </div>
            <div class="stat-item" v-if="likeCount>0">
              <span class="stat-num">{{ likeCount }}</span>
              <span class="stat-label">获赞总数</span>
            </div>
            <div class="stat-item" v-if="honorCount>0">
              <span class="stat-num">{{ honorCount }}</span>
              <span class="stat-label">荣誉数</span>
            </div>
          </div>
        </el-card>
      </div>

      <!-- 右侧主内容区 -->
      <div class="right-content">
        <!-- 1. 我的帖子模块 -->
        <el-card class="module-card post-module" shadow="hover">
          <div class="module-header">
            <h3 class="module-title">
              <i class="el-icon-document"></i> 我的帖子
            </h3>
               <div>
             <el-button type="primary" size="mini" @click="toPublishPost">发布新帖</el-button>
              <el-button type="text" size="mini" @click="toggleForumExpand">
                {{ forumExpand ? '收起' : '展开全部' }}
              </el-button>
            </div>

          </div>
          <div class="module-content">
            <div class="post-list" v-if="postList.length > 0">
              <div class="post-item" v-for="item in getShowForumList" :key="item.id" @click="toDetailPost(item.id)">
                <div class="post-title">{{ item.postTitle }}</div>
                <div class="post-content">{{ item.postContent | ellipsis(100) }}</div>
                <div class="post-meta">
                  <span>{{ item.createTime }}</span>
                  <span><i class="el-icon-view"></i> {{ item.viewCount }}</span>
                  <span><i class="el-icon-like"></i> {{ item.likeCount }}</span>
                  <el-button type="text" size="mini" @click.stop="toEditPost(item.id)">编辑</el-button>
                </div>
              </div>
            </div>
            <el-empty description="暂无发布的帖子，快去分享你的动态吧~" v-else></el-empty>
          </div>
        </el-card>

        <!-- 2. 个人履历模块（时间轴） -->
        <el-card class="module-card resume-module" shadow="hover">
          <div class="module-header">
            <h3 class="module-title">
              <i class="el-icon-time"></i> 个人履历
            </h3>
            <div>
              <el-button type="text" size="mini" @click="toResumeAdd">
                <i class="el-icon-plus">新增</i>
              </el-button>
              <el-button type="text" size="mini" @click="toggleResumeExpand">
                {{ resumeExpand ? '收起' : '展开全部' }}
              </el-button>
            </div>

          </div>
          <div class="module-content">
            <el-timeline :reverse="false">
              <el-timeline-item v-for="(item, index) in getShowResumeList" :key="index" :timestamp="item.admissionTime"
                color="item.color">
                <div class="resume-item">

                  <h4 class="resume-title">{{ item.serviceProvince + item.serviceCity + item.serviceCounty +
                    item.serviceUnit
                    }}</h4>
                  <p class="resume-desc">{{ item.postType }}</p>
                  <el-button type="text" size="mini" v-if="item.postType" @click="viewResumeDetail(item.id)">
                    查看详情
                  </el-button>
                </div>
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-card>

        <!-- 3. 荣誉表彰 + 工作感悟（双列布局） -->
        <div class="double-module">
          <!-- 荣誉表彰 -->
          <el-card class="module-card honor-module" shadow="hover">
            <div class="module-header">
              <h3 class="module-title">
                <i class="el-icon-medal"></i> 荣誉表彰
              </h3>
              <div>
                <el-button type="text" size="mini" @click="toAddHonor">
                  <i class="el-icon-plus">新增</i>
                </el-button>
                <el-button type="text" size="mini" @click="toggleHonorExpand">
                  {{ honorExpand ? '收起' : '展开全部' }}
                </el-button>
              </div>

            </div>
            <div class="module-content">
              <div class="honor-list">
                <div class="honor-item" v-for="item in getShowHonorList" :key="item.id" @click="toDetailHonor(item.id)">
                  <div class="honor-icon">
                    <i class="el-icon-trophy"></i>
                  </div>
                  <div class="honor-info">
                    <h4 class="honor-name">获奖名称：{{ item.honorName }}</h4>
                    <p class="honor-desc"><span>级别：{{ item.honorLevel }}</span><span>获奖时间：{{ item.obtainTime }}</span> </p>
                    <p class="honor-issuer">颁发机构：{{ item.issueUnit }}  </p>
                  </div>
                </div>
              </div>
            </div>
          </el-card>

          <!-- 工作感悟 -->
          <el-card class="module-card feeling-module" shadow="hover">
            <div class="module-header">
              <h3 class="module-title">
                <i class="el-icon-lightbulb"></i> 个人风采
              </h3>
               <div>
              <el-button type="text" size="mini" @click="toFeelingAdd">
                <i class="el-icon-plus">新增</i>
              </el-button>
              <el-button type="text" size="mini" @click="toggleFeelingExpand">
                {{ resumeExpand ? '收起' : '展开全部' }}
              </el-button>
            </div>
            </div>

            <div class="module-content">
              <div class="feeling-content" v-if="feelingList.length > 0">
                <div class="feeling-item" v-for="item in getShowFeelingList" :key="item.id" @click="toDetailFeeling(item.id)">
                  <div class="feeling-date"> <el-tag >{{ item.category }}</el-tag>{{ item.title }}</div>
                  <div class="feeling-text" v-html="trimHtmlText(item.content, 80)"></div>

                </div>
              </div>
              <el-empty description="暂无工作感悟，记录你的成长吧~" v-else></el-empty>
            </div>
          </el-card>
        </div>


      </div>
    </div>

    <!-- 图片预览弹窗
    <el-dialog title="个人风采预览" :visible.sync="imagePreviewVisible" width="80%" append-to-body>
      <img :src="previewImageUrl" alt="预览图" class="preview-img" />
    </el-dialog>


    <el-dialog title="我的视频播放" :visible.sync="videoPlayVisible" width="80%" append-to-body>
      <video :src="playVideoUrl" controls class="play-video" width="100%" height="auto"></video>
    </el-dialog>
    -->
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { getMyInfo } from '@/api/system/student_apply';
import { myPosts } from '@/api/system/forum_post';
import { myResume } from '@/api/system/student_resume';
import { myHonor } from '@/api/system/alumni_honor';
import { myFeeling } from '@/api/system/alumni_feeling';

export default {
  name: 'MyDesktop',
  filters: {
    // 文本截断过滤器
    ellipsis(value, length) {
      if (!value) return '';
      if (value.length <= length) return value;
      return value.substring(0, length) + '...';
    }
  },
  data() {
    return {
      // 用户基础信息
      userInfo: {       },
      // 数据统计

        postCount: 28,
        likeCount: 1256,
        honorCount: 8,

      // 我的帖子列表
      postList: [],
      // 个人履历
      resumeList: [],
      resumeExpand: false,
      honorExpand: false,
      feelingExpand: false,
      forumExpand: false,
      // 荣誉表彰
      honorList: [],
      // 工作感悟
      feelingList: [],
      // 个人风采
      styleList: [],
      // 我的视频
      videoList: [],
      // 弹窗控制
      imagePreviewVisible: false,
      previewImageUrl: '',
      videoPlayVisible: false,
      playVideoUrl: ''
    };
  },
  computed: {
    // 从Vuex获取登录用户信息
    ...mapGetters(['userId', 'isLogin','avatar']),
     // 帖子列表（控制展开/收起）
    getShowForumList() {
      if (this.forumExpand) return this.postList;
      return this.postList.slice(0, 3); // 默认显示前3条
    },
    // 履历列表（控制展开/收起）
    getShowResumeList() {
      if (this.resumeExpand) return this.resumeList;
      return this.resumeList.slice(0, 3); // 默认显示前3条
    },
    // 获奖列表（控制展开/收起）
    getShowHonorList() {

      if (this.honorExpand) return this.honorList;
      return this.honorList.slice(0, 3); // 默认显示前3条
    },
    // 个人感受（控制展开/收起）
    getShowFeelingList() {

      if (this.feelingExpand) return this.feelingList;
      return this.feelingList.slice(0, 3); // 默认显示前3条
    }
  },
  created() {
    // 初始化加载数据
    this.initPageData();
  },
  methods: {
  // 截取HTML富文本的纯文字，限制长度
  trimHtmlText(html, length) {
    if (!html) return '';
    // 1. 先去除所有HTML标签，只保留纯文本
    let text = html.replace(/<[^>]+>/g, '');
    // 2. 去除空格、换行符
    text = text.trim().replace(/\s+/g, '');
    // 3. 限制字数，超过加省略号
    return text.length > length ? text.substring(0, length) + '...' : text;
  },
    /**
     * 页面初始化加载所有模块数据
     */
    async initPageData() {
      if (!this.isLogin) {
        this.$message.warning('请先登录后查看我的桌面');
        this.$router.push('/login');
        return;
      }

      try {
        // 并行加载各模块数据（提升性能）
        const [
          userRes,
          postRes,
          resumeRes,
          honorRes,
          feelingRes,

        ] = await Promise.all([
          getMyInfo(),
          myPosts(), // 我的帖子
          myResume(), // 个人履历
          myHonor(), // 荣誉表彰
          myFeeling(), // 工作感悟

        ]);

        if(userRes.code === 200){
          this.userInfo =  userRes.data   ;
        }else {
          this.userInfo.userName = '未登录';
        }



        // 赋值各模块数据
        this.postList = postRes.data;
        this.postCount = postRes.data.length;
        this.likeCount = postRes.data.reduce((sum, item) => sum + (item.likeCount || 0), 0);

        this.resumeList = resumeRes; // 兜底模拟数据

        this.honorList = honorRes  ;
        this.honorCount = honorRes.length;
        this.feelingList = feelingRes;


        // 更新用户信息（从接口获取）
        //const userRes = await alumniApi.user.info({ userId: this.userId });
        if (userRes.data) {
          this.userInfo = { ...this.userInfo, ...userRes.data };
        }

        // 更新数据统计
        this.statistics = {
          postCount: this.postList.length,
          likeCount: this.postList.reduce((sum, item) => sum + (item.likeCount || 0), 0),
          honorCount: this.honorList.length
        };
      } catch (error) {
        console.error('我的桌面数据加载失败：', error);
        this.$message.error('数据加载失败，请稍后重试');

      }
    },


    /**
     * 切换履历展开/收起
     */
    toggleForumExpand() {
      this.forumExpand = !this.forumExpand;
    },
    /**
     * 切换履历展开/收起
     */
    toggleResumeExpand() {
      this.resumeExpand = !this.resumeExpand;
    },
        /**
     * 查看履历详情
     */
    viewResumeDetail(id) {
      this.$router.push(`/system/resume/detail/${id}`);

    },


    /**
     * 添加新履历
     */
    toResumeAdd() {
      this.$router.push('/system/resume/add');
    },

    toggleHonorExpand() {
      this.honorExpand = !this.honorExpand;
    },

    /**
     * 预览个人风采图片
     */
    previewStyleImage(url) {
      this.previewImageUrl = url;
      this.imagePreviewVisible = true;
    },

    /**
     * 播放视频
     */
    playVideo(url) {
      this.playVideoUrl = url;
      this.videoPlayVisible = true;
    },





    /**
     * 跳转到发布帖子页
     */
    toPublishPost() {
      this.$router.push('/system/forum/post/add');
    },

    /**
     * 跳转到编辑帖子页
     */
    toEditPost(id) {

      this.$router.push(`/system/forum/post/edit/${id}`);
    },
    toDetailPost(id) {

      this.$router.push(`/system/forum/post/detail/${id}`);
    },

    /**
     * 跳转到发布荣誉页
     */
    toAddHonor() {
      this.$router.push('/system/honor/add');
    },

    /**
     * 跳转到编辑荣誉页
     */
    toEditHonor(id) {

      this.$router.push(`/system/honor/edit/${id}`);
    },
    toDetailHonor(id) {
      this.$router.push(`/system/honor/detail/${id}`);
    },
    /**
     * 切换个人感受展开/收起
     */
    toggleFeelingExpand() {
      this.feelingExpand = !this.feelingExpand;
    },
        /**
     * 查看个人感受详情
     */
    viewFeelingDetail(id) {
      this.$router.push(`/system/feeling/detail/${id}`);

    },


    /**
     * 添加新个人感受
     */
    toFeelingAdd() {
      this.$router.push('/system/feeling/add');
    },
    /**
     * 编辑新个人感受
     */
     toFeelingEdit(id) {

      this.$router.push(`/system/honor/edit/${id}`);
    },
    toDetailFeeling(id){
      this.$router.push(`/system/feeling/detail/${id}`);
    },
    toBack() {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang="scss">
// 全局色彩变量（丰富且协调）
$primary-blue: #165DFF;
$primary-purple: #722ED1;
$primary-green: #00B42A;
$primary-orange: #FF7D00;
$primary-pink: #F53F3F;
$primary-cyan: #0FC6C2;

// 辅助色
$bg-blue-light: #F0F7FF;
$bg-purple-light: #F9F0FF;
$bg-green-light: #F0F9FF;
$bg-orange-light: #FFF7E8;
$bg-pink-light: #FFECE8;
$bg-cyan-light: #E5F8F7;
$bg-base: #F5F7FA;
$text-primary: #333;
$text-secondary: #667085;
$text-light: #969BA5;

// 页面全局样式
.my-desktop-page {
  min-height: 100vh;
  background-color: $bg-base;
  padding: 20px;
  font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
}

// 顶部导航
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  .breadcrumb {
    font-size: 14px;
    color: $text-secondary;
  }

  .page-title {
    font-size: 24px;
    font-weight: 600;
    background: linear-gradient(90deg, $primary-blue, $primary-purple);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin: 0;
  }
}

// 核心内容区（左右分栏）
.content-wrapper {
  display: flex;
  gap: 20px;

  @media (max-width: 992px) {
    flex-direction: column;
  }

  // 左侧栏
  .left-sidebar {
    width: 300px;
    flex-shrink: 0;

    @media (max-width: 992px) {
      width: 100%;
    }

    .info-card {
      background: linear-gradient(135deg, $bg-purple-light, $bg-blue-light);
      border: none;
      border-radius: 12px;
      overflow: hidden;
      padding: 20px;
      text-align: center;

      .avatar-wrapper {
        position: relative;
        display: inline-block;

        .user-avatar {
          width: 100px;
          height: 100px;
          border: 4px solid #fff;
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .avatar-edit-btn {
          position: absolute;
          bottom: 0;
          right: 0;
          width: 30px;
          height: 30px;
          padding: 0;
          border-radius: 50%;
        }
      }

      .user-basic-info {
        margin: 15px 0;

        .user-name {
          font-size: 20px;
          font-weight: 600;
          color: $primary-purple;
          margin: 0;
        }

        .user-identity {
          font-size: 14px;
          color: $text-secondary;
          margin: 5px 0;
        }

        .user-desc {
          font-size: 13px;
          color: $text-light;
          margin: 0;
          line-height: 1.5;
        }
      }

      .data-statistics {
        display: flex;
        justify-content: space-around;
        margin-top: 20px;
        padding-top: 20px;
        border-top: 1px solid rgba(0, 0, 0, 0.05);

        .stat-item {
          text-align: center;

          .stat-num {
            display: block;
            font-size: 22px;
            font-weight: 600;
            color: $primary-blue;
          }

          .stat-label {
            font-size: 12px;
            color: $text-secondary;
          }
        }
      }
    }
  }

  // 右侧主内容区
  .right-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: 20px;

    // 通用模块卡片样式
    .module-card {
      border: none;
      border-radius: 12px;
      overflow: hidden;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
      }

      .module-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 15px 20px;
        border-bottom: 1px solid rgba(0, 0, 0, 0.05);

        .module-title {
          font-size: 18px;
          font-weight: 600;
          margin: 0;
          display: flex;
          align-items: center;

          i {
            margin-right: 8px;
            font-size: 20px;
          }
        }
      }

      .module-content {
        padding: 20px;
      }
    }

    // 双列模块布局
    .double-module {
      display: flex;
      gap: 20px;

      @media (max-width: 768px) {
        flex-direction: column;
      }

      .module-card {
        flex: 1;
      }
    }

    // ========== 各模块个性化样式 ==========
    // 我的帖子模块
    .post-module {
      //background: $bg-green-light;
      background: linear-gradient(135deg, #f8f7f4, #b8e6c8);

      .module-title {
        color: $primary-green;

        i {
          color: $primary-green;
        }
      }

      .post-list {
        .post-item {
          padding: 15px 0;
          border-bottom: 1px solid rgba(0, 0, 0, 0.05);

          &:last-child {
            border-bottom: none;
          }

          .post-title {
            font-size: 16px;
            font-weight: 500;
            color: $text-primary;
            margin-bottom: 8px;
          }

          .post-content {
            font-size: 14px;
            color: $text-secondary;
            line-height: 1.5;
            margin-bottom: 10px;
          }

          .post-meta {
            font-size: 12px;
            color: $text-light;
            display: flex;
            align-items: center;
            gap: 15px;
          }
        }
      }
    }

    // 个人履历模块
    .resume-module {
      color: #000;

      background: linear-gradient(135deg, #f0f9ff, #ffdfba);

      .module-title {
        color: #000;

        i {
          color: #000;
        }
      }

      .resume-item {
        .resume-title {
          font-size: 16px;
          font-weight: 500;
          margin: 5px 0 5px 0;
        }

        .resume-desc {
          font-size: 14px;
          color: #000;
          margin: 0 0 8px 0;
        }
      }
    }

    // 荣誉表彰模块
    .honor-module {
      background: $bg-orange-light;

      .module-title {
        color: $primary-orange;

        i {
          color: $primary-orange;
        }
      }

      .honor-list {
        .honor-item {
          display: flex;
          align-items: center;
          gap: 15px;
          padding: 10px 0;
          border-bottom: 1px solid rgba(0, 0, 0, 0.05);

          &:last-child {
            border-bottom: none;
          }

          .honor-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background: linear-gradient(135deg, $primary-orange, #FF9E3D);
            display: flex;
            align-items: center;
            justify-content: center;

            i {
              color: #fff;
              font-size: 20px;
            }
          }

          .honor-info {
            .honor-name {
              font-size: 16px;
              font-weight: 500;
              margin: 0 0 5px 0;
            }
            .honor-desc {
               font-size: 16px;
              font-weight: 500;
              margin: 0 0 5px 0;
              color: $text-secondary;

              span{
                margin-right: 10px;
              }
            }

            .honor-time,
            .honor-issuer {
              font-size: 12px;
              color: $text-secondary;
              margin: 0;
            }
          }
        }
      }
    }

    // 工作感悟模块
    .feeling-module {
      background: $bg-pink-light;

      .module-title {
        color: $primary-pink;

        i {
          color: $primary-pink;
        }
      }

      .feeling-item {
        padding: 15px 0;
        border-bottom: 1px solid rgba(0, 0, 0, 0.05);

        &:last-child {
          border-bottom: none;
        }

        .feeling-date {
          font-size: 12px;
          color: $text-light;
          margin-bottom: 5px;
        }

        .feeling-text {
          font-size: 14px;
          color: $text-primary;
          line-height: 1.6;
          margin-bottom: 8px;



        }
      }
    }

    // 个人风采模块
    .style-module {
      background: $bg-purple-light;

      .module-title {
        color: $primary-purple;

        i {
          color: $primary-purple;
        }
      }

      .style-gallery {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;

        .style-item {
          width: calc(33.33% - 10px);
          border-radius: 8px;
          overflow: hidden;
          cursor: pointer;

          @media (max-width: 768px) {
            width: calc(50% - 7.5px);
          }

          @media (max-width: 576px) {
            width: 100%;
          }

          .style-img {
            width: 100%;
            height: 180px;
            object-fit: cover;
          }

          .style-desc {
            padding: 8px;
            font-size: 12px;
            color: $text-secondary;
            background: #fff;
          }
        }
      }

      .upload-btn {
        width: 80px;
        height: 80px;
        line-height: 80px;
      }
    }

    // 我的视频模块
    .video-module {
      background: $bg-cyan-light;

      .module-title {
        color: $primary-cyan;

        i {
          color: $primary-cyan;
        }
      }

      .video-list {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;

        .video-item {
          width: calc(50% - 7.5px);

          @media (max-width: 576px) {
            width: 100%;
          }

          .video-cover {
            position: relative;
            border-radius: 8px;
            overflow: hidden;

            .cover-img {
              width: 100%;
              height: 180px;
              object-fit: cover;
            }

            .play-btn {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);
              width: 40px;
              height: 40px;
              border-radius: 50%;
              background: rgba(255, 255, 255, 0.8);
              display: flex;
              align-items: center;
              justify-content: center;
              cursor: pointer;

              i {
                color: $primary-cyan;
                font-size: 20px;
              }
            }
          }

          .video-info {
            padding: 8px 0;

            .video-title {
              font-size: 14px;
              font-weight: 500;
              margin: 0 0 5px 0;
            }

            .video-duration {
              font-size: 12px;
              color: $text-light;
              margin: 0;
            }
          }
        }
      }

      .upload-btn {
        width: 80px;
        height: 80px;
        line-height: 80px;
      }
    }
  }
}

// 弹窗预览样式
.preview-img {
  width: 100%;
  height: auto;
}

.play-video {
  width: 100%;
  min-height: 400px;
}

// 响应式适配
@media (max-width: 768px) {
  .my-desktop-page {
    padding: 10px;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .module-card .module-content {
    padding: 15px;
  }

  .play-video {
    min-height: 200px;
  }
}
</style>
