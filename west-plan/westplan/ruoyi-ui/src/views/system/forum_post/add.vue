<template>
  <div class="forum-container">
    <el-card class="forum-card" shadow="hover">
      <div class="flex items-center justify-between mb-6">
        <h3 class="m-0">{{ isEdit ? '修改帖子' : '发布帖子' }}</h3>
        <el-button type="primary" @click="handleSubmit" :loading="loading">
          {{ isEdit ? '保存修改' : '发布帖子' }}
        </el-button>
      </div>

      <!-- 帖子表单 -->
      <el-form ref="formRef" :model="form" label-width="100px" :rules="rules" label-position="top">
        <!-- 所属板块 -->
        <el-form-item label="所属板块" prop="boardId">
          <el-select v-model="form.boardId" placeholder="请选择发布板块" clearable style="width: 100%;">
            <el-option v-for="board in boardList" :key="board.id" :label="board.boardName"
              :value="board.id"></el-option>
          </el-select>
        </el-form-item>

        <!-- 帖子标题 -->
        <el-form-item label="帖子标题" prop="postTitle">
          <el-input v-model="form.postTitle" placeholder="请输入帖子标题（2-50个字）" maxlength="50" show-word-limit
            style="width: 100%;"></el-input>
        </el-form-item>

        <!-- 话题标签 -->
        <el-form-item label="话题标签">
          <el-input v-model="tagInput" placeholder="输入标签后按回车添加，最多5个" @keyup.enter="addTag"
            style="width: 100%;"></el-input>
          <div class="mt-2">
            <el-tag v-for="(tag, index) in form.postTags" :key="index" closable @close="removeTag(index)" type="info"
              size="small">
              {{ tag }}
            </el-tag>
          </div>
        </el-form-item>

        <!-- 富文本内容（Vue2适配quill-editor） -->
        <el-form-item label="帖子内容" prop="postContent">
          <Editor v-model="form.postContent" :options="editorOptions" style="width: 100%;"></Editor>
        </el-form-item>

        <!-- 附件上传 -->
        <el-form-item label="上传附件">
          <el-upload ref="upload" class="upload-image" :action="fileAction" accept="image/jpg, image/jpeg, image/png"
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
          <el-radio-group v-model="form.isPublic">
            <el-radio label="1">公开（所有用户可看）</el-radio>
            <el-radio label="0">私密（仅自己可看）</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

    </el-card>
    <!-- 大图预览弹窗 -->
    <el-dialog :visible.sync="previewVisible" title="图片预览" width="80%" append-to-body>
      <img :src="currentPreviewUrl" alt="大图预览" style="width: 100%; max-height: 80vh; object-fit: contain" />
    </el-dialog>
  </div>
</template>

<script>
import { getForum_post, addForum_post, updateForum_post } from '@/api/system/forum_post';
import { enableList, listForum_board } from '@/api/system/forum_board';
//import { mapGetters } from 'vuex';
import { getToken } from '@/utils/auth';
// Vue2富文本编辑器
import QuillEditor from 'vue-quill-editor';
import 'quill/dist/quill.snow.css';

