<template>
  <div class="comment-item mb-4 p-4 border rounded-lg">
    <div class="flex items-start gap-3">
      <el-avatar :src="comment.avatar || ''" :alt="comment.userName"></el-avatar>
      <div class="flex-1">
        <div class="flex items-center justify-between">
          <h4 class="m-0 font-bold">{{ comment.userName }}</h4>
          <span class="text-sm text-gray-500">{{ formatTime(comment.createTime) }}</span>
        </div>
        <div class="mt-2">{{ comment.commentContent }}</div>
        <div class="mt-2 flex gap-3 text-sm">
          <span class="cursor-pointer text-gray-500" @click="$emit('like', comment.id)">
            <i class="el-icon-like"></i> 点赞 ({{ comment.likeCount }})
          </span>
          <span class="cursor-pointer text-gray-500" @click="$emit('reply', comment.id, comment.userName)">
            <i class="el-icon-chat-dot-round"></i> 回复
          </span>
          <span
            class="cursor-pointer text-gray-500"
            v-if="isLogin && (comment.userId === userId || hasPermi('system:comment:remove'))"
            @click="$emit('delete', comment.id)"
          >
            <i class="el-icon-delete"></i> 删除
          </span>
        </div>

        <!-- 子评论递归展示 -->
        <div class="comment-tree mt-3" v-if="comment.children && comment.children.length > 0">
          <comment-item
            v-for="child in comment.children"
            :key="child.id"
            :comment="child"
            :is-login="isLogin"
            :user-id="userId"
            @like="$emit('like', $event)"
            @reply="$emit('reply', $event)"
            @delete="$emit('delete', $event)"
          ></comment-item>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { formatTime } from '@/utils/utils';

export default {
  name: 'CommentItem', // 递归组件必须指定name
  props: {
    comment: {
      type: Object,
      required: true,
      default: () => ({})
    },
    isLogin: {
      type: Boolean,
      required: true,
      default: false
    },
    userId: {
      type: [Number, String],
      required: true,
      default: 0
    }
  },
  methods: {
    formatTime,
    hasPermi(permission) {
      return this.$auth.hasPermi(permission);
    }
  }
};
</script>

<style scoped lang="scss">
::v-deep .el-avatar {
  width: 40px;
  height: 40px;
}
</style>
