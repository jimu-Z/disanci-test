package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.ForumBoard;
import com.ruoyi.system.service.IForumBoardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 论坛板块Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/forum_board")
public class ForumBoardController extends BaseController {
    @Autowired
    private IForumBoardService forumBoardService;

    /**
     * 分页查询论坛板块列表（管理员）
     */
    @ApiOperation("分页查询板块列表")
    @PreAuthorize("@ss.hasPermi('system:forum_board:list')")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            ForumBoard forumBoard
    ) {
        startPage(); // 内置分页（底层封装PageHelper）

        List<ForumBoard> list = forumBoardService.selectForumBoardList(forumBoard);
        return getDataTable(list);
    }

    /**
     * 查询所有启用的板块（前端展示，无需权限）
     */
    @ApiOperation("查询启用的板块列表")
    @GetMapping("/enable/list")
    public AjaxResult selectEnableBoardList() {
        return AjaxResult.success(forumBoardService.selectEnableBoardList());
    }

    /**
     * 导出论坛板块列表
     */
    @ApiOperation("导出板块列表")
    @PreAuthorize("@ss.hasPermi('system:forum_board:export')")
    @Log(title = "论坛板块", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumBoard forumBoard) {
        List<ForumBoard> list = forumBoardService.selectForumBoardList(forumBoard);
        ExcelUtil<ForumBoard> util = new ExcelUtil<>(ForumBoard.class);
        util.exportExcel(response, list, "论坛板块数据");
    }

    /**
     * 根据板块ID查询详情
     */
    @ApiOperation("根据ID查询板块详情")
    @PreAuthorize("@ss.hasPermi('system:forum_board:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(
            @ApiParam(value = "板块ID", required = true) @PathVariable Long id
    ) {
        return AjaxResult.success(forumBoardService.selectForumBoardById(id));
    }

    /**
     * 新增论坛板块
     */
    @ApiOperation("新增论坛板块")
    @PreAuthorize("@ss.hasPermi('system:forum_board:add')")
    @Log(title = "论坛板块", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(
            @ApiParam(value = "板块信息", required = true) @Validated @RequestBody ForumBoard forumBoard
    ) {
        return toAjax(forumBoardService.insertForumBoard(forumBoard));
    }

    /**
     * 修改论坛板块
     */
    @ApiOperation("修改论坛板块")
    @PreAuthorize("@ss.hasPermi('system:forum_board:edit')")
    @Log(title = "论坛板块", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(
            @ApiParam(value = "板块信息", required = true) @Validated @RequestBody ForumBoard forumBoard
    ) {
        return toAjax(forumBoardService.updateForumBoard(forumBoard));
    }

    /**
     * 批量修改板块排序
     */
    @ApiOperation("批量修改板块排序")
    @PreAuthorize("@ss.hasPermi('system:forum_board:edit')")
    @Log(title = "论坛板块", businessType = BusinessType.UPDATE)
    @PutMapping("/sort")
    public AjaxResult updateSort(
            @ApiParam(value = "板块列表（含ID和sort）", required = true) @RequestBody List<ForumBoard> boards
    ) {
        return toAjax(forumBoardService.updateBoardSort(boards));
    }

    /**
     * 批量删除论坛板块
     */
    @ApiOperation("批量删除论坛板块")
    @PreAuthorize("@ss.hasPermi('system:forum_board:remove')")
    @Log(title = "论坛板块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(
            @ApiParam(value = "板块ID数组", required = true) @PathVariable Long[] ids
    ) {
        return toAjax(forumBoardService.deleteForumBoardByIds(ids));
    }

    /**
     * 启用/禁用论坛板块
     */
    @ApiOperation("启用/禁用板块")
    @PreAuthorize("@ss.hasPermi('system:forum_board:edit')")
    @Log(title = "论坛板块", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(
            @ApiParam(value = "板块ID", required = true) @RequestParam Long id,
            @ApiParam(value = "状态（0禁用1启用）", required = true) @RequestParam Integer status
    ) {
        ForumBoard board = new ForumBoard();
        board.setId(id);
        board.setStatus(status);
        return toAjax(forumBoardService.updateForumBoard(board));
    }
}
