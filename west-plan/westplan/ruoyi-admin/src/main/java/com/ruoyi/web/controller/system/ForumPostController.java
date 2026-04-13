package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.system.domain.dto.ForumAudit;
import com.ruoyi.system.domain.dto.ForumPostAddDTO;
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
import com.ruoyi.system.domain.ForumPost;
import com.ruoyi.system.service.IForumPostService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 论坛帖子Controller
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@RestController
@RequestMapping("/system/forum_post")
public class ForumPostController extends BaseController
{
    @Autowired
    private IForumPostService forumPostService;

    /**
     * 分页查询帖子列表（管理员/普通用户，自动做数据权限过滤）
     */
    @ApiOperation("分页查询帖子列表")
    @GetMapping("/list")
    public TableDataInfo list(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            ForumPost forumPost
    ) {
        startPage();
       List<ForumPost> list=forumPostService.selectPostPage(forumPost);
        return getDataTable(list) ;
    }

    /**
     * 查询论坛首页置顶热点帖子（无需权限）
     */
    @ApiOperation("查询置顶热点帖子")
    @GetMapping("/top/hot")
    public AjaxResult selectTopHotPostList() {
        return AjaxResult.success(forumPostService.selectTopHotPostList());
    }

    /**
     * 查询最新发布的帖子（无需权限）
     */
    @ApiOperation("查询最新帖子")
    @GetMapping("/new/list")
    public AjaxResult selectNewPostList(
            @ApiParam(value = "查询条数", defaultValue = "10") @RequestParam(defaultValue = "10") Integer limit
    ) {
        Long userId = getLoginUser().getUser().getUserId();
        return AjaxResult.success(forumPostService.selectNewPostList(userId,limit));
    }

    /**
     * 导出帖子列表（管理员）
     */
    @ApiOperation("导出帖子列表")
    @PreAuthorize("@ss.hasPermi('system:forum_post:export')")
    @Log(title = "论坛帖子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ForumPost forumPost) {
        List<ForumPost> list = forumPostService.selectPostPage( forumPost) ;
        ExcelUtil<ForumPost> util = new ExcelUtil<>(ForumPost.class);
        util.exportExcel(response, list, "论坛帖子数据");
    }

    /**
     * 根据帖子ID查询详情（含浏览量自增，限流处理）
     */
    @ApiOperation("根据ID查询帖子详情")
    @GetMapping("/{id}")
    public AjaxResult getInfo(
            @ApiParam(value = "帖子ID", required = true) @PathVariable Long id
    ) {
        return AjaxResult.success(forumPostService.selectPostDetail(id));
    }
    /**
     * 根据帖子ID查询详情（含浏览量自增，限流处理）
     */
    @ApiOperation("根据userID查询帖子 ")
    @GetMapping("/myPosts")
    public AjaxResult myPosts(  @RequestParam(defaultValue = "10") Integer limit ) {
        Long userId = getLoginUser().getUser().getUserId();

        return AjaxResult.success(forumPostService.getMyPosts(userId,limit));
    }
    /**
     * 发布论坛帖子（登录用户即可）
     */
    @ApiOperation("发布论坛帖子")
    @Log(title = "论坛帖子", businessType = BusinessType.INSERT)
    @RepeatSubmit(interval = 3000)
    @PostMapping
    public AjaxResult add(
            @ApiParam(value = "帖子发布参数", required = true) @Validated @RequestBody ForumPostAddDTO dto
    ) {
        return toAjax(forumPostService.addForumPost(dto));
    }

    /**
     * 修改论坛帖子（仅发布者可修改，待审核/驳回状态）
     */
    @ApiOperation("修改论坛帖子")
    @Log(title = "论坛帖子", businessType = BusinessType.UPDATE)
    @RepeatSubmit(interval = 3000)
    @PutMapping
    public AjaxResult edit(   @ApiParam(value = "帖子发布参数", required = true) @Validated @RequestBody ForumPostAddDTO dto) {


        Long postId = dto.getId();
        return toAjax(forumPostService.updateForumPost(dto, postId));
    }

