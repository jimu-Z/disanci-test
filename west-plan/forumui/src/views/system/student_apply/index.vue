<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="学生姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-input
          v-model="queryParams.gender"
          placeholder="请输入性别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="学历" prop="education">
        <el-input
          v-model="queryParams.education"
          placeholder="请输入学历"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学位" prop="degree">
        <el-input
          v-model="queryParams.degree"
          placeholder="请输入学位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="学院" prop="college">
        <el-input
          v-model="queryParams.college"
          placeholder="请输入学院"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input
          v-model="queryParams.major"
          placeholder="请输入专业"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学号" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="籍贯" prop="nativePlace">
        <el-input
          v-model="queryParams.nativePlace"
          placeholder="请输入籍贯"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="家庭所在地" prop="familyLocation">
        <el-input
          v-model="queryParams.familyLocation"
          placeholder="请输入家庭所在地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="是否有教师资格证" prop="hasTeacherCert">
        <el-input
          v-model="queryParams.hasTeacherCert"
          placeholder="请输入是否有教师资格证"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="是否有学生干部经历" prop="hasCadreExp">
        <el-input
          v-model="queryParams.hasCadreExp"
          placeholder="请输入是否有学生干部经历"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="服务意向" prop="intention1">
        <el-input
          v-model="queryParams.intention1"
          placeholder="请输入服务意向"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="是否服从岗位调剂" prop="acceptAdjust">
        <el-input
          v-model="queryParams.acceptAdjust"
          placeholder="请输入是否服从岗位调剂"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:student_apply:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:student_apply:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:student_apply:remove']"
        >删除</el-button>
      </el-col>
       <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:student_apply:Import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:student_apply:export']"
        >导出</el-button>
      </el-col>


      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="student_applyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表主键，自增序号" align="center" prop="id" />
      <el-table-column label="申请序号" align="center" prop="applyNo" />
      <el-table-column label="学生姓名" align="center" prop="userName" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="身份证号" align="center" prop="idCard" />
      <el-table-column label="出生年月" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="民族" align="center" prop="nation" />
      <el-table-column label="政治面貌" align="center" prop="politicalStatus" />
      <el-table-column label="学历" align="center" prop="education" />
      <el-table-column label="学位" align="center" prop="degree" />
      <el-table-column label="学校所在省" align="center" prop="schoolProvince" />
      <el-table-column label="学校" align="center" prop="schoolName" />
      <el-table-column label="院" align="center" prop="college" />
      <el-table-column label="专业" align="center" prop="major" />
      <el-table-column label="专业类别" align="center" prop="majorType" />
      <el-table-column label="学号" align="center" prop="userId" />
      <el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="电子邮箱" align="center" prop="email" />
      <el-table-column label="籍贯" align="center" prop="nativePlace" />
      <el-table-column label="家庭所在地" align="center" prop="familyLocation" />
      <el-table-column label="详细地址" align="center" prop="detailedAddress" />
      <el-table-column label="是否有教师资格证" align="center" prop="hasTeacherCert" />
      <el-table-column label="学段学科" align="center" prop="teacherSubject" />
      <el-table-column label="是否有学生干部经历" align="center" prop="hasCadreExp" />
      <el-table-column label="学生干部经历说明" align="center" prop="cadreExpDesc" />
      <el-table-column label="在校期间获奖情况" align="center" prop="awards" />
      <el-table-column label="志愿服务经历" align="center" prop="volunteerExp" />
      <el-table-column label="其他需说明事项" align="center" prop="otherDesc" />
      <el-table-column label="服务意向1" align="center" prop="intention1" />
      <el-table-column label="服务意向1-项目类型" align="center" prop="intention1Project" />
      <el-table-column label="服务意向1-服务省" align="center" prop="intention1Province" />
      <el-table-column label="服务意向1-专项类别" align="center" prop="intention1Special" />
      <el-table-column label="服务意向2" align="center" prop="intention2" />
      <el-table-column label="服务意向2-项目类型" align="center" prop="intention2Project" />
      <el-table-column label="服务意向2-服务省" align="center" prop="intention2Province" />
      <el-table-column label="服务意向2-专项类别" align="center" prop="intention2Special" />
      <el-table-column label="服务意向3" align="center" prop="intention3" />
      <el-table-column label="服务意向3-项目类型" align="center" prop="intention3Project" />
      <el-table-column label="服务意向3-服务省" align="center" prop="intention3Province" />
      <el-table-column label="服务意向3-专项类别" align="center" prop="intention3Special" />
      <el-table-column label="意向服务年限" align="center" prop="serviceYears" />
      <el-table-column label="是否服从岗位调剂" align="center" prop="acceptAdjust" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:student_apply:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:student_apply:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <request-state
      :loading="loading"
      :error="Boolean(loadError)"
      :empty="!loadError && student_applyList.length === 0"
      empty-text="暂无报名数据"
      error-text="报名数据加载失败，请重试"
      @retry="getList"
    />

    <pagination
      v-show="total>0 && !loadError"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学生服务申请记录（西部计划/三支一扶等）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="申请序号" prop="applyNo">
          <el-input v-model="form.applyNo" placeholder="请输入申请序号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="出生年月" prop="birthDate">
          <el-date-picker clearable
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="form.nation" placeholder="请输入民族" />
        </el-form-item>
        <el-form-item label="学历" prop="education">
          <el-input v-model="form.education" placeholder="请输入学历" />
        </el-form-item>
        <el-form-item label="学位" prop="degree">
          <el-input v-model="form.degree" placeholder="请输入学位" />
        </el-form-item>
        <el-form-item label="学校所在省" prop="schoolProvince">
          <el-input v-model="form.schoolProvince" placeholder="请输入学校所在省" />
        </el-form-item>
        <el-form-item label="学校" prop="schoolName">
          <el-input v-model="form.schoolName" placeholder="请输入学校" />
        </el-form-item>
        <el-form-item label="院" prop="college">
          <el-input v-model="form.college" placeholder="请输入院" />
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="学号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱" />
        </el-form-item>
        <el-form-item label="籍贯" prop="nativePlace">
          <el-input v-model="form.nativePlace" placeholder="请输入籍贯" />
        </el-form-item>
        <el-form-item label="家庭所在地" prop="familyLocation">
          <el-input v-model="form.familyLocation" placeholder="请输入家庭所在地" />
        </el-form-item>
        <el-form-item label="详细地址" prop="detailedAddress">
          <el-input v-model="form.detailedAddress" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="是否有教师资格证" prop="hasTeacherCert">
          <el-input v-model="form.hasTeacherCert" placeholder="请输入是否有教师资格证" />
        </el-form-item>
        <el-form-item label="学段学科" prop="teacherSubject">
          <el-input v-model="form.teacherSubject" placeholder="请输入学段学科" />
        </el-form-item>
        <el-form-item label="是否有学生干部经历" prop="hasCadreExp">
          <el-input v-model="form.hasCadreExp" placeholder="请输入是否有学生干部经历" />
        </el-form-item>
        <el-form-item label="学生干部经历说明" prop="cadreExpDesc">
          <el-input v-model="form.cadreExpDesc" placeholder="请输入学生干部经历说明" />
        </el-form-item>
        <el-form-item label="在校期间获奖情况" prop="awards">
          <el-input v-model="form.awards" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="志愿服务经历" prop="volunteerExp">
          <el-input v-model="form.volunteerExp" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="其他需说明事项" prop="otherDesc">
          <el-input v-model="form.otherDesc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务意向1" prop="intention1">
          <el-input v-model="form.intention1" placeholder="请输入服务意向1" />
        </el-form-item>
        <el-form-item label="服务意向1-项目类型" prop="intention1Project">
          <el-input v-model="form.intention1Project" placeholder="请输入服务意向1-项目类型" />
        </el-form-item>
        <el-form-item label="服务意向1-服务省" prop="intention1Province">
          <el-input v-model="form.intention1Province" placeholder="请输入服务意向1-服务省" />
        </el-form-item>
        <el-form-item label="服务意向1-专项类别" prop="intention1Special">
          <el-input v-model="form.intention1Special" placeholder="请输入服务意向1-专项类别" />
        </el-form-item>
        <el-form-item label="服务意向2" prop="intention2">
          <el-input v-model="form.intention2" placeholder="请输入服务意向2" />
        </el-form-item>
        <el-form-item label="服务意向2-项目类型" prop="intention2Project">
          <el-input v-model="form.intention2Project" placeholder="请输入服务意向2-项目类型" />
        </el-form-item>
        <el-form-item label="服务意向2-服务省" prop="intention2Province">
          <el-input v-model="form.intention2Province" placeholder="请输入服务意向2-服务省" />
        </el-form-item>
        <el-form-item label="服务意向2-专项类别" prop="intention2Special">
          <el-input v-model="form.intention2Special" placeholder="请输入服务意向2-专项类别" />
        </el-form-item>
        <el-form-item label="服务意向3" prop="intention3">
          <el-input v-model="form.intention3" placeholder="请输入服务意向3" />
        </el-form-item>
        <el-form-item label="服务意向3-项目类型" prop="intention3Project">
          <el-input v-model="form.intention3Project" placeholder="请输入服务意向3-项目类型" />
        </el-form-item>
        <el-form-item label="服务意向3-服务省" prop="intention3Province">
          <el-input v-model="form.intention3Province" placeholder="请输入服务意向3-服务省" />
        </el-form-item>
        <el-form-item label="服务意向3-专项类别" prop="intention3Special">
          <el-input v-model="form.intention3Special" placeholder="请输入服务意向3-专项类别" />
        </el-form-item>
        <el-form-item label="意向服务年限" prop="serviceYears">
          <el-input v-model="form.serviceYears" placeholder="请输入意向服务年限" />
        </el-form-item>
        <el-form-item label="是否服从岗位调剂" prop="acceptAdjust">
          <el-input v-model="form.acceptAdjust" placeholder="请输入是否服从岗位调剂" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

     <!-- 学生申请导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStudent_apply, getStudent_apply, delStudent_apply, addStudent_apply, updateStudent_apply } from "@/api/system/student_apply"
