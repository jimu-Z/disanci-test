<template>
  <div class="region-selector">
    <el-form-item
      :label="label"
      :prop="prop"
      class="region-form-item"
    >
      <!-- 省份选择 -->
      <el-select
        v-model="provinceValue"
        placeholder="请选择省份"
        class="region-select"
        @change="handleProvinceChange"
        filterable
        clearable
        :disabled="disabled"
      >
        <el-option
          v-for="province in provinceList"
          :key="province.code"
          :label="province.name"
          :value="province.code"
        />
      </el-select>

      <!-- 城市选择 -->
      <el-select
        v-model="cityValue"
        placeholder="请选择城市"
        class="region-select"
        @change="handleCityChange"
        filterable
        clearable
        :disabled="!provinceValue || disabled"
      >
        <el-option
          v-for="city in cityList"
          :key="city.code"
          :label="city.name"
          :value="city.code"
        />
      </el-select>

      <!-- 区县选择 -->
      <el-select
        v-model="districtValue"
        placeholder="请选择区县"
        class="region-select"
        @change="handleDistrictChange"
        filterable
        clearable
        :disabled="!cityValue || disabled"
      >
        <el-option
          v-for="district in districtList"
          :key="district.code"
          :label="district.name"
          :value="district.code"
        />
      </el-select>
    </el-form-item>
  </div>
</template>

<script>
// 导入省市区县数据
import provinceData from '@/assets/region/01province.json';
import cityData from '@/assets/region/02city.json';
import districtData from '@/assets/region/03district.json';

