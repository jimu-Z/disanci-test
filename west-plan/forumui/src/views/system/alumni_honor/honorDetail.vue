<template>
  <!--  详情页整体容器 -->
  <div class="news-detail-container">
    <div class="news-detail-wrapper">
      <!--  标题 -->
      <h1 class="news-title"> {{ form.honorName }}</h1>

      <!--  时间 + 作者 -->
      <div class="news-info">

      </div>
      <!-- 新闻正文内容 -->
      <div class="news-content">
        <div class="paragraph">

          <h4 class="honor-name">获奖名称：{{ form.honorName }}</h4>
          <p class="honor-desc"><span>级别：{{ form.honorLevel }}</span></p>
          <p class="honor-issuer">颁发机构：{{ form.issueUnit }} </p>
          <p class="honor-desc"><span>获奖时间：{{ form.obtainTime }}</span> </p>

        </div>
      </div>
      <!-- 新闻封面图 -->
      <div class="news-cover" v-for="(img, index) in imageListShow" :key="index">
        <img :src="img" alt="图片预览" />

      </div>



      <!-- 返回按钮 -->
      <div class="back-btn">
         <el-button
          type="default"
          icon="el-icon-edit"
          @click="toEdit"
          v-if="isLogin && form.userId === this.userId"
        >
          修改
        </el-button>
        <el-button
          type="danger"
          icon="el-icon-delete"
          @click="toDelete"
          v-if="isLogin && (form.userId === this.userId  )"

        >
          删除
        </el-button> <button @click="$router.back()">返回列表</button>
      </div>
    </div>
  </div>



</template>

<script>
import { listAlumni_honor, getAlumni_honor, delAlumni_honor, addAlumni_honor, updateAlumni_honor } from "@/api/system/alumni_honor"
import { getToken } from '@/utils/auth'
import { computed } from "vue";
export default {
  name: "Alumni_honor",
  data() {
    return {
      honorId: '',
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
      imagelist: [],

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,

      // 表单参数
      form: {},
      // 表单校验

    }
  },
  computed: {
    // ...mapGetters(['isLogin'])
    isLogin() { return this.$store.getters.isLogin; },
    userId() { return this.$store.getters.id; },
    userName() { return this.$store.getters.nickName; },
    imageListShow() {
      //对imageList中的每个字符串添加前缀： process.env.VUE_APP_BASE_API
      return this.imagelist.map(item => {
        return process.env.VUE_APP_BASE_API + item
      })
    },
  },
  created() {
    this.initPage()
  },
  methods: {
    async initPage() {
      // 1. 获取路由中的ID（动态路由参数）
      this.honorId = this.$route.params.id;

      if (!this.honorId) {
        this.$message.error("参数错误，无法加载详情")
        return this.$router.back()
      } else {//编辑
        try {
          const response = await getAlumni_honor(this.honorId)
          this.form = response.data
          this.imagelist = response.data.honorAttach ? response.data.honorAttach.split(',') : []

          // console.log('editPostForm', this.editPostForm);
        } catch (error) {
          console.error('页面初始化失败：', error);
          this.$message.error(error.msg || '获取 信息失败，无法编辑');
        }
      }
      // 2. 并行加载板块列表和帖子详情（提升性能）

    },
    parsePicture(pictureStr) {
      if (!pictureStr) return [];
      // 拆分逗号分隔的URL，过滤空字符串（避免末尾逗号导致的空元素）
      return pictureStr.split(",").filter(url => url.trim() !== "").map(url => { return process.env.VUE_APP_BASE_API + url });
    },
    // eslint-disable-next-line no-unused-vars




    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除校友荣誉彰编号为"' + ids + '"的数据项？').then(function () {
        return delAlumni_honor(ids)
      }).then(() => {
        this.$modal.msgSuccess("删除成功")
        this.$router.back()
      }).catch(() => { })
    },
 /** 修改按钮操作 */
    toEdit( ) {
       this.$router.push({ path: `/system/honor/edit/${this.honorId}` });

    },
    /** 删除按钮操作 */
    toDelete( ) {
      this.$confirm('是否确认删除编号为"' + this.honorId + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await delAlumni_honor(this.honorId)
        this.$message.success("删除成功")
        this.$router.back()
      }).catch(() => {})

     },
  }
}
</script>
<style scoped>
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
  background-color: #f5f7fa;
  /* 柔和背景色 */
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
  color: #34495e;
  font-size: 17px;
  line-height: 1.8;
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

  .news-info {
    flex-direction: column;
    gap: 8px;
    text-align: center;
  }
}
</style>