import { getToken } from "@/utils/auth"
import RequestState from "@/components/RequestState"
export default {
  name: "Student_apply",
  components: {
    RequestState
  },
  data() {
    return {
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
      // 学生报名表格数据
      student_applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学生申请导入参数
      upload: {
        // 是否显示弹出层（学生申请）
        open: false,
        // 弹出层标题（学生申请导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/student_apply/importData"
      },

     // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyNo: null,
        userName: null,
        gender: null,
        idCard: null,
        birthDate: null,
        nation: null,
        politicalStatus: null,
        education: null,
        degree: null,
        schoolProvince: null,
        schoolName: null,
        college: null,
        major: null,
        majorType: null,
        userId: null,
        phone: null,
        email: null,
        nativePlace: null,
        familyLocation: null,
        detailedAddress: null,
        hasTeacherCert: null,
        teacherSubject: null,
        hasCadreExp: null,
        cadreExpDesc: null,
        awards: null,
        volunteerExp: null,
        otherDesc: null,
        intention1: null,
        intention1Project: null,
        intention1Province: null,
        intention1Special: null,
        intention2: null,
        intention2Project: null,
        intention2Province: null,
        intention2Special: null,
        intention3: null,
        intention3Project: null,
        intention3Province: null,
        intention3Special: null,
        serviceYears: null,
        acceptAdjust: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "真实姓名不能为空", trigger: "blur" }
        ],
        idCard: [
          { required: true, message: "身份证号不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" }
        ],

        gender: [
          { required: true, message: "性别不能为空", trigger: "blur"}
        ]

      }
    }
  },
  created() {

    this.getList()
  },
  methods: {
    /** 查询学生报名列表 */
    async getList() {

      this.loading = true
      this.loadError = ''
      try {
        const response = await listStudent_apply(this.queryParams)
        this.student_applyList = response.rows
        this.total = response.total
      } catch (e) {
        this.student_applyList = []
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
        applyNo: null,
        userName: null,
        gender: null,
        idCard: null,
        birthDate: null,
        nation: null,
        politicalStatus: null,
        education: null,
        degree: null,
        schoolProvince: null,
        schoolName: null,
        college: null,
        major: null,
        majorType: null,
        userId: null,
        phone: null,
        email: null,
        nativePlace: null,
        familyLocation: null,
        detailedAddress: null,
        hasTeacherCert: null,
        teacherSubject: null,
        hasCadreExp: null,
        cadreExpDesc: null,
        awards: null,
        volunteerExp: null,
        otherDesc: null,
        intention1: null,
        intention1Project: null,
        intention1Province: null,
        intention1Special: null,
        intention2: null,
        intention2Project: null,
        intention2Province: null,
        intention2Special: null,
        intention3: null,
        intention3Project: null,
        intention3Province: null,
        intention3Special: null,
        serviceYears: null,
        acceptAdjust: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加学生报名"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStudent_apply(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改学生报名"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStudent_apply(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStudent_apply(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学生报名编号为"' + ids + '"的数据项？').then(function() {
        return delStudent_apply(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student_apply/export', {
        ...this.queryParams
      }, `student_apply_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入"
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/student_apply/importTemplate', {
      }, `student_apply_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true })
      this.getList()
    },
     // 提交上传文件
    submitFileForm() {
      const file = this.$refs.upload.uploadFiles
      if (!file || file.length === 0 || !file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx')) {
        this.$modal.msgError("请选择后缀为 “xls”或“xlsx”的文件。")
        return
      }
      this.$refs.upload.submit()
    }
  }
}
</script>
