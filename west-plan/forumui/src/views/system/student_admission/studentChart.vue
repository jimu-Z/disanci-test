<template>
  <div class="west-dashboard-dark">
    <!-- 顶部标题 -->
    <div class="dashboard-top">
      <div class="title-wrap">
        <h1>西部计划数据展示仓</h1>
        <p>2004‑2025 全景统计</p>
      </div>
    </div>

    <!-- 数据概览卡片 -->
    <div class="stat-cards">
      <div class="stat-card" v-for="(item, i) in statCards" :key="i">
        <div class="card-icon" :style="{ color: item.color }">
          <i :class="item.icon"></i>
        </div>
        <div class="card-text">
          <p class="label">{{ item.label }}</p>
          <p class="value">{{ item.value }}</p>
        </div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="chart-wrapper">
      <div class="chart-box">
        <div class="box-title">历年参与趋势</div>
        <div id="chartYear" class="chart-item"></div>
      </div>
      <div class="chart-box">
        <div class="box-title">性别分布</div>
        <div id="chartGender" class="chart-item"></div>
      </div>
    </div>

    <div class="chart-wrapper">
      <div class="chart-box">
        <div class="box-title">政治面貌统计</div>
        <div id="chartPolitical" class="chart-item"></div>
      </div>
      <div class="chart-box">
        <div class="box-title">学院参与 TOP10</div>
        <div id="chartCollege" class="chart-item"></div>
      </div>
    </div>

    <!-- 学院表格 -->
  <!-- 学院表格 -->
<div class="table-box">
  <div class="box-title">全院参与数据总表</div>
  <el-table
    :data="collegeTableData"
    size="small"
    border
    :header-cell-style="{ background: '#0d1122', color: '#ffffff', fontWeight: 600 }"
    :cell-style="{ color: '#ffffff' ,background: '#1a1f35'}"
  >
    <el-table-column
      label="学院名称"
      prop="college"
      min-width="180"
    />
    <el-table-column
      label="人数"
      prop="count"
      align="center"
      min-width="70"
    />
    <el-table-column
      label="占比"
      align="center"
      min-width="80"
    >
      <template #default="scope">
        <span style="color:#36d399; font-weight:600;">
          {{ ((scope.row.count / 604) * 100).toFixed(1) }}%
        </span>
      </template>
    </el-table-column>
  </el-table>
</div>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  mounted() {
    this.initAllChart();
  },
  data() {
    return {
      yearData: [1,2,1,7,3,6,5,3,0,6,5,7,21,16,37,38,20,19,19,34,161,193],
      yearList: [2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023,2024,2025],
      genderData: [{ name: "女", value: 329 },{ name: "男", value: 269 }],
      politicalData: [
        { name: "中共党员", value: 118 },
        { name: "预备党员", value: 40 },
        { name: "共青团员", value: 302 },
        { name: "群众", value: 96 }
      ],
      collegeData: [
        { college: "计算机学院", count: 83 },
        { college: "美术学院", count: 51 },
        { college: "音乐学院", count: 48 },
        { college: "生态资源学院", count: 43 },
        { college: "纺织服装学院", count: 42 },
        { college: "物理电子学院", count: 40 },
        { college: "法学公共学院", count: 38 },
        { college: "文历学院", count: 35 },
        { college: "化工学院", count: 35 },
        { college: "体育学院", count: 31 },
      ],
      collegeTableData: [
        { college: "马克思主义学院", count: 26 },
        { college: "法学与公共管理学院", count: 38 },
        { college: "教师教育学院", count: 28 },
        { college: "文学与历史文化学院", count: 35 },
        { college: "外国语学院", count: 20 },
        { college: "美术学院", count: 51 },
        { college: "音乐学院", count: 48 },
        { college: "体育学院", count: 31 },
        { college: "商学院", count: 10 },
        { college: "数学与大数据学院", count: 21 },
        { college: "物理与电子信息学院", count: 40 },
        { college: "化学化工学院", count: 35 },
        { college: "生态与资源环境学院", count: 43 },
        { college: "生命科学学院", count: 20 },
        { college: "计算机与信息管理学院", count: 83 },
        { college: "能源与机械学院", count: 14 },
        { college: "纺织服装学院", count: 42 },
        { college: "药学院", count: 29 },
        { college: "健康医学院", count: 3 },
        { college: "别尔哥罗德食品学院", count: 1 },
      ],
      statCards: [
        { label: "总参与", value: "604", icon: "el-icon-user-solid", color: "#409eff" },
        { label: "年份", value: "22", icon: "el-icon-calendar", color: "#67c23a" },
        { label: "学院", value: "20", icon: "el-icon-office-building", color: "#e6a23c" },
        { label: "年均", value: "27.5", icon: "el-icon-s-data", color: "#f56c6c" },
        { label: "女性", value: "55%", icon: "el-icon-chat-dot-round", color: "#ff69b4" },
        { label: "团员", value: "302", icon: "el-icon-star-on", color: "#9966ff" },
      ],
    };
  },
  methods: {
    initAllChart() {
      const chartYear = echarts.init(document.getElementById("chartYear"));
      chartYear.setOption({
        backgroundColor: "transparent", tooltip: { trigger: "axis" },
        grid: { left:5, right:10, top:10, bottom:5, containLabel:true },
        xAxis: { type: "category", data: this.yearList, axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#999", fontSize:10 } },
        yAxis: { type: "value", axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#999" }, splitLine: { lineStyle: { color: "#181818" } } },
        series: [{ name: "人数", type: "line", smooth: true, data: this.yearData, itemStyle: { color: "#00c48c" }, lineStyle: { width: 2 }, areaStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"rgba(0,196,140,0.3)"},{offset:1,color:"rgba(0,196,140,0)"}]) } }],
      });

      const chartGender = echarts.init(document.getElementById("chartGender"));
      chartGender.setOption({
        backgroundColor: "transparent", tooltip: { trigger: "item" },
        series: [{ name: "性别", type: "pie", radius: ["40%", "75%"], data: this.genderData, itemStyle: { borderRadius: 4 }, color: ["#ff69b4","#409eff"], label: { color: "#eee", fontSize:12 } }],
      });

      const chartPolitical = echarts.init(document.getElementById("chartPolitical"));
      chartPolitical.setOption({
        backgroundColor: "transparent", tooltip: { trigger: "axis" },
        grid: { left:5, right:5, top:10, bottom:5, containLabel:true },
        xAxis: { type: "category", data: this.politicalData.map(d=>d.name), axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#999", fontSize:10 } },
        yAxis: { type: "value", axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#999" }, splitLine: { lineStyle: { color: "#181818" } } },
        series: [{ type: "bar", data: this.politicalData.map(d=>d.value), itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:"#9966ff"},{offset:1,color:"#5b44ff"}]), borderRadius: 4 } }],
      });

      const chartCollege = echarts.init(document.getElementById("chartCollege"));
      chartCollege.setOption({
        backgroundColor: "transparent", tooltip: { trigger: "axis" },
        grid: { left:70, right:10, top:10, bottom:5, containLabel:true },
        xAxis: { type: "value", axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#999" }, splitLine: { lineStyle: { color: "#181818" } } },
        yAxis: { type: "category", data: this.collegeData.map(d=>d.college).reverse(), axisLine: { lineStyle: { color: "#333" } }, axisLabel: { color: "#eee", fontSize:11 } },
        series: [{ type: "bar", data: this.collegeData.map(d=>d.count).reverse(), itemStyle: { color: new echarts.graphic.LinearGradient(1,0,0,0,[{offset:0,color:"#00c48c"},{offset:1,color:"#0096ff"}]), borderRadius:4 } }],
      });

      window.addEventListener("resize", () => {
        chartYear.resize(); chartGender.resize(); chartPolitical.resize(); chartCollege.resize();
      });
    },
  },
};
</script>

