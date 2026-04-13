<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input
          v-model="queryParams.idCard"
          placeholder="请输入身份证号"
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
      <el-form-item label="民族" prop="nation">
        <el-input
          v-model="queryParams.nation"
          placeholder="请输入民族"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出生年月" prop="birthDate">
        <el-date-picker clearable
          v-model="queryParams.birthDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出生年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="学院/系名称" prop="college">
        <el-input
          v-model="queryParams.college"
          placeholder="请输入学院/系名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专业全称" prop="major">
        <el-input
          v-model="queryParams.major"
          placeholder="请输入专业全称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学校建行卡号" prop="schoolCcbCard">
        <el-input
          v-model="queryParams.schoolCcbCard"
          placeholder="请输入学校建行卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="籍贯省(区、市)" prop="nativeProvince">
        <el-input
          v-model="queryParams.nativeProvince"
          placeholder="请输入籍贯省(区、市)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="籍贯市(地、州)" prop="nativeCity">
        <el-input
          v-model="queryParams.nativeCity"
          placeholder="请输入籍贯市(地、州)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="户籍地省(区、市)" prop="hukouProvince">
        <el-input
          v-model="queryParams.hukouProvince"
          placeholder="请输入户籍地省(区、市)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="户籍地市(地、州)" prop="hukouCity">
        <el-input
          v-model="queryParams.hukouCity"
          placeholder="请输入户籍地市(地、州)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="家庭住址" prop="familyAddress">
        <el-input
          v-model="queryParams.familyAddress"
          placeholder="请输入家庭住址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身高" prop="height">
        <el-input
          v-model="queryParams.height"
          placeholder="请输入身高"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="志愿者polo衫尺码" prop="poloSize">
        <el-input
          v-model="queryParams.poloSize"
          placeholder="请输入志愿者polo衫尺码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否参加出征仪式" prop="attendCeremony">
        <el-input
          v-model="queryParams.attendCeremony"
          placeholder="请输入是否参加出征仪式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分配省份" prop="assignProvince">
        <el-input
          v-model="queryParams.assignProvince"
          placeholder="请输入分配省份"
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
      <el-form-item label="学段学科" prop="teacherSubject">
        <el-input
          v-model="queryParams.teacherSubject"
          placeholder="请输入学段学科"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电子邮箱" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入电子邮箱"
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
      <el-form-item label="服务省份" prop="serviceProvince">
        <el-input
          v-model="queryParams.serviceProvince"
          placeholder="请输入服务省份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务县" prop="serviceCounty">
        <el-input
          v-model="queryParams.serviceCounty"
          placeholder="请输入服务县"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务单位" prop="serviceUnit">
        <el-input
          v-model="queryParams.serviceUnit"
          placeholder="请输入服务单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职位" prop="position">
        <el-input
          v-model="queryParams.position"
          placeholder="请输入职位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位编号" prop="postCode">
        <el-input
          v-model="queryParams.postCode"
          placeholder="请输入岗位编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务单位地址" prop="serviceAddress">
        <el-input
          v-model="queryParams.serviceAddress"
          placeholder="请输入服务单位地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务单位联系人" prop="contactPerson">
        <el-input
          v-model="queryParams.contactPerson"
          placeholder="请输入服务单位联系人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位联系电话" prop="unitPhone">
        <el-input
          v-model="queryParams.unitPhone"
          placeholder="请输入单位联系电话"
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
          v-hasPermi="['system:student_admission:add']"
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
          v-hasPermi="['system:student_admission:edit']"
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
          v-hasPermi="['system:student_admission:remove']"
        >删除</el-button>
      </el-col>
       <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:student_admission:Import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:student_admission:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="student_admissionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表主键，自增序号" align="center" prop="id" />
      <el-table-column label="学号" align="center" prop="userId" />
      <el-table-column label="学生姓名" align="center" prop="userName" />
      <el-table-column label="身份证号" align="center" prop="idCard" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="民族" align="center" prop="nation" />
      <el-table-column label="出生年月" align="center" prop="birthDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="政治面貌" align="center" prop="politicalStatus" />
      <el-table-column label="学院/系名称" align="center" prop="college" />
      <el-table-column label="专业全称" align="center" prop="major" />
      <el-table-column label="学校建行卡号" align="center" prop="schoolCcbCard" />
      <el-table-column label="籍贯省(区、市)" align="center" prop="nativeProvince" />
      <el-table-column label="籍贯市(地、州)" align="center" prop="nativeCity" />
      <el-table-column label="户籍地省(区、市)" align="center" prop="hukouProvince" />
      <el-table-column label="户籍地市(地、州)" align="center" prop="hukouCity" />
      <el-table-column label="家庭住址" align="center" prop="familyAddress" />
      <el-table-column label="身高" align="center" prop="height" />
      <el-table-column label="志愿者polo衫尺码" align="center" prop="poloSize" />
      <el-table-column label="是否参加出征仪式" align="center" prop="attendCeremony" />
      <el-table-column label="分配省份" align="center" prop="assignProvince" />
      <el-table-column label="是否有教师资格证" align="center" prop="hasTeacherCert" />
      <el-table-column label="学段学科" align="center" prop="teacherSubject" />
      <el-table-column label="项目类型" align="center" prop="projectType" />
      <el-table-column label="专项类别" align="center" prop="specialType" />
      <el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="电子邮箱" align="center" prop="email" />
      <el-table-column label="是否有学生干部经历" align="center" prop="hasCadreExp" />
      <el-table-column label="服务省份" align="center" prop="serviceProvince" />
      <el-table-column label="服务县" align="center" prop="serviceCounty" />
      <el-table-column label="服务单位" align="center" prop="serviceUnit" />
      <el-table-column label="职位" align="center" prop="position" />
      <el-table-column label="岗位编号" align="center" prop="postCode" />
      <el-table-column label="服务单位地址" align="center" prop="serviceAddress" />
      <el-table-column label="服务单位联系人" align="center" prop="contactPerson" />
      <el-table-column label="单位联系电话" align="center" prop="unitPhone" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:student_admission:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:student_admission:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <request-state
      :loading="loading"
      :error="Boolean(loadError)"
      :empty="!loadError && student_admissionList.length === 0"
      empty-text="暂无录取数据"
      error-text="录取数据加载失败，请重试"
      @retry="getList"
    />

    <pagination
      v-show="total>0 && !loadError"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学生录取信息（西部计划/三支一扶等项目）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="民族" prop="nation">
          <el-input v-model="form.nation" placeholder="请输入民族" />
        </el-form-item>
        <el-form-item label="出生年月" prop="birthDate">
          <el-date-picker clearable
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学院/系名称" prop="college">
          <el-input v-model="form.college" placeholder="请输入学院/系名称" />
        </el-form-item>
        <el-form-item label="专业全称" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业全称" />
        </el-form-item>
        <el-form-item label="学校建行卡号" prop="schoolCcbCard">
          <el-input v-model="form.schoolCcbCard" placeholder="请输入学校建行卡号" />
        </el-form-item>
        <el-form-item label="籍贯省(区、市)" prop="nativeProvince">
          <el-input v-model="form.nativeProvince" placeholder="请输入籍贯省(区、市)" />
        </el-form-item>
        <el-form-item label="籍贯市(地、州)" prop="nativeCity">
          <el-input v-model="form.nativeCity" placeholder="请输入籍贯市(地、州)" />
        </el-form-item>
        <el-form-item label="户籍地省(区、市)" prop="hukouProvince">
          <el-input v-model="form.hukouProvince" placeholder="请输入户籍地省(区、市)" />
        </el-form-item>
        <el-form-item label="户籍地市(地、州)" prop="hukouCity">
          <el-input v-model="form.hukouCity" placeholder="请输入户籍地市(地、州)" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="familyAddress">
          <el-input v-model="form.familyAddress" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item label="身高" prop="height">
          <el-input v-model="form.height" placeholder="请输入身高" />
        </el-form-item>
        <el-form-item label="志愿者polo衫尺码" prop="poloSize">
          <el-input v-model="form.poloSize" placeholder="请输入志愿者polo衫尺码" />
        </el-form-item>
        <el-form-item label="是否参加出征仪式" prop="attendCeremony">
          <el-input v-model="form.attendCeremony" placeholder="请输入是否参加出征仪式" />
        </el-form-item>
        <el-form-item label="分配省份" prop="assignProvince">
          <el-input v-model="form.assignProvince" placeholder="请输入分配省份" />
        </el-form-item>
        <el-form-item label="是否有教师资格证" prop="hasTeacherCert">
          <el-input v-model="form.hasTeacherCert" placeholder="请输入是否有教师资格证" />
        </el-form-item>
        <el-form-item label="学段学科" prop="teacherSubject">
          <el-input v-model="form.teacherSubject" placeholder="请输入学段学科" />
        </el-form-item>
        <el-form-item label="联系方式" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱" />
        </el-form-item>
        <el-form-item label="是否有学生干部经历" prop="hasCadreExp">
          <el-input v-model="form.hasCadreExp" placeholder="请输入是否有学生干部经历" />
        </el-form-item>
        <el-form-item label="服务省份" prop="serviceProvince">
          <el-input v-model="form.serviceProvince" placeholder="请输入服务省份" />
        </el-form-item>
        <el-form-item label="服务县" prop="serviceCounty">
          <el-input v-model="form.serviceCounty" placeholder="请输入服务县" />
        </el-form-item>
        <el-form-item label="服务单位" prop="serviceUnit">
          <el-input v-model="form.serviceUnit" placeholder="请输入服务单位" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" placeholder="请输入职位" />
        </el-form-item>
        <el-form-item label="岗位编号" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入岗位编号" />
        </el-form-item>
        <el-form-item label="服务单位地址" prop="serviceAddress">
          <el-input v-model="form.serviceAddress" placeholder="请输入服务单位地址" />
        </el-form-item>
        <el-form-item label="服务单位联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入服务单位联系人" />
        </el-form-item>
        <el-form-item label="单位联系电话" prop="unitPhone">
          <el-input v-model="form.unitPhone" placeholder="请输入单位联系电话" />
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
        <!--  导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="500px" append-to-body>
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
import { listStudent_admission, getStudent_admission, delStudent_admission, addStudent_admission, updateStudent_admission } from "@/api/system/student_admission"
import { getToken } from "@/utils/auth"
import RequestState from "@/components/RequestState"
export default {
  name: "Student_admission",
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
      // 学生录取信息（西部计划/三支一扶等项目）表格数据
      student_admissionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
       //  导入参数
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
        userId: null,
        userName: null,
        idCard: null,
        gender: null,
        nation: null,
        birthDate: null,
        politicalStatus: null,
        college: null,
        major: null,
        schoolCcbCard: null,
        nativeProvince: null,
        nativeCity: null,
        hukouProvince: null,
        hukouCity: null,
        familyAddress: null,
        height: null,
        poloSize: null,
        attendCeremony: null,
        assignProvince: null,
        hasTeacherCert: null,
        teacherSubject: null,
        projectType: null,
        specialType: null,
        phone: null,
        email: null,
        hasCadreExp: null,
        serviceProvince: null,
        serviceCounty: null,
        serviceUnit: null,
        position: null,
        postCode: null,
        serviceAddress: null,
        contactPerson: null,
        unitPhone: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        idCard: [
          { required: true, message: "身份证号不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询学生录取信息（西部计划/三支一扶等项目）列表 */
    async getList() {
      this.loading = true
      this.loadError = ''
      try {
        const response = await listStudent_admission(this.queryParams)
        this.student_admissionList = response.rows
        this.total = response.total
      } catch (e) {
        this.student_admissionList = []
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
        userId: null,
        userName: null,
        idCard: null,
        gender: null,
        nation: null,
        birthDate: null,
        politicalStatus: null,
        college: null,
        major: null,
        schoolCcbCard: null,
        nativeProvince: null,
        nativeCity: null,
        hukouProvince: null,
        hukouCity: null,
        familyAddress: null,
        height: null,
        poloSize: null,
        attendCeremony: null,
        assignProvince: null,
        hasTeacherCert: null,
        teacherSubject: null,
        projectType: null,
        specialType: null,
        phone: null,
        email: null,
        hasCadreExp: null,
        serviceProvince: null,
        serviceCounty: null,
        serviceUnit: null,
        position: null,
        postCode: null,
        serviceAddress: null,
        contactPerson: null,
        unitPhone: null,
        remark: null,
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
      this.title = "添加学生录取信息（西部计划/三支一扶等项目）"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStudent_admission(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改学生录取信息（西部计划/三支一扶等项目）"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStudent_admission(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStudent_admission(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学生录取信息（西部计划/三支一扶等项目）编号为"' + ids + '"的数据项？').then(function() {
        return delStudent_admission(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student_admission/export', {
        ...this.queryParams
      }, `student_admission_${new Date().getTime()}.xlsx`)
    },
     /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入"
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/student_admission/importTemplate', {
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
