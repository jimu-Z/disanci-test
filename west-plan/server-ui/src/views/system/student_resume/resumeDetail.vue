<template>
   <div class="forum-container">
    <!-- 帖子信息卡片 -->
    <el-card class="forum-card" shadow="hover" v-loading="loading">
      <div class="mb-4">

        <div class="forum-post-meta mt-3">
          <span>起止时间：{{ form.admissionTime }}</span>
          <span>服务单位 {{ form.serviceProvince+form.serviceCity+form.serviceCounty+form.serviceUnit }} </span>

        </div>
      </div>

      <!-- 帖子富文本内容 -->
      <div class="post-content">
        <div >服务岗位描述：</div>
        {{ form.postType }}
          {{ form.userId }}
      </div>



      <!-- 操作按钮 -->
      <div class="mt-6 flex gap-3">

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
        </el-button>

         <el-button type="default" icon="el-icon-back" @click="toBack()">返回</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>

import {   getStudent_resume, delStudent_resume  } from "@/api/system/student_resume"
import RegionSelector from '@/components/RegionSelector';
import { getToken } from '@/utils/auth'
import { mapGetters } from 'vuex'
export default {
  name: "Student_resume",

  data() {
    return {
      // 路由传参的ID
      resumeId: '',
      // 省市区县绑定值（仅存储 code）


      form: {
        // 省市区县绑定值（仅存储 code）
        serviceRegion: {
          provinceCode: '',
          cityCode: '',
          districtCode: ''
        },
        userId: null,
        serviceProvince: null,
        serviceCity: null,
        serviceCounty: null,
        serviceUnit: null,
        postType: null,
         admissionTime: null,
      },
      // 完整的地区信息（存储名称，便于展示/提交）
      regionInfo: {},
      // 表单校验

    }
  },
  created() {
   this.initPage()
  },
  computed: {
    // 从Vuex获取登录状态
    ...mapGetters(['isLogin']),
     userId() { return this.$store.getters.id; },
    userName() { return this.$store.getters.nickName; },

  },
  methods: {


    /**
    * 页面初始化
    */
    async initPage() {

      // 1. 获取路由中的帖子ID（动态路由参数）
      this.resumeId = this.$route.params.id;


      // 无ID为添加帖子
      if (!this.resumeId) {

       return
      } else {//编辑帖子
        try {

          getStudent_resume(this.resumeId).then(response => {
            this.form = response.data
            this.form.serviceRegion = {
              provinceCode: response.data.serviceProvince,
              cityCode: response.data.serviceCity,
              districtCode: response.data.serviceCounty
            }

            this.title = "修改学生履历"
          })

          // console.log('editPostForm', this.editPostForm);
        } catch (error) {
          console.error('页面初始化失败：', error);
          this.$message.error(error.msg || '获取帖子信息失败，无法编辑');
        }
      }
      // 2. 并行加载板块列表和帖子详情（提升性能）

    },

      /** 修改按钮操作 */
    toBack( ) {
       this.$router.go(-1 );

    },

     /** 修改按钮操作 */
    toEdit( ) {
       this.$router.push({ path: `/system/resume/edit/${this.resumeId}` });

    },
    /** 删除按钮操作 */
    toDelete( ) {
      if(confirm('是否确认删除学生履历编号为"' + this.resumeId + '"的数据项？')){
        delStudent_resume(this.resumeId )
        this.$message.success("删除成功")
      }
       this.$router.go(-1)

     },

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
