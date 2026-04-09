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

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.friendApplyStatus === 'pending' ? 'el-icon-loading' : 'el-icon-edit'"
            :disabled="scope.row.friendApplyStatus === 'pending'"
            @click="addFriend(scope.row)"
          >
            {{ getFriendButtonText(scope.row.friendApplyStatus) }}
          </el-button>
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
import { listStudent_admission, getStudent_admission } from "@/api/system/student_admission"
import { addFriendApply } from "@/api/system/student_search" // 导入好友申请接口
import { getToken } from "@/utils/auth"
import { getCurrentUserId } from "@/utils/auth" // 获取当前登录用户ID的工具函数

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
        admitYear: null
      },
      // 表单参数
      form: {},
      // 当前登录用户ID
      currentUserId: null
    }
  },
  created() {
    this.currentUserId = getCurrentUserId() // 获取当前登录用户ID
    this.getList()
  },
  methods: {
    /** 查询学生录取信息（西部计划/三支一扶等项目）列表 */
    getList() {
      this.loading = true
      listStudent_admission(this.queryParams).then(response => {
        // 为每条数据初始化好友申请状态
        this.student_admissionList = (response.rows || []).map(item => ({
          ...item,
          friendApplyStatus: item.friendApplyStatus || null // 从后端获取状态，如果没有则为null
        }))
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
    /** 获取好友按钮显示文字 */
    getFriendButtonText(status) {
      if (status === 'pending') {
        return '待处理'
      }
      return '加为好友'
    },
    /** 添加好友 */
    addFriend(row) {
      // 如果已经是待处理状态，不允许重复点击
      if (row.friendApplyStatus === 'pending') {
        this.$message.warning('好友申请已发送，请耐心等待处理')
        return
      }

      // 防止重复点击
      if (row._isApplying) {
        return
      }

      // 设置申请中状态，防止重复提交
      this.$set(row, '_isApplying', true)

      // 调用好友申请接口
      addFriendApply({
        applyUserId: this.currentUserId,
        targetUserId: row.userId,
        applyMsg: `我是${this.$store.state.user.name || '系统用户'}，请求添加好友`
      }).then(response => {
        // 申请成功，更新按钮状态为待处理
        this.$set(row, 'friendApplyStatus', 'pending')
        this.$message.success('好友申请已发送')
      }).catch(error => {
        console.error('好友申请失败', error)
        this.$message.error(error.msg || '好友申请发送失败，请稍后重试')
      }).finally(() => {
        // 移除申请中标记
        this.$set(row, '_isApplying', false)
      })
    },
    /** 留言 */
    addMessage(userId) {
      // 跳转到留言页面或打开留言弹窗
      this.$router.push({
        path: '/message/add',
        query: { targetUserId: userId }
      })
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    }
  }
}
</script>
