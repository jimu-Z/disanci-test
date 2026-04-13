<template>
  <div v-if="!loading && (error || empty)" class="request-state">
    <el-empty :description="error ? errorText : emptyText"></el-empty>
    <el-button v-if="error" type="primary" size="small" @click="$emit('retry')">重试</el-button>
  </div>
</template>

<script>
export default {
  name: 'RequestState',
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    error: {
      type: Boolean,
      default: false
    },
    empty: {
      type: Boolean,
      default: false
    },
    emptyText: {
      type: String,
      default: '暂无数据'
    },
    errorText: {
      type: String,
      default: '加载失败，请重试'
    }
  }
}
</script>

<style scoped>
.request-state {
  padding: 12px 0 20px;
  text-align: center;
}
</style>
