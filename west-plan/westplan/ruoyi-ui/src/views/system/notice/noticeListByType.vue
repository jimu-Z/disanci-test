<template>
  <div class="notice-list-page">
    <div class="container-box" :style="containerStyle">
      <div class="page-header">
        <el-button icon="el-icon-arrow-left" @click="$router.back()" :style="btnStyle">返回</el-button>
        <h2 :style="titleStyle">{{ typeName }} 列表</h2>
      </div>

      <div class="news-list">
        <div
          class="news-item"
          :style="itemStyle"
          v-for="item in list"
          :key="item.noticeId"
          @click="goDetail(item.noticeId)"
        >
          <div class="title">{{ item.noticeTitle }}</div>
          <div class="time">{{ item.createTime }}</div>
        </div>

        <div class="empty" v-if="list.length === 0">暂无数据</div>
      </div>

      <el-pagination
        v-if="total > 0"
        @size-change="getList"
        @current-change="getList"
        :current-page="pageNum"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total"
        style="text-align: center; margin-top: 30px"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import { listNotice } from "@/api/system/notice";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "NoticeListPage",
  data() {
    return {
      typeName: "",
      list: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      themeColor: "#409EFF", // 主题色
    };
  },
  computed: {
    noticeType() {
      return this.$route.params.typeId;
    },
    containerStyle() {
      return {
        borderTop: `4px solid ${this.themeColor}`,
        boxShadow: `0 4px 20px rgba(0,0,0,0.08), 0 0 12px ${this.themeColor}30`
      };
    },
    titleStyle() {
      return {
        color: this.themeColor,
        fontWeight: "bold"
      };
    },
    btnStyle() {
      return {
        backgroundColor: this.themeColor,
        borderColor: this.themeColor,
        color: "#fff"
      };
    },
    itemStyle() {
      return {
        borderLeft: `3px solid ${this.themeColor}`
      };
    }
  },
  created() {
    this.getTypeName();
    this.getList();
  },
  methods: {
    async getTypeName() {
      try {
        const res = await getDicts("sys_notice_type");
        if (res.data) {
          let typeList = res.data.map(item => ({
            dictLabel: item.dictLabel,
            dictValue: item.dictValue
          }));
          const currType = typeList.find(item => item.dictValue == this.noticeType);
          this.typeName = currType ? currType.dictLabel : "新闻列表";
          this.setThemeColor(this.noticeType);
        }
      } catch (err) {
        console.error("获取分类失败", err);
      }
    },
    // 动态设置主题色（每个分类不同颜色！）
    setThemeColor(type) {
      const colorList = [
        "#409EFF", "#FF7D00", "#67C23A", "#F56C6C",
        "#8E44AD", "#E74C3C", "#1ABC9C", "#FF6F91"
      ];
      this.themeColor = colorList[(type - 1) % colorList.length];
    },

    async getList() {
      const res = await listNotice({
        noticeType: this.noticeType,
        status: 0,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      });
      if (res.code === 200) {
        this.list = res.rows;
        this.total = res.total;
      }
    },
    goDetail(id) {
      this.$router.push(`/system/notice/detail/${id}`);
    }
  }
};
</script>

<style scoped>
.notice-list-page {
  background: linear-gradient(135deg, #f5f7fa 0%, #e4eaf5 100%);
  min-height: 100vh;
  padding: 30px 0;
}
.container-box {
  width: 90%;
  max-width: 1000px;
  margin: 0 auto;
  background: #ffffff;
  padding: 28px;
  border-radius: 16px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}
.page-header {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 28px;
}
.page-header h2 {
  margin: 0;
  font-size: 24px;
  position: relative;
}
.page-header h2::after {
  content: "";
  position: absolute;
  bottom: -6px;
  left: 0;
  width: 40px;
  height: 3px;
  background-color: currentColor;
  border-radius: 2px;
}

.news-item {
  padding: 18px 20px;
  margin-bottom: 12px;
  background: #fafbfc;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.25s ease;
}
.news-item:hover {
  background: #f4f7ff;
  transform: translateX(6px);
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.06);
}
.title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 6px;
}
.time {
  font-size: 13px;
  color: #888;
}
.empty {
  text-align: center;
  padding: 60px 0;
  color: #999;
  font-size: 16px;
}
</style>
