package com.ruoyi.web.controller.system;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.StudentAdmission;
import com.ruoyi.system.service.IFriendRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 好友Controller
 */
@RestController
@RequestMapping("/system/friendRelation")
public class FriendRelationController extends BaseController {

    @Autowired
    private IFriendRelationService friendRelationService;

    /**
     * 查询我的好友
     * @return
     */
    @GetMapping("/list")
    public AjaxResult selectMySendApply() {
        Long userId = getLoginUser().getUser().getUserId();
        List<StudentAdmission> list = friendRelationService.selectMyRealFriend(userId);
        return AjaxResult.success(list);
    }
    /**
     * 查看好友信息详细信息
     * @return
     */
    @GetMapping()
    public AjaxResult selectMySendApply(@RequestParam Long targetId) {
        Long userId = getLoginUser().getUser().getUserId();
        StudentAdmission studentAdmission = friendRelationService.selectByIdStudentAdmission(userId,targetId);
        return AjaxResult.success(studentAdmission);
    }

    /**
     * 删除好友
     * @param targetId
     * @return
     */
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestParam Long targetId)
    {
        Long userId = getLoginUser().getUser().getUserId();
        return toAjax(friendRelationService.updateApplyStatus(userId, targetId));
    }


}