export default {
  name: 'Add',
  components: {
    QuillEditor
  },
  data() {
    return {
      headers: {
        Authorization: 'Bearer ' + getToken(),
      },
      imagelist: [],
      previewVisible: false, // 单图预览弹窗
      currentPreviewUrl: "", // 当前预览URL
      // 标记当前上传组件有无文件
      hasFile: false,
      fileAction: process.env.VUE_APP_BASE_API + '/system/alumni_honor/uploadImage', // 上传文件服务器地址
      filesAction: process.env.VUE_APP_BASE_API + '/common/uploads', // 上传文件服务器地址
      // 标记当前是不是在上传
      imageOnUploading: false,
      previews: {},
      // 编辑模式判断
      isEdit: false,
      postId: null,
      // 加载状态
      loading: false,
      // 表单相关
      formRef: null,
      form: {
        boardId: null,
        postTitle: '',
        postTags: [], // 标签数组
        postContent: '',
        postAttach: '',
        attachIds: [], // 附件ID数组（传给后端）
        isPublic: '1' // 默认为公开
      },
      tagInput: '', // 标签输入框临时值
      rules: {
        boardId: [{ required: true, message: '请选择发布板块', trigger: 'change' }],
        postTitle: [
          { required: true, message: '请输入帖子标题', trigger: 'blur' },
          { min: 2, max: 50, message: '标题长度在2到50个字之间', trigger: 'blur' }
        ],
        postContent: [{ required: true, message: '请输入帖子内容', trigger: 'blur' }],
        isPublic: [{ required: true, message: '请选择发布范围', trigger: 'change' }]
      },
      // 板块列表
      boardList: [],
      // 富文本编辑器配置
      editorOptions: {
        placeholder: '请输入帖子内容，支持富文本编辑...',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ align: [] }],
            ['link', 'image'],
            ['clean']
          ]
        }
      },
      // 文件上传相关
      uploadUrl: process.env.BASE_API + '/system/file/upload',
      uploadHeaders: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList: []
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
    // 获取路由参数（修改模式传id）
    this.postId = this.$route.params.id;
    this.isEdit = !!this.postId;
    this.getBoardList();
    // 编辑模式回显数据
    if (this.isEdit) {
      this.getPostDetail();
    }
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
        this.imageOnUploading = false
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
    // 获取启用的板块列表
    async getBoardList() {
      try {
        const res = await enableList();
        this.boardList = res.data;
      } catch (error) {
        console.error('获取板块列表失败：', error);
        this.$message.error('获取板块列表失败');
      }
    },
    // 获取帖子详情（编辑模式）
    async getPostDetail() {
      try {
        const res = await getForum_post(this.postId);
        const data = res.data;
        // 表单回显
        this.form.postId = data.postId;
        this.form.boardId = data.boardId;
        this.form.postTitle = data.postTitle;
        this.form.postContent = data.postContent;
        this.form.isPublic = data.isPublic + '';
        this.imagelist = data.postAttach ? data.postAttach.split(',').filter(item => item) : [];
        // 标签回显（后端逗号分隔转数组）
        this.form.postTags = data.postTags ? data.postTags.split(',').filter(item => item) : [];
        // 附件暂不回显（如需回显需对接文件查询接口）
      } catch (error) {
        console.error('获取帖子详情失败：', error);
        this.$message.error('帖子不存在或已被删除');
        this.$router.back();
      }
    },
    // 添加标签
    addTag() {
      if (!this.tagInput.trim() || this.form.postTags.length >= 5) {
        if (this.form.postTags.length >= 5) {
          this.$message.warning('最多只能添加5个标签');
        }
        this.tagInput = '';
        return;
      }
      this.form.postTags.push(this.tagInput.trim());
      this.tagInput = '';
    },
    // 移除标签
    removeTag(index) {
      this.form.postTags.splice(index, 1);
    },
    // 附件上传前校验
    beforeFileUpload(file) {
      const maxSize = 50 * 1024 * 1024; // 50M
      if (file.size > maxSize) {
        this.$message.error('单文件大小不能超过50M');
        return false;
      }
      return true;
    },
    // 附件上传超出数量
    handleFileExceed() {
      this.$message.error('最多只能上传5个附件');
    },
    // 附件上传成功（后端返回fileId）
    handleFileSuccess(res, file) {
      if (res.code === 200) {
        this.form.attachIds.push(res.data.fileId);
        this.fileList.push({
          name: file.name,
          url: res.data.url,
          fileId: res.data.fileId
        });
      } else {
        this.$message.error('附件上传失败：' + res.msg);
      }
    },
    // 附件移除
    handleFileRemove(file) {
      const index = this.form.attachIds.findIndex(id => id === file.fileId);
      if (index > -1) {
        this.form.attachIds.splice(index, 1);
      }
    },
    // 提交表单
    async handleSubmit() {
      if (!this.$refs.formRef) return;
      // 表单校验
      this.$refs.formRef.validate(async (valid) => {
        if (!valid) return;

        this.loading = true;
          this.form.postAttach = this.imagelist.join(',');
        try {
          if (this.isEdit) {
            // 修改帖子
            await updateForum_post(this.form);
            this.$message.success('帖子修改成功，重新进入待审核');
          } else {
            // 发布帖子
            await addForum_post(this.form);
            this.$message.success('帖子发布成功，等待管理员审核');
          }
          // 跳转到论坛首页
          this.$router.push({ path: '/system/forum' });
        } catch (error) {
          console.error('提交失败：', error);
        } finally {
          this.loading = false;
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
@import "../../../assets/css/forum.css";

::v-deep .el-upload-list {
  margin-top: 10px;
}

::v-deep .ql-toolbar {
  border-bottom: 1px solid #e6e6e6;
}

.preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  /* 预览卡片之间的间距 */
}

/* 悬浮蒙版层样式：默认隐藏，hover时显示 */
.preview-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  /* 半透明黑色蒙版 */
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  /* 默认透明 */
  transition: opacity 0.2s ease;
  /* 渐变显示/隐藏 */
  border-radius: 8px;
  z-index: 5;
}

/* hover时显示蒙版 */
.preview-card:hover .preview-mask {
  opacity: 1;
}

/* 预览卡片核心样式 */
.preview-card {
  width: 180px;
  border: 1px solid #e6e6e6;
  border-radius: 8px;
  padding: 10px;
  position: relative;
  background: #fafafa;
  cursor: pointer;
  transition: all 0.2s ease;
  /* 过渡动画，提升流畅性 */
  overflow: hidden;
  /* 隐藏蒙版超出部分 */
}

/* 卡片hover效果 */
.preview-card:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-color: #409eff;
}

.preview-img {
  width: 100%;
  height: 120px;
  object-fit: cover;
  border-radius: 4px;
  margin-bottom: 8px;
}

/* 删除按钮样式 */
.delete-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #ff4d4f;
  color: white;
  font-size: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
  transition: transform 0.2s ease;
}

/* 删除按钮hover放大效果 */
.delete-btn:hover {
  transform: scale(1.1);
  background: #ff7875;
  color: white;
}
</style>
