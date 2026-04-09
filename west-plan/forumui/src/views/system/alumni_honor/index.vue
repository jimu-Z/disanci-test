<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入学号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="荣誉名称" prop="honorName">
        <el-input v-model="queryParams.honorName" placeholder="请输入荣誉名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="荣誉级别" prop="honorLevel">
        <el-input v-model="queryParams.honorLevel" placeholder="请输入荣誉级别" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="颁发单位" prop="issueUnit">
        <el-input v-model="queryParams.issueUnit" placeholder="请输入颁发单位" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:alumni_honor:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:alumni_honor:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:alumni_honor:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:alumni_honor:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="alumni_honorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="帐号" align="center" prop="userId" />
      <el-table-column label="荣誉名称" align="center" prop="honorName" />
      <el-table-column label="荣誉级别" align="center" prop="honorLevel" />
      <el-table-column label="获得时间" align="center" prop="obtainTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.obtainTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="颁发单位" align="center" prop="issueUnit" />

      <el-table-column label="荣誉附件" align="center" prop="honorAttach">
        <template slot-scope="scope">
          <!-- 解析picture字段：多图拆分，单图直接使用 -->
          <div class="table-image-wrapper">
            <img v-for="(imgUrl, idx) in parsePicture(scope.row.honorAttach)" :key="idx" :src="imgUrl"
              @click="openImagePreview(imgUrl)" alt="图片" class="table-image-item" />
            <!-- 空图片提示 -->
            <span v-if="!scope.row.honorAttach || parsePicture(scope.row.honorAttach).length === 0"
              class="empty-image-tip">
              暂无图片
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:alumni_honor:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:alumni_honor:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改校友荣誉彰对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="荣誉名称" prop="honorName">
          <el-input v-model="form.honorName" placeholder="请输入荣誉名称" />
        </el-form-item>
        <el-form-item label="荣誉级别" prop="honorLevel">
          <el-input v-model="form.honorLevel" placeholder="请输入荣誉级别" />
        </el-form-item>
        <el-form-item label="获得时间" prop="obtainTime">
          <el-date-picker clearable v-model="form.obtainTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择获得时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="颁发单位" prop="issueUnit">
          <el-input v-model="form.issueUnit" placeholder="请输入颁发单位" />
        </el-form-item>
        <el-form-item label="荣誉附件" prop="honorAttach">
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

        </el-form-item>
      </el-form>

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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 大图预览弹窗 -->
    <el-dialog :visible.sync="previewVisible" title="图片预览" width="80%" append-to-body>
      <img :src="currentPreviewUrl" alt="大图预览" style="width: 100%; max-height: 80vh; object-fit: contain" />
    </el-dialog>
  </div>
</template>

<script>
import { listAlumni_honor, getAlumni_honor, delAlumni_honor, addAlumni_honor, updateAlumni_honor } from "@/api/system/alumni_honor"
import { getToken } from '@/utils/auth'
import { computed } from "vue";
export default {
  name: "Alumni_honor",
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
      // 校友荣誉彰表格数据
      alumni_honorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        honorName: null,
        honorLevel: null,
        obtainTime: null,
        issueUnit: null,
        honorAttach: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {

        honorName: [
          { required: true, message: "荣誉名称不能为空", trigger: "blur" }
        ],
        honorLevel: [
          { required: true, message: "荣誉级别不能为空", trigger: "blur" }
        ],
        obtainTime: [
          { required: true, message: "获得时间不能为空", trigger: "blur" }
        ],
        issueUnit: [
          { required: true, message: "颁发单位不能为空", trigger: "blur" }
        ],
      }
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
  created() {
    this.getList()
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
      console.log(this.imagelist)
      this.$refs.upload.fileList = this.imagelist;
      // this.imagelist = fileList; // 更新fileList以保持与上传组件同步
      if (imagelist.length > 0) {
        this.hasFile = true
      } else {
        this.hasFile = false
        this.form.honorAttach = null
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
    /** 查询校友荣誉彰列表 */
    getList() {
      this.loading = true
      listAlumni_honor(this.queryParams).then(response => {
        this.alumni_honorList = response.rows
        this.total = response.total
        this.loading = false
      })
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
        honorName: null,
        honorLevel: null,
        obtainTime: null,
        issueUnit: null,
        honorAttach: null,
        createTime: null,
        updateTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加校友荣誉彰"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAlumni_honor(id).then(response => {
        this.form = response.data
        this.imagelist = response.data.honorAttach.split(',');
        this.open = true
        this.title = "修改校友荣誉彰"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.userId = this.userId;
          this.form.honorAttach = this.imagelist.join(',');
          if (this.form.id != null) {
            updateAlumni_honor(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAlumni_honor(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除校友荣誉彰编号为"' + ids + '"的数据项？').then(function () {
        return delAlumni_honor(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/alumni_honor/export', {
        ...this.queryParams
      }, `alumni_honor_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style scoped>
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
.multi-image-item:hover,
.multi-image-item:active {
  border-color: #409eff;
}
</style>
