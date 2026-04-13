<template>
  <div class="forum-post-edit-page container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2 class="page-title">编辑帖子</h2>
      <el-breadcrumb separator="/" class="page-breadcrumb">
        <el-breadcrumb-item><a href="/#/forum/index">论坛首页</a></el-breadcrumb-item>
        <el-breadcrumb-item><a :href="`/#/forum/post/detail/${postId}`">帖子详情</a></el-breadcrumb-item>
        <el-breadcrumb-item>编辑帖子</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <!-- 编辑表单 -->
    <el-card class="edit-form-card" shadow="hover">
      <el-form
        ref="editPostFormRef"
        :model="editPostForm"
        :rules="editPostRules"
        label-width="80px"
        label-position="left"
      >
        <!-- 板块选择 -->
        <el-form-item label="所属板块" prop="boardId">
          <el-select
            v-model="editPostForm.boardId"
            placeholder="请选择帖子所属板块"
            clearable
            style="width: 300px;"
          >
            <el-option
              v-for="board in boardList"
              :key="board.id"
              :label="board.boardName"
              :value="board.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <!-- 帖子标题 -->
        <el-form-item label="帖子标题" prop="postTitle">
          <el-input
            v-model="editPostForm.postTitle"
            placeholder="请输入帖子标题（2-50个字符）"
            maxlength="50"
            show-word-limit
            style="width: 100%;"
          ></el-input>
        </el-form-item>

        <!-- 帖子标签 -->
        <el-form-item label="帖子标签" prop="postTags">
          <el-input
            v-model="editPostForm.postTags"
            placeholder="请输入标签，多个标签用英文逗号分隔（如Vue2,Java,SpringBoot）"
            maxlength="100"
            show-word-limit
            style="width: 100%;"
            tooltip="标签用于帖子分类，方便他人搜索"
          ></el-input>
        </el-form-item>

        <!-- 富文本内容编辑 -->
        <el-form-item label="帖子内容" prop="postContent">
            <Editor
            v-model="editPostForm.postContent"
            :options="editorOptions"
            style="width: 100%;"
          ></Editor>

        </el-form-item>
  <!-- 附件上传 -->
        <el-form-item label="上传附件">
          <el-upload class="upload-image" :action="fileAction" accept="image/jpg, image/jpeg, image/png"
            :on-progress="imageOnUpload" :on-success="imageUploadSuccess" :on-error="imageUploadError"
            :headers="headers" :file-list="imagelist" :on-remove="handleRemove" list-type="picture-card">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
            <div slot="tip" class="el-upload__tip">
              只能上传jpg/jpeg/png文件
            </div>
          </el-upload>
          <div class="preview-container" v-if="imagelist.length > 0">
            <div class="preview-card" v-for="(img, index) in imageListShow" :key="index">
              <img :src="img" alt="图片预览" class="preview-img" />
              <div class="preview-mask">
                <el-button type="text" icon="el-icon-delete" class="delete-btn"
                  @click.stop="removeImage(index)"></el-button>
              </div>
              <!-- 其他信息/按钮 -->
            </div>
          </div>
        </el-form-item>

        <!-- 发布范围 -->
        <el-form-item label="发布范围" prop="isPublic">

          <el-radio-group v-model="editPostForm.isPublic"  clearable>
            <el-radio label='1' >公开（所有用户可看）</el-radio>
            <el-radio label='0'  >私密（仅自己可看）</el-radio>
          </el-radio-group>

        </el-form-item>
        <!-- 操作按钮 -->
        <el-form-item class="btn-group">
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">提交修改</el-button>
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="warning" @click="handleReset" :loading="submitLoading">重置表单</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
// 引入接口、工具函数
import { getForum_post, updateForum_post} from '@/api/system/forum_post';
import {enableList,listForum_board } from '@/api/system/forum_board';
import { formatTime } from '@/utils/utils';
import { mapGetters } from 'vuex';
// Vue2富文本编辑器
import QuillEditor from 'vue-quill-editor';
import 'quill/dist/quill.snow.css';
export default {
  name: 'ForumPostEdit',
  data() {
    return {
        imagelist: [],
      previewVisible: false, // 单图预览弹窗
      currentPreviewUrl: "", // 当前预览URL
      // 标记当前上传组件有无文件
      hasFile: false,
    fileAction: process.env.VUE_APP_BASE_API + '/system/forum_post/uploadImage', // 上传文件服务器地址
      filesAction: process.env.VUE_APP_BASE_API + '/common/uploads', // 上传文件服务器地址
      // 标记当前是不是在上传
      imageOnUploading: false,
      previews: {},
      // 路由传参的帖子ID
      postId: '',
      // 加载态
      pageLoading: true,
      submitLoading: false,
      // 板块列表（用于下拉选择）
      boardList: [],

      // 编辑表单数据
      editPostForm: {
        boardId: '', // 板块ID
        postTitle: '', // 帖子标题
        postTags: [], // 标签数组, // 帖子标签（逗号分隔）
        postContent: '', // 富文本内容
        attachIds: [], // 附件ID数组（传给后端）
        isPublic: "1" // 默认为公开
      },
      // 富文本编辑器配置（与发布页保持一致）
      editorOptions: {
        placeholder: '请编辑帖子内容，支持富文本格式（粗体、图片、链接等）...',

        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // 粗体、斜体、下划线、删除线
            ['blockquote', 'code-block'], // 块引用、代码块
            [{ list: 'ordered' }, { list: 'bullet' }], // 有序/无序列表
            [{ align: [] }], // 对齐方式
            ['link', 'image'], // 链接、图片
            ['clean'] // 清除格式
          ]
        }
      },
      // 表单校验规则
      editPostRules: {
        boardId: [
          { required: true, message: '请选择所属板块', trigger: 'change' }
        ],
        postTitle: [
          { required: true, message: '请输入帖子标题', trigger: 'blur' },
          { min: 2, max: 50, message: '标题长度在2-50个字符之间', trigger: 'blur' }
        ],
        postContent: [
          { required: true, message: '请输入帖子内容', trigger: 'blur' },
          { min: 10, message: '内容长度不能少于10个字符', trigger: 'blur' }
        ],
        postTags: [
          { max: 100, message: '标签总长度不能超过100个字符', trigger: 'blur' }
        ]
      }
    };
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
    // 初始化：获取路由参数、加载板块、加载帖子详情
    this.initPage();
  },
  methods: {
    parsePicture(pictureStr) {
      if (!pictureStr) return [];
      // 拆分逗号分隔的URL，过滤空字符串（避免末尾逗号导致的空元素）
      return pictureStr.split(",").filter(url => url.trim() !== "").map(url => { return process.env.VUE_APP_BASE_API + url });
    },
    // eslint-disable-next-line no-unused-vars
    imageOnUpload(event, file, fileList) {
      this.imageOnUploading = true
    },
    // eslint-disable-next-line no-unused-vars
    imageUploadSuccess(response, file, fileList) {

      // 如果response.code 不是200,抛出异常
      if (response.code !== 200) {
        this.$message.error(response.msg)
      } else {
        this.imageOnUploading = true
        this.imagelist.push(response.url)


      }
    },
    // eslint-disable-next-line no-unused-vars
    imageUploadError(err, file, fileList) {
      this.$message.error('图片上传错误，请刷新页面后重试!')
    },

    handleRemove(file, fileList) {
      // 文件移除时的处理逻辑，例如从fileList中移除文件等
      const index = this.imagelist.indexOf(file.url);
      //从imagelist中移除文件
      if (index !== -1) {
        this.imagelist.splice(index, 1);
      }
      this.$refs.upload.fileList = this.imagelist;
      // this.imagelist = fileList; // 更新fileList以保持与上传组件同步
      if (this.imagelist.length > 0) {
        this.hasFile = true
      } else {
        this.hasFile = false
        this.form.postAttach = null
      }



    },
    handleDialogClose() {
      // 清空文件列表+重置预览状态
      this.resetUploadState();

    },

    /**
     * 重置上传组件的所有状态（通用方法）
     */
    resetUploadState() {
      this.imagelist = []; // 核心：清空文件列表

      if (this.$refs.upload) {
        this.$refs.upload.clearFiles();
      }
    },
    /**
     * 移除指定索引的图片
     * @param {Number} index - 图片在imageList中的索引
     */
    removeImage(index) {
      // 删除数组中对应索引的图片

      this.imagelist = this.imagelist.filter((value, i) => i !== index);
      this.$message.info("已移除该图片");
    },
    /**
    * 打开单图预览弹窗
    * @param {String} imgUrl - 图片URL
    */
    openImagePreview(imgUrl) {

      this.currentPreviewUrl = imgUrl;
      this.previewVisible = true;
    },



    /**
     * 多图预览时切换当前预览图
     * @param {String} imgUrl - 图片URL
     */
    switchPreviewImage(imgUrl) {
      this.currentPreviewUrl = imgUrl;
    },
    /**
     * 页面初始化
     */
    async initPage() {
      // 1. 获取路由中的帖子ID（动态路由参数）
      this.postId = this.$route.params.id;
      // 校验帖子ID是否合法
      if (!this.postId || isNaN(Number(this.postId))) {
        this.$message.error('帖子ID不合法，无法编辑');
        this.pageLoading = false;
        return;
      }
      // 2. 并行加载板块列表和帖子详情（提升性能）
      try {

        const [boardRes, postRes] = await Promise.all([
          enableList(), // 获取启用的板块列表
          getForum_post(this.postId) // 获取帖子详情
        ]);
        // 加载板块列表
        this.boardList = boardRes.data || [];
        // 加载帖子详情并回显到表单
        this.setPostForm(postRes.data);
       // console.log('editPostForm', this.editPostForm);
      } catch (error) {
        console.error('页面初始化失败：', error);
        this.$message.error(error.msg || '获取帖子信息失败，无法编辑');
      } finally {
        this.pageLoading = false;
      }
    },

    /**
     * 回显帖子数据到表单
     * @param {Object} post - 帖子详情数据
     */
    setPostForm(post) {
      //console.log('post', post);
      if (!post) return;
      // 基础数据回显
      this.editPostForm = {
        boardId: post.boardId || '',
        postTitle: post.postTitle || '',
        postTags: post.postTags || '', // 直接回显逗号分隔的标签
        postContent: post.postContent || '' ,// 富文本内容直接回显（保留格式）
        isPublic: post.isPublic+"" ,
        postAttach: post.postAttach || []

      };
       this.imagelist =  this.editPostForm.postAttach.split(',');

      // 基础权限校验：非帖子作者给出提示（后端需做最终权限校验）
      if (post.userId !== this.id) {
        this.$message.warning('你并非该帖子作者，修改后可能无法提交（仅作者可编辑）');
      }
    },

    /**
     * 提交修改
     */
    async handleSubmit() {
      // 1. 表单前置校验
      const formRef = this.$refs.editPostFormRef;
      try {
        await formRef.validate();
      } catch (error) {
        this.$message.error('表单校验失败，请检查填写内容');
        return;
      }

      // 2. 提交数据
      this.submitLoading = true;
      try {
        //将标签转换为数组
        this.editPostForm.postTags = this.editPostForm.postTags.split(',');


        // 构造提交参数（包含帖子ID，后端根据ID更新）
        this.editPostForm.postAttach = this.imagelist.join(',');
        const submitData = {
          id: this.postId, // 帖子主键ID（必传）
          ...this.editPostForm
        };

        // 调用帖子修改接口
        await updateForum_post(submitData);
        this.$message.success('帖子修改成功！');
        // 跳转回帖子详情页
        this.$router.push({ path: `/system/forum/post/detail/${this.postId}` });
      } catch (error) {
        console.error('帖子修改失败：', error);
        this.$message.error(error.msg || '帖子修改失败，请稍后重试');
      } finally {
        this.submitLoading = false;
      }
    },

    /**
     * 取消编辑，返回上一页
     */
    handleCancel() {
      // 若有未保存修改，给出确认提示
      if (this.checkFormChange()) {
        this.$confirm('你有未保存的修改，确定要取消编辑吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.back();
        }).catch(() => {});
      } else {
        this.$router.back();
      }
    },

    /**
     * 重置表单
     */
    handleReset() {
      this.$refs.editPostFormRef.resetFields();
      // 重置后重新回显原始数据（避免重置为空）
      this.initPage();
      this.$message.info('表单已重置为原始内容');
    },

    /**
     * 检查表单是否有修改（用于取消时的提示）
     * @returns {Boolean} 是否有修改
     */
    checkFormChange() {
      // 简单对比：可根据实际需求扩展更细致的对比
      const originalPost = this.$options.data().editPostForm;
      return JSON.stringify(this.editPostForm) !== JSON.stringify(originalPost);
    },

    // 时间格式化工具函数
    formatTime
  }
};
</script>

