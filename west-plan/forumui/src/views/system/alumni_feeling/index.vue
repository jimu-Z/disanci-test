<template>
  <div class="platform-page">
    <!-- 顶部导航 -->


    <!-- 主体内容 -->
    <main class="page-main">
      <!-- 校友感悟模块 -->
      <alumni-feeling />
    </main>

    <!-- 底部 -->
    <footer class="page-footer">
      <p>© 2026 德院XI望 版权所有</p>
    </footer>
  </div>
</template>

<script>
import AlumniFeeling from '@/views/system/alumni_feeling/feeling_component.vue';

export default {
  name: 'PlatformPage',
  components: {
    AlumniFeeling
  }
};
</script>

<style scoped>
.platform-page {
  min-height: 100vh;
  background-color: #f8f9fa;
}

.page-header {
  background: linear-gradient(90deg, #E63946, #D62828);
  color: #fff;
  padding: 20px;
  text-align: center;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
}

.page-main {
  padding: 20px;
}

.page-footer {
  background: #1D3557;
  color: #fff;
  text-align: center;
  padding: 15px;
  margin-top: 30px;
}

.page-footer p {
  margin: 0;
  font-size: 14px;
}
</style>
