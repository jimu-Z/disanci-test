<template>
  <div class="forum-container">
    <el-card class="forum-card" shadow="hover">
      <div class="flex items-center justify-between mb-4">
        <h3 class="m-0">板块管理</h3>
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd"   v-hasPermi="['system:forum_board:add']"
 >
          新增板块
        </el-button>
      </div>

      <!-- 搜索栏 -->
      <el-form :model="searchForm" inline class="mb-4">
        <el-form-item label="板块名称">
          <el-input
            v-model="searchForm.boardName"
            placeholder="请输入板块名称"
            clearable
            @keyup.enter="getBoardList"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="searchForm.status"
            placeholder="请选择状态"
            clearable
            style="width: 120px;"
          >
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="getBoardList">查询</el-button>
          <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 板块列表 -->
      <el-table
        :data="boardList"
        border
        stripe
        v-loading="loading"
        @sort-change="handleSortChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="boardName" label="板块名称" min-width="150"></el-table-column>
        <el-table-column prop="boardDesc" label="板块描述" min-width="200"></el-table-column>
        <el-table-column prop="sort" label="排序" width="80" align="center">
          <template slot-scope="scope">
            <el-input-number
              v-model="scope.row.sort"
              :min="1"
              :max="999"
              @change="handleSingleSort(scope.row)"
              size="small"
            ></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="1"
              inactive-value="0"
              @change="handleStatusChange(scope.row)"
              v-if="hasPermi('system:forum_board:edit')"
            ></el-switch>
            <el-tag :type="scope.row.status === '1' ? 'success' : 'danger'" v-else>
              {{ scope.row.status === '1' ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="180"
          :formatter="formatTime"
        ></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
              v-if="hasPermi('system:forum_board:edit')"
            >修改</el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row.id)"
              class="danger-button"
              v-if="hasPermi('system:forum_board:remove')"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
         v-show="total>0"
      :total="total"
      :page.sync="pageNum"
      :limit.sync="pageSize"
      @pagination="getBoardList"

        class="mt-4 flex justify-center"
      ></el-pagination>
    </el-card>

    <!-- 新增/修改板块弹窗 -->
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="formRef"
        :model="form"
        label-width="80px"
        :rules="rules"
        label-position="top"
      >
        <el-form-item label="板块名称" prop="boardName">
          <el-input
            v-model="form.boardName"
            placeholder="请输入板块名称（2-20个字）"
            maxlength="20"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="板块描述" prop="boardDesc">
          <el-input
            v-model="form.boardDesc"
            placeholder="请输入板块描述（0-100个字）"
            type="textarea"
            rows="3"
            maxlength="100"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" :min="1" :max="999"></el-input-number>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleDialogSubmit" :loading="dialogLoading">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listForum_board,enableList,getForum_board,addForum_board,updateForum_board,delForum_board} from '@/api/system/forum_board';
import { mapGetters } from 'vuex';
import { formatTime } from '@/utils/utils';
import { export_json_to_excel } from '@/utils/excel'; // Vue2内置Excel导出

export default {
  name: 'ForumBoardManager',
  data() {
    return {
      // 加载状态
      loading: false,
      dialogLoading: false,
      // 分页参数
      pageNum: 1,
      pageSize: 10,
      total: 0,
      // 板块列表
      boardList: [],
      // 搜索表单
      searchForm: {
        boardName: '',
        status: ''
      },
      // 弹窗相关
      dialogVisible: false,
      dialogTitle: '新增板块',
      // 表单数据
      form: {
        id: null,
        boardName: '',
        boardDesc: '',
        sort: 1,
        status: '1'
      },
      // 表单校验规则
      rules: {
        boardName: [
          { required: true, message: '请输入板块名称', trigger: 'blur' },
          { min: 2, max: 20, message: '板块名称长度在2到20个字之间', trigger: 'blur' }
        ],
        sort: [{ required: true, message: '请输入排序值', trigger: 'blur' }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }]
      }
    };
  },
  computed: {
    ...mapGetters(['permissionList'])
  },
  created() {
    this.getBoardList();
  },
  methods: {
    hasPermi(permission) {
      return this.$auth.hasPermi(permission);
    },
    // 获取板块列表
    async getBoardList() {
      this.loading = true;
      try {
        const params = {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          boardName: this.searchForm.boardName,
          status: this.searchForm.status
        };
        const res = await listForum_board(params);
        this.boardList = res.rows;
        this.total = res.total;
      } catch (error) {
        console.error('获取板块列表失败：', error);
        this.boardList = [];
        this.total = 0;
      } finally {
        this.loading = false;
      }
    },
    // 重置搜索
    resetSearch() {
      this.searchForm = {
        boardName: '',
        status: ''
      };
      this.pageNum = 1;
      this.getBoardList();
    },
    // 单个排序修改
    async handleSingleSort(row) {
      try {
        await forumApi.board.edit({ id: row.id, sort: row.sort });
        this.$message.success('排序修改成功');
      } catch (error) {
        console.error('排序修改失败：', error);
        this.$message.error('排序修改失败');
        this.getBoardList();
      }
    },
    // 状态修改（启用/禁用）
    async handleStatusChange(row) {
      try {
        await forumApi.board.changeStatus({ id: row.id, status: row.status });
        this.$message.success('状态修改成功');
      } catch (error) {
        console.error('状态修改失败：', error);
        this.$message.error('状态修改失败');
        this.getBoardList();
      }
    },
    // 新增板块
    handleAdd() {
      this.dialogTitle = '新增板块';
      this.form = { id: null, boardName: '', boardDesc: '', sort: 1, status: '1' };
      this.dialogVisible = true;
    },
    // 修改板块
    handleEdit(row) {
      this.dialogTitle = '修改板块';
      this.form = { ...row };
      this.dialogVisible = true;
    },
    // 删除板块
    async handleDelete(id) {
      try {
        await this.$confirm(
          '确定要删除该板块吗？板块下有帖子时无法删除',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        );
        await  delForum_board(id);
        this.$message.success('板块删除成功');
        this.getBoardList();
      } catch (error) {
        this.$message.info('已取消删除');
      }
    },
    // 弹窗提交（新增/修改）
    async handleDialogSubmit() {
      this.$refs.formRef.validate(async (valid) => {
        if (!valid) return;

        this.dialogLoading = true;
        try {
          if (this.form.id) {
            await updateForum_board(this.form);
            this.$message.success('板块修改成功');
          } else {
            await  addForum_board(this.form);
            this.$message.success('板块新增成功');
          }
          this.dialogVisible = false;
          this.getBoardList();
        } catch (error) {
          console.error('提交失败：', error);
        } finally {
          this.dialogLoading = false;
        }
      });
    },
    // 时间格式化
    formatTime,
    // 导出板块列表
    exportBoardList() {
      const tHeader = ['ID', '板块名称', '板块描述', '排序', '状态', '创建时间'];
      const filterVal = ['id', 'boardName', 'boardDesc', 'sort', 'status', 'createTime'];
      const data = this.formatExcelData(this.boardList, filterVal);
      export_json_to_excel(tHeader, data, '论坛板块数据');
    },
    // 格式化Excel导出数据
    formatExcelData(list, filterVal) {
      return list.map(item => {
        return filterVal.map(key => {
          if (key === 'status') {
            return item[key] === '1' ? '启用' : '禁用';
          } else if (key === 'createTime') {
            return formatTime(item[key]);
          } else {
            return item[key] || '';
          }
        });
      });
    }
  }
};
</script>

<style scoped lang="scss">
@import '/static/css/forum.css';
</style>
