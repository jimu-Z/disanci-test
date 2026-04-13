<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公众号APPID" prop="appId">
        <el-input
          v-model="queryParams.appId"
          placeholder="请输入公众号APPID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公众号APPSECRET" prop="appSecret">
        <el-input
          v-model="queryParams.appSecret"
          placeholder="请输入公众号APPSECRET"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公众号Token" prop="token">
        <el-input
          v-model="queryParams.token"
          placeholder="请输入公众号Token"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消息加密密钥" prop="aesKey">
        <el-input
          v-model="queryParams.aesKey"
          placeholder="请输入消息加密密钥"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="登录回调地址" prop="loginRedirectUrl">
        <el-input
          v-model="queryParams.loginRedirectUrl"
          placeholder="请输入登录回调地址"
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
          v-hasPermi="['system:wx_mp_config:add']"
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
          v-hasPermi="['system:wx_mp_config:edit']"
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
          v-hasPermi="['system:wx_mp_config:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:wx_mp_config:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wx_mp_configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="公众号APPID" align="center" prop="appId" />
      <el-table-column label="公众号APPSECRET" align="center" prop="appSecret" />
      <el-table-column label="公众号Token" align="center" prop="token" />
      <el-table-column label="消息加密密钥" align="center" prop="aesKey" />
      <el-table-column label="登录回调地址" align="center" prop="loginRedirectUrl" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:wx_mp_config:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:wx_mp_config:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <request-state
      :loading="loading"
      :error="Boolean(loadError)"
      :empty="!loadError && wx_mp_configList.length === 0"
      empty-text="暂无公众号配置数据"
      error-text="公众号配置加载失败，请重试"
      @retry="getList"
    />
    
    <pagination
      v-show="total>0 && !loadError"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改微信公众号配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公众号APPID" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入公众号APPID" />
        </el-form-item>
        <el-form-item label="公众号APPSECRET" prop="appSecret">
          <el-input v-model="form.appSecret" placeholder="请输入公众号APPSECRET" />
        </el-form-item>
        <el-form-item label="公众号Token" prop="token">
          <el-input v-model="form.token" placeholder="请输入公众号Token" />
        </el-form-item>
        <el-form-item label="消息加密密钥" prop="aesKey">
          <el-input v-model="form.aesKey" placeholder="请输入消息加密密钥" />
        </el-form-item>
        <el-form-item label="登录回调地址" prop="loginRedirectUrl">
          <el-input v-model="form.loginRedirectUrl" placeholder="请输入登录回调地址" />
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
import { listWx_mp_config, getWx_mp_config, delWx_mp_config, addWx_mp_config, updateWx_mp_config } from "@/api/system/wx_mp_config"
import RequestState from "@/components/RequestState"

export default {
  name: "Wx_mp_config",
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
      // 微信公众号配置表格数据
      wx_mp_configList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        appId: null,
        appSecret: null,
        token: null,
        aesKey: null,
        loginRedirectUrl: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        appId: [
          { required: true, message: "公众号APPID不能为空", trigger: "blur" }
        ],
        appSecret: [
          { required: true, message: "公众号APPSECRET不能为空", trigger: "blur" }
        ],
        token: [
          { required: true, message: "公众号Token不能为空", trigger: "blur" }
        ],
        loginRedirectUrl: [
          { required: true, message: "登录回调地址不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询微信公众号配置列表 */
    async getList() {
      this.loading = true
      this.loadError = ''
      try {
        const response = await listWx_mp_config(this.queryParams)
        this.wx_mp_configList = response.rows
        this.total = response.total
      } catch (e) {
        this.wx_mp_configList = []
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
        appId: null,
        appSecret: null,
        token: null,
        aesKey: null,
        loginRedirectUrl: null,
        status: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加微信公众号配置"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getWx_mp_config(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改微信公众号配置"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWx_mp_config(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addWx_mp_config(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除微信公众号配置编号为"' + ids + '"的数据项？').then(function() {
        return delWx_mp_config(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/wx_mp_config/export', {
        ...this.queryParams
      }, `wx_mp_config_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
