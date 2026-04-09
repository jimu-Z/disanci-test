<template>
  <div class="news-detail-container">
    <div class="news-detail-wrapper" :style="containerStyle">
      <h1 class="news-title">{{ newsData.noticeTitle }}</h1>

      <div class="news-info">
        <span>{{ newsData.createTime }}</span>
        <span>{{ newsData.createBy }}</span>
        <span :style="titleStyle">{{ typeName }}</span>
      </div>

      <div class="news-content" v-html="newsData.noticeContent"></div>

      <div class="back-btn">
        <el-button @click="$router.back()" :style="btnStyle" icon="el-icon-arrow-left">
          返回列表
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getNotice } from "@/api/system/notice";
import { getDicts } from "@/api/system/dict/data";
import { getToken } from "@/utils/auth";

export default {
  name: "News_notice",
  data() {
    return {
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      newsData: {},
      typeName: "",
      themeColor: "#409EFF",
    };
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin;
    },
    userId() {
      return this.$store.getters.id;
    },
    userName() {
      return this.$store.getters.nickName;
    },
    containerStyle() {
      return {
        borderTop: `4px solid ${this.themeColor}`,
        boxShadow: `0 4px 20px rgba(0,0,0,0.08), 0 0 12px ${this.themeColor}30`,
      };
    },
    titleStyle() {
      return {
        color: this.themeColor,
        fontWeight: "bold",
      };
    },
    btnStyle() {
      return {
        backgroundColor: this.themeColor,
        borderColor: this.themeColor,
        color: "#fff",
      };
    },
  },
  created() {
    this.initPage();
  },
  methods: {
    async initPage() {
      const id = this.$route.params.id;
      if (!id) {
        this.$message.error("参数错误");
        return this.$router.back();
      }

      try {
        const res = await getNotice(id);
        this.newsData = res.data;
        // 加载类型名称 + 自动变色
        await this.getTypeName(this.newsData.noticeType);
      } catch (error) {
        console.error("初始化失败：", error);
        this.$message.error("获取信息失败");
      }
    },

    // 获取分类名称（自动变色）
    async getTypeName(noticeType) {
      try {
        const res = await getDicts("sys_notice_type");
        if (res.data) {
          const currType = res.data.find(
            (item) => item.dictValue == noticeType
          );
          this.typeName = currType ? currType.dictLabel : "通知公告";
          this.setThemeColor(noticeType);
        }
      } catch (err) {
        console.error("获取分类失败", err);
      }
    },

    // 自动设置主题色（和列表页完全一致）
    setThemeColor(type) {
      const colorList = [
        "#409EFF",
        "#FF7D00",
        "#67C23A",
        "#F56C6C",
        "#8E44AD",
        "#E74C3C",
        "#1ABC9C",
        "#FF6F91",
      ];
      this.themeColor = colorList[(type - 1) % colorList.length];
    },
  },
};
</script>

<style scoped>
.news-detail-container {
  background: linear-gradient(135deg, #f5f7fa 0%, #e4eaf5 100%);
  width: 100%;
  min-height: 100vh;
  padding: 30px 15px;
}

.news-detail-wrapper {
  max-width: 850px;
  margin: 0 auto;
  background: #ffffff;
  padding: 50px 60px;
  border-radius: 16px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.news-title {
  font-size: 28px;
  color: #2c3e50;
  line-height: 1.4;
  font-weight: 700;
  margin-bottom: 20px;
  text-align: center;
}

.news-info {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 25px;
  color: #7f8c8d;
  font-size: 15px;
  margin-bottom: 40px;
  padding-bottom: 20px;
  border-bottom: 1px dashed #eee;
}

.news-content {
  width: 100%;
  color: #34495e;
  font-size: 17px;
  line-height: 1.9;
}

.news-content >>> img {
  max-width: 100% !important;
  height: auto !important;
  border-radius: 8px;
  margin: 10px 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.back-btn {
  margin-top: 50px;
  text-align: center;
}

@media (max-width: 768px) {
  .news-detail-wrapper {
    padding: 30px 25px;
  }

  .news-title {
    font-size: 24px;
  }
}
</style>
