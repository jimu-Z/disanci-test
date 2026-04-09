package com.ruoyi.web.controller.system;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.FriendApply;
import com.ruoyi.system.service.IFriendApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好友申请Controller
 */
@RestController
@RequestMapping("/system/friendApply")
public class FriendApplyController extends BaseController {

    @Autowired
    private IFriendApplyService friendApplyService;

    /**
     * 发起好友申请
     * 前端点击“加为好友”时调用
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody FriendApply friendApply) {
        friendApply.setApplyUserId(getLoginUser().getUser().getUserId());
        return toAjax(friendApplyService.insertFriendApply(friendApply));
    }

    /**
     * 获取好友申请详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        // 调用 service 层查询数据，并用 AjaxResult.success 包裹返回给前端
        return AjaxResult.success(friendApplyService.selectFriendApplyById(id));
    }

    /**
     * 获取我收到的好友申请列表
     */
    @GetMapping("/mySendList")
    public AjaxResult getApplyList() {
        Long userId = getLoginUser().getUser().getUserId();
        // userId 同样建议从登录上下文中获取，防止越权查询
        List<FriendApply> list = friendApplyService.getMyReceiveApply(userId);
        return AjaxResult.success(list);
    }

    /**
     * 查询我发出的好友申请（关联目标人信息）
     */
    @GetMapping("/list")
    public AjaxResult selectMySendApply() {
        Long userId = getLoginUser().getUser().getUserId();
        // userId 同样建议从登录上下文中获取，防止越权查询
        List<FriendApply> list = friendApplyService.getMySendApply(userId);
        return AjaxResult.success(list);
    }

    /**
     * 修改好友申请状态（审核：同意/拒绝）
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FriendApply friendApply)
    {
        // 这里的 friendApply 对象应包含：id 和 status (2-同意, 3-拒绝)
        return toAjax(friendApplyService.updateApplyStatus(friendApply));
    }


}