export default {
  name: 'RegionSelector',
  props: {
    // 表单标签文本
    label: {
      type: String,
      default: '服务地区'
    },
    // 表单prop名称（用于表单校验）
    prop: {
      type: String,
      default: 'serviceRegion'
    },
    // 初始值：{ provinceCode: '', cityCode: '', districtCode: '' }
    value: {  // Vue2 中使用 value 接收 v-model 绑定值
      type: Object,
      default: () => ({
        provinceCode: '',
        cityCode: '',
        districtCode: ''
      })
    },
    // 是否禁用组件
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 原始省市区县数据
     provinceData: [],
     cityData: [],
     districtData: [],
      // 下拉列表数据
      provinceList: [],
      cityList: [],
      districtList: [],
      // 选中值
      provinceValue: '',
      cityValue: '',
      districtValue: ''
    };
  },
  computed: {
    // 计算完整的选中地区信息
    selectedRegion() {
      // 查找省份名称
      const province = this.provinceList.find(item => item.code === this.provinceValue) || { name: '' };
      // 查找城市名称
      const city = this.cityList.find(item => item.code === this.cityValue) || { name: '' };
      // 查找区县名称
      const district = this.districtList.find(item => item.code === this.districtValue) || { name: '' };

      return {
        provinceCode: this.provinceValue,
        provinceName: province.name,
        cityCode: this.cityValue,
        cityName: city.name,
        districtCode: this.districtValue,
        districtName: district.name,
        fullName: `${province.name}${city.name}${district.name}` // 完整地区名称
      };
    }
  },
  watch: {
    // 监听初始值变化（实现数据回显）
    value: {
      async handler(newVal) {
        // 清空所有状态（初始化）
        this.provinceValue = '';
        this.cityValue = '';
        this.districtValue = '';
        this.cityList = [];
        this.districtList = [];

        if (newVal && Object.keys(newVal).length) {
          // 回显省份
          if (newVal.provinceCode) {
            this.provinceValue = newVal.provinceCode;
            // 加载城市列表
            await this.loadCityList(newVal.provinceCode);
            // 回显城市
            if (newVal.cityCode) {
              this.cityValue = newVal.cityCode;
              // 加载区县列表
              await this.loadDistrictList( newVal.cityCode);
              // 回显区县
              if (newVal.districtCode) {
                this.districtValue = newVal.districtCode;
              }
            }
          }
        }
      },
      immediate: true, // 初始化时立即执行
      deep: true // 深度监听对象属性变化
    },
    // 监听禁用状态变化
    disabled(newVal) {
      if (newVal) {
        // 禁用时清空选中值
        this.provinceValue = '';
        this.cityValue = '';
        this.districtValue = '';
        this.cityList = [];
        this.districtList = [];
      }
    }
  },
  created() {
    // 初始化省市区县数据
    this.provinceData= provinceData,
      this.cityData= cityData,
      this.districtData= districtData,
    // 加载省份列表
    this.initProvinceList();
  },
  methods: {
    /**
     * 初始化省份列表
     */
    initProvinceList() {
      this.provinceList = this.provinceData.map(item => ({
        code: item.code,
        name: item.name
      }));
    },
    /**
     * 加载城市列表（抽离为独立方法，便于异步调用）
     * @param {String} provinceCode 省份代码
     */
    async loadCityList(provinceCode) {

      return new Promise((resolve) => {
        const currentProvince = this.cityData.filter(item =>  item.parent_code === provinceCode);

        if (currentProvince  ) {
          this.cityList = currentProvince.map(item => ({
            code: item.code,
            name: item.name
          }));
        } else {
          this.cityList = [];
          this.$message.warning('该省份暂无城市数据');
        }
        resolve();
      });
    },
    /**
     * 加载区县列表（抽离为独立方法，便于异步调用）
     * @param {String} provinceCode 省份代码
     * @param {String} cityCode 城市代码
     */
    async loadDistrictList( cityCode) {
      return new Promise((resolve) => {
         const currentCity = this.districtData.filter(item =>  item.parent_code === cityCode);
        if (currentCity  ) {
          this.districtList = currentCity.map(item => ({
            code: item.code,
            name: item.name
          }));
        } else {
          this.districtList = [];
          this.$message.warning('该城市暂无区县数据');
        }
        resolve();
      });
    },
    /**
     * 省份选择变更事件
     * @param {String} code 选中的省份代码
     */
    async handleProvinceChange(code) {
      // 清空城市和区县选中值
      this.cityValue = '';
      this.districtValue = '';
      this.districtList = [];

      // 加载城市列表
      await this.loadCityList(code);

      // 触发值更新
      this.emitValueChange();
    },
    /**
     * 城市选择变更事件
     * @param {String} code 选中的城市代码
     */
    async handleCityChange(code) {
      // 清空区县选中值
      this.districtValue = '';

      // 加载区县列表
      await this.loadDistrictList(this.provinceValue, code);

      // 触发值更新
      this.emitValueChange();
    },
    /**
     * 区县选择变更事件
     */
    handleDistrictChange() {
      this.emitValueChange();
    },
    /**
     * 触发值更新（Vue2 中更新 v-model）
     */
    emitValueChange() {
      // 1. 更新 v-model 绑定值（仅返回 code，便于存储）
      const value = {
        provinceCode: this.provinceValue,
        cityCode: this.cityValue,
        districtCode: this.districtValue
      };
      this.$emit('input', value); // Vue2 中通过 input 事件更新 v-model

      // 2. 触发自定义 change 事件（返回完整信息，便于业务使用）
      this.$emit('change', this.selectedRegion);
    }
  }
};
</script>

<style scoped>
/* 全局继承 box-sizing，避免布局错乱 */
.region-selector,
.region-selector * {
  box-sizing: border-box;
}

.region-selector {
  .region-form-item {
    margin-bottom: 20px;
    width: 100%;

    /* 兼容所有 Vue2 环境的样式穿透 */
    /deep/ .el-form-item__label {
      font-weight: 500;
      color: #333;
      padding: 0 5px;
    }

    /deep/ .el-form-item__content {
      display: flex;
      flex-wrap: wrap;
      gap: 10px; /* 替代 margin-right，更优雅的间距控制 */
    }

    .region-select {
      width: calc(33.33% - 7px); /* 适配 gap 间距，确保3列均分 */
      height: 36px; /* 统一高度，匹配 Element UI 原生样式 */

      /* 响应式适配：移动端纵向排列 */
      @media (max-width: 768px) {
        width: 100%;
        margin-bottom: 8px;
      }
    }

    /* 最后一个选择框移除底部间距（移动端） */
    .region-select:last-child {
      @media (max-width: 768px) {
        margin-bottom: 0;
      }
    }
  }
}
</style>