<style scoped>
.west-dashboard-dark {
  background: #0f1219;
  min-height: 100vh;
  padding: 10px;
  color: #e0e0e0;
}

/* 顶部标题 */
.dashboard-top {
  text-align: center;
  margin-bottom: 12px;
}
.title-wrap h1 {
  font-size: 22px;
  color: #fff;
  margin: 0;
}
.title-wrap p {
  font-size: 12px;
  color: #8899b3;
  margin: 2px 0 0;
}

/* 数据卡片 */
.stat-cards {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 8px;
  margin-bottom: 12px;
}
.stat-card {
  background: #1a1f35;
  border-radius: 8px;
  padding: 10px 8px;
  display: flex;
  align-items: center;
  border: 1px solid #2a2f4a;
}
.card-icon {
  font-size: 20px;
  margin-right: 8px;
}
.card-text .label {
  font-size: 11px;
  color: #8899b3;
  margin: 0;
}
.card-text .value {
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  margin: 2px 0 0;
}

/* 图表布局 */
.chart-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
  margin-bottom: 8px;
}
.chart-box {
  background: #1a1f35;
  border-radius: 8px;
  padding: 10px;
  border: 1px solid #2a2f4a;
  height: 260px;
}
.box-title {
  font-size: 14px;
  color: #c0cfff;
  margin-bottom: 6px;
  font-weight: 500;
}
.chart-item {
  width: 100%;
  height: calc(100% - 22px);
}

/* 表格 - 高亮清晰版 */
/* 表格 - 超清高亮版 */
.table-box {
  background: #0f1424;
  border-radius: 8px;
  padding: 10px;
  border: 1px solid #25305a;
  margin-top: 8px;
}

:deep(.el-table) {
  background: #0f1424 !important;
  border: none !important;
  font-size: 12px;
}
:deep(.el-table th) {
  background: #151c32 !important;
  color: #ffffff !important;
  border-color: #25305a !important;
}
:deep(.el-table td) {
  background: #121a30 !important;
  color: #ffffff !important;
  border-color: #25305a !important;
  padding: 8px 0 !important;
}
:deep(.el-table tr:hover > td) {
  background: #1b2442 !important;
  color: #ffffff !important;
}

/* 响应式 */
@media (max-width: 1200px) {
  .stat-cards { grid-template-columns: repeat(3, 1fr); }
  .chart-wrapper { grid-template-columns: 1fr; }
  .chart-box { height: 240px; }
}
@media (max-width: 768px) {
  .stat-cards { grid-template-columns: repeat(2, 1fr); }
}
</style>
