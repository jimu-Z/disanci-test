<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题库名称" prop="bankName">
        <el-input v-model="queryParams.bankName" placeholder="请输入题库名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="所属批次" prop="batch">
        <el-input v-model="queryParams.batch" placeholder="请输入所属批次" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:question_bank:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:question_bank:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:question_bank:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['system:question_bank:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="question_bankList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="题库名称" align="center" prop="bankName" />
      <el-table-column label="所属批次" align="center" prop="batch" />
      <el-table-column label="题目内容" align="center" prop="questionContent" />
      <el-table-column label="正确答案" align="center" prop="correctAnswer" />
      <el-table-column label="开放开始时间" align="center" prop="openStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.openStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开放结束时间" align="center" prop="openEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.openEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:question_bank:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:question_bank:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <request-state
      :loading="loading"
      :error="Boolean(loadError)"
      :empty="!loadError && question_bankList.length === 0"
      empty-text="暂无题库数据"
      error-text="题库数据加载失败，请重试"
      @retry="getList"
    />

    <pagination v-show="total > 0 && !loadError" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改题库信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题库名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入题库名称" />
        </el-form-item>
        <el-form-item label="所属批次" prop="batch">
          <el-input v-model="form.batch" placeholder="请输入所属批次" />
        </el-form-item>
        <el-form-item label="题目内容">
          <editor v-model="form.questionContent" :min-height="192" :editor-options="editorOptions" />
        </el-form-item>
        <el-form-item label="正确答案" prop="correctAnswer">
          <el-input v-model="form.correctAnswer" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="开放开始时间" prop="openStartTime">
          <el-date-picker clearable v-model="form.openStartTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择开放开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="开放结束时间" prop="openEndTime">
          <el-date-picker clearable v-model="form.openEndTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择开放结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listQuestion_bank, getQuestion_bank, delQuestion_bank, addQuestion_bank, updateQuestion_bank } from "@/api/system/question_bank"
import { uploadImage } from "@/api/system/uploadImage"
import RequestState from "@/components/RequestState"
import xss from 'xss'
export default {
  name: "Question_bank",
  components: {
    RequestState
  },
  data() {
    return {
      //编辑器配置，防止恶意上传
      editorOptions: {
        modules: {
          toolbar: {
            container: [['bold', 'italic', 'underline'],
            ['image'], // 只允许图片
            ['clean']], // 只保留需要的功能
            handlers: {
              // 关键：重写图片上传
              image: this.handleImageUpload
            }
          }
        }
      },

      // 遮罩层
      loading: true,
      loadError: '',
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
      // 题库信息表格数据
      question_bankList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bankName: null,
        batch: null,
        questionContent: null,
        correctAnswer: null,
        openStartTime: null,
        openEndTime: null,
        status: null,
        sort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bankName: [
          { required: true, message: "题库名称不能为空", trigger: "blur" }
        ],
        batch: [
          { required: true, message: "所属批次不能为空", trigger: "blur" }
        ],
        questionContent: [
          { required: true, message: "题目内容不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        sort: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
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
          const maxSize = 4 * 1024 * 1024
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

    /** 查询题库信息列表 */
    async getList() {
      this.loading = true
      this.loadError = ''
      try {
        const response = await listQuestion_bank(this.queryParams)
        this.question_bankList = response.rows
        this.total = response.total
      } catch (e) {
        this.question_bankList = []
        this.total = 0
        this.loadError = e && e.message ? e.message : 'load_failed'
      } finally {
        this.loading = false
      }
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
        bankName: null,
        batch: null,
        questionContent: null,
        correctAnswer: null,
        openStartTime: null,
        openEndTime: null,
        status: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加题库信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getQuestion_bank(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改题库信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.questionContent = this.form.questionContent.replace(/data:image\/[^"']+/g, '') // 清除 base64
            // 过滤危险标签：script/iframe/onclick/javascript:void(0)
            this.form.questionContent = xss(this.form.questionContent, {
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
            updateQuestion_bank(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addQuestion_bank(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除题库信息编号为"' + ids + '"的数据项？').then(function () {
        return delQuestion_bank(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/question_bank/export', {
        ...this.queryParams
      }, `question_bank_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
