<template>
<!-- 新闻详情页整体容器 -->
  <div class="news-detail-container">
    <div class="news-detail-wrapper">
      <!-- 新闻标题 -->
      <h1 class="news-title">{{ feelingData.title }}</h1>

      <!-- 新闻信息栏：时间 + 作者 -->
      <div class="news-info">
        <span>{{ feelingData.createTime }}</span>
           <span>点赞： {{ feelingData.likeCount }}</span>  <span>  浏览量： {{ feelingData.viewCount }}</span>
      </div>

      <!-- 新闻封面图
      <div class="news-cover" v-if="newsData.noticeAttach">
        <img :src="newsData.coverImg" alt="新闻封面" />
      </div>
-->
      <!-- 新闻正文内容 -->
      <div class="news-content" v-html="feelingData.content">

      </div>

      <!-- 返回按钮 -->
      <div class="back-btn">
  <el-button
          type="default"
          icon="el-icon-edit"
          @click="toEdit"
          v-if="isLogin && feelingData.userId === userId"
        >
          修改
        </el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          @click="toDelete"
          v-if="isLogin && (feelingData.userId === userId   )"
          v-hasPermi="['system:feeling:remove']"
        >
          删除
        </el-button>

        <el-button  type="text‌" @click="$router.back()">返回列表</el-button>
      </div>
    </div>
  </div>

</template>

<script>
import {  getAlumni_feeling,updateAlumni_feeling ,delAlumni_feeling} from "@/api/system/alumni_feeling"
import { getToken } from '@/utils/auth'
import { computed } from "vue";
export default {
  name: "News_notice",
  data() {
    return {
        headers: {
        Authorization: 'Bearer ' + getToken(),
      },

      // 表单参数
      feelingData: {},

    }
  },
   computed: {
    // ...mapGetters(['isLogin'])
    isLogin() { return this.$store.getters.isLogin; },
    userId() { return this.$store.getters.id; },
    userName() { return this.$store.getters.nickName; },

  },
  created() {
     this.initPage()
  },
  methods: {
      async initPage() {
      // 1. 获取路由中的ID（动态路由参数）
     const id = this.$route.params.id;


      if (!id) {
        this.$message.error("参数错误")
        return this.$router.back()
      } else {//编辑
        try {

          getAlumni_feeling(id).then(response => {

            this.feelingData = response.data


          })

          // console.log('editPostForm', this.editPostForm);
        } catch (error) {
          console.error('页面初始化失败：', error);
          this.$message.error(error.msg || '获取 信息失败，无法编辑');
        }
      }
      // 2. 并行加载板块列表和帖子详情（提升性能）

    },
    toEdit() {
      this.$router.push({
        path: '/system/feeling/edit/' + this.feelingData.id
      })
    },
    toDelete() {
      this.$confirm('此操作将永久该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 确定删除
        delAlumni_feeling(this.feelingData.id).then(response => {
          this.$message.success('删除成功')
          this.$router.go(-1)
        })
      }).catch(() => {
        // 取消删除
        this.$message({
          type: 'info',
        })
      })

    },
   // 点赞
    async handleLike(item) {
      try {
         item.likeCount++;
        await  updateAlumni_feeling( {id:item.Id,likeCount:item.likeCount});
        this.$message.success('点赞成功');

      } catch (error) {
        console.error('评论点赞失败：', error);
      }
    },


  }
}
</script>
<style scoped  >
/* 全局样式重置 + 页面背景 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Microsoft YaHei", sans-serif;
}

.news-detail-container {
  width: 100%;
  min-height: 100vh;
  background-color: #f5f7fa; /* 柔和背景色 */
  padding: 30px 15px;
}

/* 内容容器：居中 + 卡片样式 */
.news-detail-wrapper {
  max-width: 800px;
  margin: 0 auto;
  background: #ffffff;
  padding: 45px 55px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

/* 标题样式 */
.news-title {
  font-size: 28px;
  color: #2c3e50;
  line-height: 1.4;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
}

/* 信息栏：时间、作者 */
.news-info {
  display: flex;
  justify-content: center;
  gap: 30px;
  color: #7f8c8d;
  font-size: 15px;
  margin-bottom: 30px;
}
.news-info >>>span {
  margin-right: 8px ;

}
/* 新闻封面 */
.news-cover {
  width: 100%;
  margin-bottom: 35px;
  border-radius: 10px;
  overflow: hidden;
}

.news-cover img {
  width: 100%;
  height: auto;
  display: block;
  object-fit: cover;
}

/* 正文样式 */
.news-content {
  width: 100%;
  color: #34495e;
  font-size: 17px;
  line-height: 1.8;
}

.news-content >>>img {
  max-width: 100%!important;
  height: auto;
  display: block;
  object-fit: cover;
}

.paragraph {
  margin-bottom: 22px;
  text-align: justify;
}

/* 返回按钮 */
.back-btn {
  margin-top: 40px;
  text-align: center;
}

.back-btn button {
  padding: 12px 30px;
  background-color: #3498db;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}


.back-btn button:hover {
  background-color: #2980b9;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .news-detail-wrapper {
    padding: 25px 20px;
  }

  .news-title {
    font-size: 22px;
  }



}
</style>
