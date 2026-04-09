<template>
  <div class="notice-container">
    <div class="container-box">
      <!-- 双列布局容器 -->
      <div class="type-grid">
        <!-- 按分类循环渲染模块 -->
        <div class="type-section" v-for="(typeItem, index) in typeList" :key="index">
          <!-- 分类标题栏 -->
          <div class="type-header" :style="{ backgroundColor: getTypeColor(typeItem.dictValue) }">
            <span>{{ typeItem.dictLabel }}</span>
            <el-button
              type="text"
              icon="el-icon-more"
              @click="goMoreList(typeItem.dictValue)"
              style="color: #fff; margin-left: 10px"
            >
              更多
            </el-button>
          </div>

          <!-- 分类下新闻列表（最多6条） -->
          <div class="notice-list">
            <div
              class="notice-item"
              v-for="item in typeItem.newsList"
              :key="item.noticeId"
              @click="goDetail(item.noticeId)"
            >
              <div class="notice-title">{{ item.noticeTitle }}</div>
              <div class="notice-time">{{ item.createTime }}</div>
            </div>

            <div class="empty" v-if="typeItem.newsList.length === 0">
              暂无该分类新闻
            </div>
          </div>
        </div>
      </div>

      <!-- 无分类提示 -->
      <div class="empty-page" v-if="typeList.length === 0">
        暂无新闻分类数据
      </div>
    </div>
  </div>
</template>

<script>
import { getDicts } from "@/api/system/dict/data";
import { listNotice } from "@/api/system/notice";

export default {
  name: "NoticeIndex",
  dicts: ["sys_notice_type"],
  data() {
    return {
      typeList: []
    };
  },
  created() {
    this.getNoticeTypeDict();
  },
  methods: {
    async getNoticeTypeDict() {
      try {
        const res = await getDicts("sys_notice_type");
        if (res.data) {
          this.typeList = res.data.map((item) => ({
            dictLabel: item.dictLabel,
            dictValue: item.dictValue,
            newsList: []
          }));
          await this.getNewsByTypeList();
        }
      } catch (err) {
        console.error("获取通知类型字典失败：", err);
      }
    },

    async getNewsByTypeList() {
      if (!this.typeList || this.typeList.length === 0) return;

      for (let i = 0; i < this.typeList.length; i++) {
        const type = this.typeList[i].dictValue;
        if (!type) continue;

        try {
          const queryParams = {
            pageNum: 1,
            pageSize: 6,
            noticeType: type,
            status: 0
          };

          const res = await listNotice(queryParams);
          if (res.code === 200) {
            this.typeList[i].newsList = res.rows;
          }
        } catch (error) {
          console.error("获取新闻失败：", error);
          this.typeList[i].newsList = [];
        }
      }
    },

    goMoreList(type) {
      this.$router.push({
        path: `/system/notice/list/${type}`,
      });
    },

    goDetail(id) {
      this.$router.push(`/system/notice/detail/${id}`);
    },

    getTypeColor(type) {
      const colorList = [
        "#409EFF",
        "#FF7D00",
        "#67C23A",
        "#F56C6C",
        "#909399",
        "#8E44AD",
        "#E74C3C",
        "#1ABC9C"
      ];
      return colorList[(type - 1) % colorList.length];
    }
  }
};
</script>

<style scoped>
.notice-container {
  background-color: #f5f7fa;
  min-height: 100vh;
  padding: 20px 0;
}
.container-box {
  width: 92%;
  max-width: 1200px;
  margin: 0 auto;
}

/* ========== 双列网格布局核心 ========== */
.type-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

/* 小屏幕自动变成单列 */
@media (max-width: 768px) {
  .type-grid {
    grid-template-columns: repeat(1, 1fr);
  }
}

.type-section {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  margin-bottom: 0; /* 网格已自带间距，取消原有margin */
}
.type-header {
  padding: 14px 20px;
  color: white;
  font-size: 18px;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.notice-list {
  padding: 10px 20px;
}
.notice-item {
  padding: 12px 0;
  border-bottom: 1px dashed #f0f0f0;
  cursor: pointer;
}
.notice-item:hover {
  color: #409eff;
  padding-left: 5px;
  transition: all 0.2s;
}
.notice-title {
  font-size: 15px;
  color: #333;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.notice-time {
  font-size: 12px;
  color: #999;
}
.empty {
  padding: 30px 0;
  text-align: center;
  color: #999;
}
.empty-page {
  padding: 100px 0;
  text-align: center;
  color: #999;
  font-size: 16px;
}
</style>
