package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.AlumniMessage;
import com.ruoyi.system.service.IAlumniMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 校友私信Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/alumni_message")
public class AlumniMessageController extends BaseController
{
    @Autowired
    private IAlumniMessageService alumniMessageService;

    /**
     * 查询校友私信列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:list')")
    @GetMapping("/list")
    public TableDataInfo list(AlumniMessage alumniMessage)
    {
        startPage();
        List<AlumniMessage> list = alumniMessageService.selectAlumniMessageList(alumniMessage);
        return getDataTable(list);
    }

    /**
     * 导出校友私信列表
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:export')")
    @Log(title = "校友私信", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AlumniMessage alumniMessage)
    {
        List<AlumniMessage> list = alumniMessageService.selectAlumniMessageList(alumniMessage);
        ExcelUtil<AlumniMessage> util = new ExcelUtil<AlumniMessage>(AlumniMessage.class);
        util.exportExcel(response, list, "校友私信数据");
    }

    /**
     * 获取校友私信详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(alumniMessageService.selectAlumniMessageById(id));
    }

    /**
     * 新增校友私信
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:add')")
    @Log(title = "校友私信", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AlumniMessage alumniMessage)
    {
        return toAjax(alumniMessageService.insertAlumniMessage(alumniMessage));
    }

    /**
     * 修改校友私信
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:edit')")
    @Log(title = "校友私信", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AlumniMessage alumniMessage)
    {
        return toAjax(alumniMessageService.updateAlumniMessage(alumniMessage));
    }

    /**
     * 删除校友私信
     */
    @PreAuthorize("@ss.hasPermi('system:alumni_message:remove')")
    @Log(title = "校友私信", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(alumniMessageService.deleteAlumniMessageByIds(ids));
    }
}
