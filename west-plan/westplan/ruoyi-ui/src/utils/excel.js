import * as XLSX from 'xlsx';
import FileSaver from 'file-saver';

/**
 * 通用 JSON 导出 Excel 方法（单 Sheet，最常用）
 * @param {Array} header 导出表头 ['姓名', '年龄', '性别']
 * @param {Array} data 导出数据 [{name: '张三', age: 20, sex: '男'}, ...]
 * @param {String} fileName 导出文件名（无需.xlsx后缀）
 * @param {String} sheetName Sheet 名称，默认「Sheet1」
 */
export function export_json_to_excel({
  header,
  data,
  fileName = 'excel-file',
  sheetName = 'Sheet1'
}) {
  // 校验入参
  if (!Array.isArray(header) || header.length === 0) {
    throw new Error('导出表头header必须为非空数组');
  }
  if (!Array.isArray(data) || data.length === 0) {
    throw new Error('导出数据data必须为非空数组');
  }

  // 处理数据：将对象数组转为二维数组（与表头顺序匹配）
  const filterData = data.map(item => header.map(key => item[key] || ''));
  // 组合表头和数据
  const excelData = [header, ...filterData];
  // 创建工作簿
  const wb = XLSX.utils.book_new();
  // 将二维数组转为 Sheet 数据（aoa_to_sheet：Array of Array to Sheet）
  const ws = XLSX.utils.aoa_to_sheet(excelData);

  // 追加 Sheet 到工作簿
  XLSX.utils.book_append_sheet(wb, ws, sheetName);
  // 生成 Excel 二进制数据
  const wbout = XLSX.write(wb, {
    bookType: 'xlsx', // 导出格式：xlsx（兼容所有Excel版本）
    bookSST: false,  // 禁用共享字符串表，提升性能
    type: 'array'    // 输出类型：数组（适配FileSaver）
  });

  try {
    // 下载文件（FileSaver 核心方法）
    FileSaver.saveAs(
      new Blob([wbout], { type: 'application/octet-stream' }),
      `${fileName}.xlsx`
    );
  } catch (e) {
    if (typeof console !== 'undefined') console.error('Excel 导出失败：', e);
  }
  return wbout;
}

/**
 * 多 Sheet 导出 Excel 方法（扩展用法，适用于多模块数据导出）
 * @param {Array} sheets 多 Sheet 配置 [{header: [], data: [], sheetName: ''}, ...]
 * @param {String} fileName 导出文件名
 */
export function export_multi_sheet_excel({ sheets, fileName = 'multi-sheet-excel' }) {
  if (!Array.isArray(sheets) || sheets.length === 0) {
    throw new Error('多Sheet配置sheets必须为非空数组');
  }

  const wb = XLSX.utils.book_new();
  // 遍历生成多个 Sheet
  sheets.forEach(sheet => {
    const { header, data, sheetName = 'Sheet' } = sheet;
    const filterData = data.map(item => header.map(key => item[key] || ''));
    const ws = XLSX.utils.aoa_to_sheet([header, ...filterData]);
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
  });

  const wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: false, type: 'array' });
  try {
    FileSaver.saveAs(
      new Blob([wbout], { type: 'application/octet-stream' }),
      `${fileName}.xlsx`
    );
  } catch (e) {
    if (typeof console !== 'undefined') console.error('多Sheet Excel 导出失败：', e);
  }
  return wbout;
}

/**
 * 适配 Element UI Table 直接导出（根据表格列配置自动生成表头/数据）
 * @param {Object} tableEl Element UI Table 实例（$refs.xxx）
 * @param {String} fileName 导出文件名
 * @param {Array} ignoreColumns 忽略导出的列prop ['id', 'operation']
 */
export function export_el_table_excel({
  tableEl,
  fileName = 'table-excel',
  ignoreColumns = []
}) {
  if (!tableEl) {
    throw new Error('请传入Element UI Table的$refs实例');
  }

  // 从 Table 列配置中提取表头和数据键名（过滤忽略列）
  const columns = tableEl.columns.filter(col => !ignoreColumns.includes(col.prop));
  const header = columns.map(col => col.label); // 表头：列的显示名称
  const propList = columns.map(col => col.prop); // 数据键名：列的prop属性

  // 提取 Table 数据源（过滤忽略列）
  const data = tableEl.data.map(item =>
    propList.map(prop => {
      // 处理格式化列（如状态值转文字：0→禁用，1→启用）
      const col = columns.find(c => c.prop === prop);
      return col.formatter ? col.formatter(item, col) : (item[prop] || '');
    })
  );

  // 调用单 Sheet 导出方法
  export_json_to_excel({ header, data, fileName });
}

// 兼容旧版调用方式（与之前论坛代码中的 export_json_to_excel 保持一致）
export default export_json_to_excel;