    /**
     * 帖子审核（管理员）
     */
    @ApiOperation("帖子审核")
    @PreAuthorize("@ss.hasPermi('system:forum_post:audit')")
    @Log(title = "论坛帖子", businessType = BusinessType.UPDATE)
    @RepeatSubmit(interval = 2000)
    @PutMapping("/audit")
    public AjaxResult audit(@Validated @RequestBody ForumAudit dto) {
        if (dto.getId() == null) {
            return AjaxResult.error("帖子ID不能为空");
        }
        return toAjax(forumPostService.auditPost(dto.getId(), dto.getAuditStatus(), dto.getAuditRemark()));
    }


    /**
     * 帖子审核（管理员）
     */
    @ApiOperation("帖子审核")
    @PreAuthorize("@ss.hasPermi('system:forum_post:audit')")
    @Log(title = "论坛帖子", businessType = BusinessType.UPDATE)
    @RepeatSubmit(interval = 2000)
    @PutMapping("/batchAudit")
    public AjaxResult batchAudit(@Validated @RequestBody ForumAudit forumAudit) {
        if (forumAudit.getIds() == null || forumAudit.getIds().length == 0) {
            return AjaxResult.error("请选择待审核帖子");
        }
         return toAjax(forumPostService.batchAuditPost(forumAudit));
    }
    /**
     * 帖子置顶/取消置顶（管理员）
     */
    @ApiOperation("帖子置顶/取消置顶")
    @PreAuthorize("@ss.hasPermi('system:forum_post:top')")
    @Log(title = "论坛帖子", businessType = BusinessType.UPDATE)
    @PutMapping("/top")
    public AjaxResult top(
            @ApiParam(value = "帖子ID", required = true) @RequestParam Long id,
            @ApiParam(value = "是否置顶（0否1是）", required = true) @RequestParam Integer isTop
    ) {
        return toAjax(forumPostService.topPost(id, isTop));
    }

    /**
     * 帖子设为热点/取消热点（管理员）
     */
    @ApiOperation("帖子设为热点/取消热点")
    @PreAuthorize("@ss.hasPermi('system:forum_post:hot')")
    @Log(title = "论坛帖子", businessType = BusinessType.UPDATE)
    @PutMapping("/hot")
    public AjaxResult hot(
            @ApiParam(value = "帖子ID", required = true) @RequestParam Long id,
            @ApiParam(value = "是否热点（0否1是）", required = true) @RequestParam Integer isHot
    ) {
        return toAjax(forumPostService.hotPost(id, isHot));
    }

    /**
     * 批量删除帖子（管理员/发布者）
     */
    @ApiOperation("批量删除帖子")
    @Log(title = "论坛帖子", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(
            @ApiParam(value = "帖子ID数组", required = true) @PathVariable Long[] ids
    ) {
        return toAjax(forumPostService.deletePostByIds(ids));
    }

    /**
     * 帖子点赞/取消点赞（登录用户即可）
     */
    @ApiOperation("帖子点赞/取消点赞")
    @PostMapping("/like/{postId}")
    public AjaxResult likePost(
            @ApiParam(value = "帖子ID", required = true) @PathVariable Long postId
    ) {
        forumPostService.likePost(postId);
        return AjaxResult.success("操作成功");
    }

    /**
     * 帖子收藏/取消收藏（登录用户即可）
     */
    @ApiOperation("帖子收藏/取消收藏")
    @PostMapping("/collect/{postId}")
    public AjaxResult collectPost(
            @ApiParam(value = "帖子ID", required = true) @PathVariable Long postId
    ) {
        forumPostService.collectPost(postId);
        return AjaxResult.success("操作成功");
    }
    @PreAuthorize("@ss.hasPermi('system:forum_post:upload')")
    @PostMapping("/uploadImage")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            String path= RuoYiConfig.getPicturePath()+"/"+getUserId();
            String url = FileUploadUtils.upload(path, file, MimeTypeUtils.IMAGE_EXTENSION, false);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);

            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
}
