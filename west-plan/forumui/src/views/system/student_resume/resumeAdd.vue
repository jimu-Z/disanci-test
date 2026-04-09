<template>
  <div class="app-container">


    <!-- 添加或修改学生履历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

         <el-form-item label="起止时间" prop="admissionTime">
          <el-input v-model="form.admissionTime" placeholder="请输入起止时间" />

        </el-form-item>

        <!-- 省市区县联动选择组件 -->
        <region-selector label="服务地区" prop="serviceRegion" v-model="form.serviceRegion" @change="handleFormRegionChange"
          :disabled="isDisabled" />
        <div hidden>

          <el-form-item label="服务省" prop="serviceProvince">
            <el-input v-model="form.serviceProvince" placeholder="请输入服务省" />
          </el-form-item>
          <el-form-item label="服务市" prop="serviceCity">
            <el-input v-model="form.serviceCity" placeholder="请输入服务市" />
          </el-form-item>
          <el-form-item label="服务县区" prop="serviceCounty">
            <el-input v-model="form.serviceCounty" placeholder="请输入服务县区" />
          </el-form-item>
        </div>

        <el-form-item label="服务单位" prop="serviceUnit">
          <el-input v-model="form.serviceUnit" placeholder="请输入服务单位" />
        </el-form-item>
        <el-form-item label="岗位描述" prop="postType">
          <el-input v-model="form.postType" placeholder="请输入服务单位" />
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
import { listStudent_resume, getStudent_resume, delStudent_resume, addStudent_resume, updateStudent_resume } from "@/api/system/student_resume"
import RegionSelector from '@/components/RegionSelector';

export default {
  name: "Student_resume",
  components: {
    RegionSelector // 注册组件
  },
  data() {
    return {
      // 路由传参的ID
      resumeId: '',
      // 省市区县绑定值（仅存储 code）

      isDisabled: false,
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
      // 学生履历表格数据
      student_resumeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,
      // 查询参数
      queryParams: {
        serviceRegion: {
          provinceCode: '',
          cityCode: '',
          districtCode: ''
        },
        pageNum: 1,
        pageSize: 10,
        applyId: null,
        userId: null,
        serviceProvince: null,
        serviceCity: null,
        serviceCounty: null,
        serviceUnit: null,
        postType: null,
        admissionTime: null,
      },
      // 表单参数
      form: {
        // 省市区县绑定值（仅存储 code）
        serviceRegion: {
          provinceCode: '',
          cityCode: '',
          districtCode: ''
        },
        userId: null,
        serviceProvince: null,
        serviceCity: null,
        serviceCounty: null,
        serviceUnit: null,
        postType: null,
      },
      // 完整的地区信息（存储名称，便于展示/提交）
      regionInfo: {},
      // 表单校验
      rules: {
        applyId: [
          { required: true, message: "学号不能为空", trigger: "blur" }
        ],

        serviceProvince: [
          { required: true, message: "服务省不能为空", trigger: "blur" }
        ],
        serviceCity: [
          { required: true, message: "服务市不能为空", trigger: "blur" }
        ],
        serviceCounty: [
          { required: true, message: "服务县区不能为空", trigger: "blur" }
        ],
        serviceUnit: [
          { required: true, message: "服务单位不能为空", trigger: "blur" }
        ],
        postType: [
          { required: true, message: "岗位描述不能为空", trigger: "change" }
        ],
        admissionTime: [
          { required: true, message: "起止时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.initPage()
  },
  methods: {

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        applyId: null,
        userId: null,
        serviceProvince: null,
        serviceCity: null,
        serviceCounty: null,
        serviceUnit: null,
        postType: null,
        admissionTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      }
      this.resetForm("form")
    },
    /**
    * 页面初始化
    */
    async initPage() {
      // 1. 获取路由中的 ID（动态路由参数）
      this.resumeId = this.$route.params.id;



      // 无ID为添加
      if (!this.resumeId) {
        handleAdd()
      } else {//编辑
        try {

          getStudent_resume(this.resumeId ).then(response => {
            this.form = response.data
            this.form.serviceRegion = {
              provinceCode: response.data.serviceProvince,
              cityCode: response.data.serviceCity,
              districtCode: response.data.serviceCounty
            }
            this.open = true
            this.title = "修改学生履历"
          })

          // console.log('editPostForm', this.editPostForm);
        } catch (error) {
          console.error('页面初始化失败：', error);
          this.$message.error(error.msg || '获取 信息失败，无法编辑');
        }
      }
      // 2. 并行加载板块列表和帖子详情（提升性能）

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
      this.title = "添加学生履历"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStudent_resume(id).then(response => {
        this.form = response.data
        this.form.serviceRegion = {
          provinceCode: response.data.serviceProvince,
          cityCode: response.data.serviceCity,
          districtCode: response.data.serviceCounty
        }
        this.open = true
        this.title = "修改学生履历"
      })
    },
    /**
    * 地区选择变更事件（获取完整信息）
    * @param {Object} regionInfo 完整的地区信息
    */
    handleQueryRegionChange(regionInfo) {
      this.regionInfo = regionInfo;
      this.queryParams.serviceProvince = regionInfo.provinceName;
      this.queryParams.serviceCity = regionInfo.cityName;
      this.queryParams.serviceCounty = regionInfo.districtName;

    },
    handleFormRegionChange(regionInfo) {
      //console.log('选中的地区信息：', regionInfo);
      this.regionInfo = regionInfo;
      this.form.serviceProvince = regionInfo.provinceName;
      this.form.serviceCity = regionInfo.cityName;
      this.form.serviceCounty = regionInfo.districtName;
      // console.log('选中的地区信息2：', this.form);

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {

          if (this.form.id != null) {
            updateStudent_resume(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStudent_resume(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
        //返回上一个页面
        this.$router.back()
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除学生履历编号为"' + ids + '"的数据项？').then(function () {
        return delStudent_resume(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/student_resume/export', {
        ...this.queryParams
      }, `student_resume_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
