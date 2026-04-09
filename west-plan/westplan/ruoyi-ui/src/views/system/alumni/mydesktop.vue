<template>
  <div class="my-desktop-page">
    <!-- 顶部面包屑导航 -->
    <div class="page-header">
      <el-breadcrumb separator="/" class="breadcrumb">
        <el-breadcrumb-item><a href="/system/forum/index">论坛首页</a></el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
        <el-breadcrumb-item>我的桌面</el-breadcrumb-item>
      </el-breadcrumb>
      <h2 class="page-title">我的桌面</h2>
    </div>

    <!-- 核心内容区 -->
    <div class="content-wrapper">
      <!-- 左侧个人信息栏 -->
      <div class="left-sidebar">
        <!-- 个人基础信息卡片 -->
        <el-card class="info-card" shadow="hover">
          <div class="avatar-wrapper">
            <el-avatar :src="userInfo.avatar" class="user-avatar"></el-avatar>
            <el-button type="primary" icon="el-icon-camera" class="avatar-edit-btn">更换头像</el-button>
          </div>
          <div class="user-basic-info">
            <h3 class="user-name">{{ userInfo.name }}</h3>
            <p class="user-identity">{{ userInfo.identity }}</p>
            <p class="user-desc">{{ userInfo.desc }}</p>
          </div>
          <!-- 数据统计 -->
          <div class="data-statistics">
            <div class="stat-item">
              <span class="stat-num">{{ statistics.postCount }}</span>
              <span class="stat-label">发布帖子</span>
            </div>
            <div class="stat-item">
              <span class="stat-num">{{ statistics.likeCount }}</span>
              <span class="stat-label">获赞总数</span>
            </div>
            <div class="stat-item">
              <span class="stat-num">{{ statistics.honorCount }}</span>
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
            <el-button type="primary" size="mini" @click="toPublishPost">发布新帖</el-button>
          </div>
          <div class="module-content">
            <div class="post-list" v-if="postList.length > 0">
              <div class="post-item" v-for="item in postList" :key="item.id">
                <div class="post-title">{{ item.title }}</div>
                <div class="post-content">{{ item.content | ellipsis(100) }}</div>
                <div class="post-meta">
                  <span>{{ item.createTime }}</span>
                  <span><i class="el-icon-view"></i> {{ item.viewCount }}</span>
                  <span><i class="el-icon-like"></i> {{ item.likeCount }}</span>
                  <el-button type="text" size="mini" @click="toEditPost(item.id)">编辑</el-button>
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
            <el-button type="text" size="mini" @click="toggleResumeExpand">
              {{ resumeExpand ? '收起' : '展开全部' }}
            </el-button>
          </div>
          <div class="module-content">
            <el-timeline :reverse="false">
              <el-timeline-item
                v-for="(item, index) in getShowResumeList"
                :key="index"
                :timestamp="item.time"
                :color="item.color"
              >
                <div class="resume-item">
                  <h4 class="resume-title">{{ item.title }}</h4>
                  <p class="resume-desc">{{ item.desc }}</p>
                  <el-button
                    type="text"
                    size="mini"
                    v-if="item.hasDetail"
                    @click="viewResumeDetail(item.id)"
                  >
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
            </div>
            <div class="module-content">
              <div class="honor-list">
                <div class="honor-item" v-for="item in honorList" :key="item.id">
                  <div class="honor-icon">
                    <i class="el-icon-trophy"></i>
                  </div>
                  <div class="honor-info">
                    <h4 class="honor-name">{{ item.name }}</h4>
                    <p class="honor-time">{{ item.time }}</p>
                    <p class="honor-issuer">颁发机构：{{ item.issuer }}</p>
                  </div>
                </div>
              </div>
            </div>
          </el-card>

          <!-- 工作感悟 -->
          <el-card class="module-card feeling-module" shadow="hover">
            <div class="module-header">
              <h3 class="module-title">
                <i class="el-icon-lightbulb"></i> 工作感悟
              </h3>
            </div>
            <div class="module-content">
              <div class="feeling-content" v-if="feelingList.length > 0">
                <div class="feeling-item" v-for="item in feelingList" :key="item.id">
                  <div class="feeling-date">{{ item.date }}</div>
                  <div class="feeling-text">{{ item.content }}</div>
                  <div class="feeling-tag">
                    <el-tag :color="item.tagColor">{{ item.tag }}</el-tag>
                  </div>
                </div>
              </div>
              <el-empty description="暂无工作感悟，记录你的成长吧~" v-else></el-empty>
            </div>
          </el-card>
        </div>

        <!-- 4. 个人风采 + 我的视频（双列布局） -->
        <div class="double-module">
          <!-- 个人风采（图片画廊） -->
          <el-card class="module-card style-module" shadow="hover">
            <div class="module-header">
              <h3 class="module-title">
                <i class="el-icon-picture"></i> 个人风采
              </h3>
              <el-upload
                action="/api/upload/image"
                list-type="picture-card"
                :show-file-list="false"
                :on-success="handleStyleUpload"
                class="upload-btn"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </div>
            <div class="module-content">
              <div class="style-gallery">
                <div
                  class="style-item"
                  v-for="(item, index) in styleList"
                  :key="index"
                  @click="previewStyleImage(item.url)"
                >
                  <img :src="item.url" alt="个人风采" class="style-img" />
                  <div class="style-desc">{{ item.desc }}</div>
                </div>
              </div>
            </div>
          </el-card>

          <!-- 我的视频 -->
          <el-card class="module-card video-module" shadow="hover">
            <div class="module-header">
              <h3 class="module-title">
                <i class="el-icon-video-camera"></i> 我的视频
              </h3>
              <el-upload
                action="/api/upload/video"
                list-type="picture-card"
                :show-file-list="false"
                :on-success="handleVideoUpload"
                class="upload-btn"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </div>
            <div class="module-content">
              <div class="video-list">
                <div class="video-item" v-for="(item, index) in videoList" :key="index">
                  <div class="video-cover">
                    <img :src="item.coverUrl" alt="视频封面" class="cover-img" />
                    <div class="play-btn" @click="playVideo(item.url)">
                      <i class="el-icon-video-play"></i>
                    </div>
                  </div>
                  <div class="video-info">
                    <h4 class="video-title">{{ item.title }}</h4>
                    <p class="video-duration">{{ item.duration }}</p>
                  </div>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </div>
    </div>

    <!-- 图片预览弹窗 -->
    <el-dialog
      title="个人风采预览"
      :visible.sync="imagePreviewVisible"
      width="80%"
      append-to-body
    >
      <img :src="previewImageUrl" alt="预览图" class="preview-img" />
    </el-dialog>

    <!-- 视频播放弹窗 -->
    <el-dialog
      title="我的视频播放"
      :visible.sync="videoPlayVisible"
      width="80%"
      append-to-body
    >
      <video
        :src="playVideoUrl"
        controls
        class="play-video"
        width="100%"
        height="auto"
      ></video>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { topHotList,newList,listForum_post} from "@/api/system/forum_post"



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
      userInfo: {
        avatar: 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
        name: '张三',
        identity: '2010级 · 计算机科学与技术 · 高级工程师',
        desc: '专注前端开发10年，现任职于某互联网大厂，热爱技术分享'
      },
      // 数据统计
      statistics: {
        postCount: 28,
        likeCount: 1256,
        honorCount: 8
      },
      // 我的帖子列表
      postList: [],
      // 个人履历
      resumeList: [],
      resumeExpand: false,
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
    ...mapGetters(['userId', 'isLogin']),
    // 履历列表（控制展开/收起）
    getShowResumeList() {
      if (this.resumeExpand) return this.resumeList;
      return this.resumeList.slice(0, 3); // 默认显示前3条
    }
  },
  created() {
    // 初始化加载数据
    this.initPageData();
  },
  methods: {
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
          postRes,
          resumeRes,
          honorRes,
          feelingRes,
          styleRes,
          videoRes
        ] = await Promise.all([
         // forumApi.post.myList({ userId: this.userId }), // 我的帖子
         // alumniApi.resume.list({ userId: this.userId }), // 个人履历
        //  alumniApi.honor.list({ userId: this.userId }), // 荣誉表彰
        //  alumniApi.feeling.list({ userId: this.userId }), // 工作感悟
       //   alumniApi.style.list({ userId: this.userId }), // 个人风采
         // alumniApi.video.list({ userId: this.userId }) // 我的视频
        ]);

        // 赋值各模块数据
        this.postList = postRes.data || [];
        this.resumeList = resumeRes.data || this.mockResumeData(); // 兜底模拟数据
        this.honorList = honorRes.data || this.mockHonorData();
        this.feelingList = feelingRes.data || this.mockFeelingData();
        this.styleList = styleRes.data || this.mockStyleData();
        this.videoList = videoRes.data || this.mockVideoData();

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
        // 加载模拟数据兜底
        this.loadMockData();
      }
    },

    /**
     * 加载模拟数据（接口异常时兜底）
     */
    loadMockData() {
      this.postList = this.mockPostData();
      this.resumeList = this.mockResumeData();
      this.honorList = this.mockHonorData();
      this.feelingList = this.mockFeelingData();
      this.styleList = this.mockStyleData();
      this.videoList = this.mockVideoData();
    },

    // ========== 模拟数据（用于测试，实际项目删除） ==========
    mockPostData() {
      return [
        { id: 1, title: '10年前端开发的成长感悟', content: '从零基础到高级工程师，这10年踩过的坑和收获的成长...', createTime: '2026-01-15', viewCount: 520, likeCount: 88 },
        { id: 2, title: 'Vue2项目实战：论坛系统开发总结', content: '分享Vue2 + Element UI开发论坛系统的核心要点，包括路由配置、组件封装、权限控制...', createTime: '2026-01-10', viewCount: 380, likeCount: 66 }
      ];
    },
    mockResumeData() {
      return [
        { id: 1, time: '2010-09 ~ 2014-06', title: 'XX大学 计算机科学与技术', desc: '本科，主修软件工程、数据结构、计算机网络，GPA 3.8/4.0，获校级奖学金', color: '#165DFF', hasDetail: true },
        { id: 2, time: '2014-07 ~ 2018-12', title: 'XX科技 前端开发工程师', desc: '负责企业官网、管理系统前端开发，熟练使用jQuery、Bootstrap，参与3个大型项目', color: '#722ED1', hasDetail: true },
        { id: 3, time: '2019-01 ~ 至今', title: 'XX互联网大厂 高级前端工程师', desc: '负责核心业务线前端架构设计，主导Vue2/Vue3项目迁移，团队技术分享负责人', color: '#00B42A', hasDetail: true }
      ];
    },
    mockHonorData() {
      return [
        { id: 1, name: '年度优秀员工', time: '2020-12', issuer: 'XX互联网大厂' },
        { id: 2, name: '前端技术创新奖', time: '2022-06', issuer: '行业技术峰会' },
        { id: 3, name: '校友贡献奖', time: '2023-09', issuer: 'XX大学校友会' }
      ];
    },
    mockFeelingData() {
      return [
        { id: 1, date: '2026-01-20', content: '工作的本质是解决问题，持续学习是解决问题的唯一途径', tag: '成长', tagColor: 'success' },
        { id: 2, date: '2026-01-10', content: '团队协作的核心是换位思考，多一份理解，少一份内耗', tag: '团队', tagColor: 'primary' },
        { id: 3, date: '2026-01-01', content: '10年职场，从技术到管理，最大的收获是学会了沉淀与分享', tag: '感悟', tagColor: 'warning' }
      ];
    },
    mockStyleData() {
      return [
        { url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg', desc: '2023年校友会合影' },
        { url: 'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef379461f7670636538jpeg.jpeg', desc: '技术分享现场' },
        { url: 'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg', desc: '户外团建' }
      ];
    },
    mockVideoData() {
      return [
        { id: 1, title: '前端性能优化实战分享', coverUrl: 'https://fuss10.elemecdn.com/c/63/419f91f7601e18527390217935851jpeg.jpeg', url: 'https://www.w3school.com.cn/i/movie.mp4', duration: '15:30' },
        { id: 2, title: '校友经验分享会', coverUrl: 'https://fuss10.elemecdn.com/9/bb/e2788e094e7499997469230740cc9ajpeg.jpeg', url: 'https://www.w3school.com.cn/i/movie.mp4', duration: '28:15' }
      ];
    },

    // ========== 交互方法 ==========
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
      this.$message.info(`查看履历详情：${id}`);
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
     * 上传个人风采图片成功回调
     */
    handleStyleUpload(response) {
      if (response.code === 200) {
        this.styleList.unshift({ url: response.data.url, desc: '新上传的风采照片' });
        this.$message.success('图片上传成功');
      }
    },

    /**
     * 上传视频成功回调
     */
    handleVideoUpload(response) {
      if (response.code === 200) {
        this.videoList.unshift({
          title: '新上传的视频',
          coverUrl: response.data.coverUrl,
          url: response.data.url,
          duration: '00:00'
        });
        this.$message.success('视频上传成功');
      }
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
          box-shadow: 0 4px 12px rgba(0,0,0,0.1);
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
        border-top: 1px solid rgba(0,0,0,0.05);
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
        box-shadow: 0 8px 24px rgba(0,0,0,0.12);
      }

      .module-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 15px 20px;
        border-bottom: 1px solid rgba(0,0,0,0.05);
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
      background: $bg-green-light;
      .module-title {
        color: $primary-green;
        i {
          color: $primary-green;
        }
      }
      .post-list {
        .post-item {
          padding: 15px 0;
          border-bottom: 1px solid rgba(0,0,0,0.05);
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
      background: $bg-blue-light;
      .module-title {
        color: $primary-blue;
        i {
          color: $primary-blue;
        }
      }
      .resume-item {
        .resume-title {
          font-size: 16px;
          font-weight: 500;
          margin: 0 0 5px 0;
        }
        .resume-desc {
          font-size: 14px;
          color: $text-secondary;
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
          border-bottom: 1px solid rgba(0,0,0,0.05);
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
            .honor-time, .honor-issuer {
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
        border-bottom: 1px solid rgba(0,0,0,0.05);
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
              background: rgba(255,255,255,0.8);
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
