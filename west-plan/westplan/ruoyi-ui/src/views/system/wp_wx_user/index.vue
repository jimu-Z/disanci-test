<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联sys_user.id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入关联sys_user.id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信OpenID" prop="wxOpenid">
        <el-input
          v-model="queryParams.wxOpenid"
          placeholder="请输入微信OpenID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信昵称" prop="wxNickname">
        <el-input
          v-model="queryParams.wxNickname"
          placeholder="请输入微信昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信性别" prop="wxGender">
        <el-input
          v-model="queryParams.wxGender"
          placeholder="请输入微信性别"
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
          v-hasPermi="['system:wp_wx_user:add']"
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
          v-hasPermi="['system:wp_wx_user:edit']"
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
          v-hasPermi="['system:wp_wx_user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:wp_wx_user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wp_wx_userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="关联sys_user.id" align="center" prop="userId" />
      <el-table-column label="微信OpenID" align="center" prop="wxOpenid" />
      <el-table-column label="微信昵称" align="center" prop="wxNickname" />
      <el-table-column label="微信头像" align="center" prop="wxAvatar" />
      <el-table-column label="微信性别" align="center" prop="wxGender" />
      <el-table-column label="绑定状态" align="center" prop="bindStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:wp_wx_user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:wp_wx_user:remove']"
          >删除</el-button>
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

    <!-- 添加或修改微信用户绑定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="关联sys_user.id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入关联sys_user.id" />
        </el-form-item>
        <el-form-item label="微信OpenID" prop="wxOpenid">
          <el-input v-model="form.wxOpenid" placeholder="请输入微信OpenID" />
        </el-form-item>
        <el-form-item label="微信昵称" prop="wxNickname">
          <el-input v-model="form.wxNickname" placeholder="请输入微信昵称" />
        </el-form-item>
        <el-form-item label="微信头像" prop="wxAvatar">
          <el-input v-model="form.wxAvatar" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="微信性别" prop="wxGender">
          <el-input v-model="form.wxGender" placeholder="请输入微信性别" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listWp_wx_user, getWp_wx_user, delWp_wx_user, addWp_wx_user, updateWp_wx_user } from "@/api/system/wp_wx_user"

export default {
  name: "Wp_wx_user",
  data() {
    return {
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
      // 微信用户绑定表格数据
      wp_wx_userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        wxOpenid: null,
        wxNickname: null,
        wxAvatar: null,
        wxGender: null,
        bindStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "关联sys_user.id不能为空", trigger: "blur" }
        ],
        wxOpenid: [
          { required: true, message: "微信OpenID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询微信用户绑定列表 */
    getList() {
      this.loading = true
      listWp_wx_user(this.queryParams).then(response => {
        this.wp_wx_userList = response.rows
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
        wxOpenid: null,
        wxNickname: null,
        wxAvatar: null,
        wxGender: null,
        bindStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.title = "添加微信用户绑定"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWp_wx_user(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改微信用户绑定"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWp_wx_user(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWp_wx_user(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除微信用户绑定编号为"' + ids + '"的数据项？').then(function() {
        return delWp_wx_user(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/wp_wx_user/export', {
        ...this.queryParams
      }, `wp_wx_user_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
