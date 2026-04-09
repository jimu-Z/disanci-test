<template>
  <div class="app-container">


      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

         <el-form-item label="类别"  v-show="false" prop="category">
             <el-input v-model="form.category" placeholder="请输入标题" />
         </el-form-item>

        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :editor-options="editorOptions" :min-height="192"/>
        </el-form-item>
        <el-form-item label="图片" prop="picture">

            <el-upload
              class="upload-image"
              :action="fileAction"
              accept="image/jpg, image/jpeg, image/png"
              :on-progress="imageOnUpload"
              :on-success="imageUploadSuccess"
              :on-error="imageUploadError"
              :headers="headers"
              :file-list="imagelist"
              :on-remove="handleRemove"


      list-type="picture-card"
            >
              <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
              </el-button>
               <div slot="tip" class="el-upload__tip">
                只能上传jpg/jpeg/png文件
              </div>
          </el-upload>
        </el-form-item>
       <div class="preview-container" v-if="imagelist.length > 0">
  <div
    class="preview-card"
    v-for="(img, index) in imageListShow"
    :key="index"
  >
    <img :src="img" alt="图片预览" class="preview-img"  />
    <div class="preview-mask">
          <el-button
            type="text"
            icon="el-icon-delete"
            class="delete-btn"
            @click.stop="removeImage(index)"
          ></el-button>
     </div>
    <!-- 其他信息/按钮 -->
  </div>
      </div>


        <el-form-item label="是否公开" prop="isPublic">
         <el-select v-model="form.isPublic" placeholder="请选择是否公开">
                <el-option v-for="dict in dict.type.sys_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>

        </el-form-item>
        </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">重置</el-button>
      </div>

     <!-- 大图预览弹窗 -->
    <el-dialog
      :visible.sync="previewVisible"
      title="图片预览"
      width="80%"
      append-to-body
    >
      <img
        :src="currentPreviewUrl"
        alt="大图预览"
        style="width: 100%; max-height: 80vh; object-fit: contain"
      />
    </el-dialog>

    <!-- 多图预览弹窗（可选：查看一行的所有图片） -->
    <el-dialog
      v-model="multiPreviewVisible"
      title="所有图片"
      width="90%"
      append-to-body
    >
      <div class="multi-image-preview">
        <img
          v-for="(imgUrl, idx) in multiPreviewImages"
          :key="idx"
          :src="imgUrl"
          alt="多图预览"
          class="multi-image-item"
          @click="switchPreviewImage(imgUrl)"
        />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAlumni_feeling, getAlumni_feeling, delAlumni_feeling, addAlumni_feeling, updateAlumni_feeling } from "@/api/system/alumni_feeling"
