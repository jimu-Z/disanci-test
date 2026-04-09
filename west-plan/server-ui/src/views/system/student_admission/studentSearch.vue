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
       <el-form-item label="录取年份" prop="admitYear">
        <el-input
          v-model="queryParams.admitYear"
          placeholder="请输入录取年份"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="籍贯省" prop="nativeProvince">
        <el-input
          v-model="queryParams.nativeProvince"
          placeholder="请输入籍贯省"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="籍贯市 " prop="nativeCity">
        <el-input
          v-model="queryParams.nativeCity"
          placeholder="请输入籍贯市(地、州)"
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


      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>



    <el-table v-loading="loading" :data="student_admissionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="学号" align="center" prop="userId" />
      <el-table-column label="学生姓名" align="center" prop="userName" />

      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="民族" align="center" prop="nation" />

      <el-table-column label="政治面貌" align="center" prop="politicalStatus" />
      <el-table-column label="学院/系名称" align="center" prop="college" />
      <el-table-column label="专业全称" align="center" prop="major" />
       <el-table-column label="录取年份" align="center" prop="admitYear" />


      <el-table-column label="籍贯省" align="center" prop="nativeProvince" />
      <el-table-column label="籍贯市 " align="center" prop="nativeCity" />

      <el-table-column label="分配省份" align="center" prop="assignProvince" />

      <!--el-table-column label="联系方式" align="center" prop="phone" />
      <el-table-column label="电子邮箱" align="center" prop="email" /-->

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="addFriend(scope.row.userId)"

          >加为好友</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="addMessage(scope.row.userId)"

          >留言</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listStudent_admission, getStudent_admission    } from "@/api/system/student_admission"
import { getToken } from "@/utils/auth"
export default {
  name: "Student_admission",
  data() {
    return {
      // 遮罩层
      loading: false,
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
        url: process.env.VUE_APP_BASE_API + "/system/student_admission/importData"
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
        admitYear:null
      },
      // 表单参数
      form: {},

    }
  },
  created() {

  },
  methods: {
    /** 查询学生录取信息（西部计划/三支一扶等项目）列表 */
    getList() {
      this.loading = true
      listStudent_admission(this.queryParams).then(response => {
        this.student_admissionList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
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

  }
}
</script>