<style scoped lang="scss">
// 引入论坛全局样式变量（与首页、发布页保持风格统一）
$primary-color: #165DFF;
$secondary-color: #667085;
$bg-color: #F5F7FA;
$card-bg: #FFFFFF;
$radius: 8px;
$shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.04);
$shadow-hover: 0 4px 16px 0 rgba(0, 0, 0, 0.08);
$spacing: 20px;

// 页面全局样式
.forum-post-edit-page {
  width: 100%;
  min-height: calc(100vh - 120px);
  background-color: $bg-color;
  padding: $spacing 0;
  font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
}

// 容器居中（与论坛首页统一宽度）
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 16px;
}

// 页面标题+面包屑
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: $spacing;

  .page-title {
    font-size: 20px;
    font-weight: 600;
    color: #333;
    margin: 0;
  }

  .page-breadcrumb {
    color: $secondary-color;
    a {
      color: $secondary-color;
      &:hover {
        color: $primary-color;
      }
    }
  }
}

// 编辑表单卡片
.edit-form-card {
  background: $card-bg;
  border-radius: $radius;
  box-shadow: $shadow;
  padding: 30px;

  // 富文本编辑器样式
  .post-editor {
    width: 100%;
    ::v-deep .ql-editor {
      min-height: 400px; // 编辑区最小高度，比发布页稍高
      line-height: 1.8;
      font-size: 14px;
    }
    ::v-deep .ql-toolbar {
      border: 1px solid #e5e7eb;
      border-bottom: none;
      border-radius: $radius $radius 0 0;
    }
    ::v-deep .ql-container {
      border: 1px solid #e5e7eb;
      border-radius: 0 0 $radius $radius;
    }
  }

  // 按钮组
  .btn-group {
    margin-top: 20px;
    text-align: center;
    .el-button {
      margin: 0 8px;
      padding: 8px 24px;
    }
  }
}

// 响应式适配（平板/手机端）
@media (max-width: 992px) {
  .edit-form-card {
    padding: 20px 16px;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .post-editor {
    ::v-deep .ql-editor {
      min-height: 300px !important;
    }
  }
}

@media (max-width: 768px) {
  .forum-post-edit-page {
    padding: 16px 0;
  }

  .btn-group {
    .el-button {
      margin: 0 4px 8px 4px;
      width: 100%;
    }
  }
}
</style>