import { uploadImage  } from "@/api/system/uploadImage"
import xss from 'xss'
import { getToken } from '@/utils/auth'
import { computed } from "vue";
export default {
  name: "Alumni_feeling",
  dicts: [ 'sys_yes_no'],
  data() {
    return {
       headers: {
        Authorization: 'Bearer ' + getToken(),
      },
      //编辑器配置，防止恶意上传
      editorOptions: {
        modules: {
          toolbar: {
            container: [  ['bold', 'italic', 'underline'],
                  ['image'], // 只允许图片
               ['clean']], // 只保留需要的功能
            handlers: {
              // 关键：重写图片上传
              image: this.handleImageUpload
            }
          }
        }
        },
            // 编辑模式判断
      isEdit: false,
      feelingId: null,
      imagelist:[],
       previewVisible: false, // 单图预览弹窗
      currentPreviewUrl: "", // 当前预览URL
      multiPreviewVisible: false, // 多图预览弹窗
      multiPreviewImages: [] ,// 多图预览列表
      paraCategory:'工作感悟',
      // 标记当前上传组件有无文件
      hasFile: false,
      fileAction: process.env.VUE_APP_BASE_API + '/system/alumni_feeling/uploadImage', // 上传文件服务器地址
      filesAction: process.env.VUE_APP_BASE_API + '/common/uploads', // 上传文件服务器地址
      // 标记当前是不是在上传
      imageOnUploading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 校友工作感悟表格数据
      alumni_feelingList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,

      previews: {},
      resizeHandler: null,




      // 表单参数
      form: {},
      // 表单校验
      rules: {


        title: [
          { required: true, message: "标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "内容不能为空", trigger: "blur" }
        ],

        isPublic: [
          { required: true, message: "是否公开不能为空", trigger: "blur" }
        ],
      }
    }
  },

  created() {
   this.feelingId = this.$route.params.id;
    this.isEdit = !!this.feelingId;

    // 编辑模式回显数据
    if (this.isEdit) {
      this.getFeeling(this.feelingId);
    }


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
  methods: {  // ====================
    // 安全图片上传（防漏洞）
    // ====================
    async handleImageUpload() {
      const input = document.createElement('input')
      input.type = 'file'
      input.accept = 'image/png,image/jpeg,image/jpg,image/gif' // 仅允许图片

      input.onchange = async (e) => {
        const file = e.target.files[0]
        if (!file) return

        // ============== 前端层层校验（防漏洞核心） ==============
        try {
          // 1. 文件类型校验
          const allowTypes = ['image/png', 'image/jpeg', 'image/jpg', 'image/gif']
          if (!allowTypes.includes(file.type)) {
            this.$message.error('仅支持 PNG/JPG/GIF 格式')
            return
          }

          // 2. 文件大小限制（4MB）
          const maxSize = 4* 1024 * 1024
          if (file.size > maxSize) {
            this.$message.error('图片不能超过 4MB')
            return
          }

          // 3. 真实图片校验（防止改后缀上传木马）
          await this.validateRealImage(file)

          // 4. 上传到后端（后端必须再做一次校验）
          const formData = new FormData()
          formData.append('file', file)

          const res = await uploadImage(formData)
          const imageUrl = res.url // 后端返回可访问URL

          // 插入图片到编辑器
          const editor = this.$refs.editor.quill
          const range = editor.getSelection()
          editor.insertEmbed(range.index, 'image', imageUrl)
        } catch (err) {
          this.$message.error('图片不安全或上传失败')
        }
      }
      input.click()
    },

    // 验证真实图片（防篡改后缀）
    validateRealImage(file) {
      return new Promise((resolve, reject) => {
        const img = new Image()
        img.onload = () => resolve(true)
        img.onerror = () => reject(false)
        img.src = URL.createObjectURL(file)
      })
    },

     parsePicture(pictureStr) {
      if (!pictureStr) return [];
      // 拆分逗号分隔的URL，过滤空字符串（避免末尾逗号导致的空元素）
      return pictureStr.split(",").filter(url => url.trim() !== "").map(url => { return process.env.VUE_APP_BASE_API + url});
    },
        // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,

        title: null,
        content: null,
        picture: null,
        vedio: null,

        isPublic: null,
        createTime: null,
        updateTime: null,
        showOrder: null
      }
      this.resetForm("form")
    },


    /** 修改按钮操作 */
    getFeeling(id) {

      getAlumni_feeling(id).then(response => {
        this.form = response.data
        this.imagelist=response.data.picture.split(',');
        this.open = true
        this.title = "修改校友工作感悟"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          this.form.userId=this.userId;
          this.form.category=this.paraCategory;
           this.form.likeCount =0
           this.viewCount=0

           this.form.picture=this.imagelist.join(',');

           this.form.content = this.form.content.replace(/data:image\/[^"']+/g, '') // 清除 base64
            // 过滤危险标签：script/iframe/onclick/javascript:void(0)
  this.form.content = xss(this.form.content, {
    whiteList: {
      p: [],
      br: [],
      span: ['style'],
      img: ['src', 'alt', 'width', 'height'],
      strong: [],
      em: [],
      u: [],
      ul: [],
      li: [],
      ol: []
    }
  })
          if (this.form.id != null) {
            updateAlumni_feeling(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAlumni_feeling(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
          this.imagelist=[]
        }
      })
         this.$router.go(-1)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除校友工作感悟编号为"' + ids + '"的数据项？').then(function() {
        return delAlumni_feeling(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
     this.$router.go(-1)
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
        this.imagelist.push(   response.url)


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
   //   console.log( this.imagelist)
      this.$refs.upload.fileList= this.imagelist;
     // this.imagelist = fileList; // 更新fileList以保持与上传组件同步
      if (imagelist.length > 0) {
        this.hasFile = true
      }else {
        this.hasFile = false
        this.form.picture = null
      }
      this.$router.go(-1)
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
     * 查看一行的所有图片
     * @param {String} pictureStr - 数据库存储的图片字符串
     */
    viewAllImages(pictureStr) {
      this.multiPreviewImages = this.parsePicture(pictureStr);
      if (this.multiPreviewImages.length === 0) {
        this.$message.info("该条数据暂无图片");
        return;
      }
      this.multiPreviewVisible = true;
      // 默认选中第一张
      this.currentPreviewUrl = this.multiPreviewImages[0];
    },

    /**
     * 多图预览时切换当前预览图
     * @param {String} imgUrl - 图片URL
     */
    switchPreviewImage(imgUrl) {
      this.currentPreviewUrl = imgUrl;
    }
  }
}
</script>

<style scoped>
.preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px; /* 预览卡片之间的间距 */
}
/* 悬浮蒙版层样式：默认隐藏，hover时显示 */
.preview-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* 半透明黑色蒙版 */
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0; /* 默认透明 */
  transition: opacity 0.2s ease; /* 渐变显示/隐藏 */
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
  transition: all 0.2s ease; /* 过渡动画，提升流畅性 */
  overflow: hidden; /* 隐藏蒙版超出部分 */
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

.empty-tip {
  color: #999;
  text-align: center;
  padding: 30px 0;
  border: 1px dashed #e6e6e6;
  border-radius: 8px;
}
.table-image-container {
  width: 95%;
  margin: 20px auto;
}

/* 表格内图片容器样式 */
.table-image-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  justify-content: center;
  padding: 5px 0;
}

/* 表格内单张图片样式 */
.table-image-item {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
  cursor: pointer;
  border: 1px solid #e6e6e6;
  transition: all 0.2s;
}

.table-image-item:hover {
  transform: scale(1.05);
  border-color: #409eff;
}
.multi-image-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 10px 0;
  max-height: 60vh;
  overflow-y: auto;
  justify-content: center;
}

.multi-image-item {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
  cursor: pointer;
  border: 2px solid transparent;
}

/* 选中的图片高亮 */
.multi-image-item:hover, .multi-image-item:active {
  border-color: #409eff;
}
/* 富文本编辑器图片自适应（不破坏布局、不变形） */
::v-deep .ql-editor img {
  max-width: 100% !important;
  width: 450px !important;
  height: auto !important;
  border-radius: 6px; /* 可选圆角 */
  margin: 12px 0;
}
</style>
