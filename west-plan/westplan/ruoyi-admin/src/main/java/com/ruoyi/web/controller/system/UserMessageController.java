package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.UserMessage;
import com.ruoyi.system.service.IUserMessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/userMessage")
public class UserMessageController extends BaseController {
    @Resource
    private IUserMessageService userMessageService;


    /**
     * 用户查询自己全部的留言
     * @param userMessage
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo UserList(UserMessage userMessage){
        Long userId = getLoginUser().getUser().getUserId();
        userMessage.setUserId(userId);
        startPage();
        List<UserMessage> list = userMessageService.getAll(userMessage);
        return getDataTable(list);
    }
    /**
     * 用户查询自己全部的回复
     * @param userMessage
     * @return
     */
    @GetMapping("/replyList")
    public TableDataInfo rePlyList(UserMessage userMessage){
        Long userId = getLoginUser().getUser().getUserId();
        userMessage.setReceiveUserId(userId);
        startPage();
        List<UserMessage> list = userMessageService.getRePlyAll(userMessage);
        return getDataTable(list);
    }

    /**
     * 给好友留言
     * @param userMessage
     * @return
     */
    @Log(title = "给用户留言", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult addPro(@RequestBody UserMessage userMessage ){
        // 自动获取用户IP作为标识
        Long userId = getLoginUser().getUser().getUserId();
        userMessage.setUserId(userId);
        userMessage.setNickname(getLoginUser().getUser().getNickName());
        return toAjax( userMessageService.add(userMessage));
    }

    /**
     * 用户查自己的留言
     * @return
     */
    @GetMapping("/my")
    public  List<UserMessage>  my(  ){
        Long userId = getLoginUser().getUser().getUserId();
        List<UserMessage> list = userMessageService.getMy(userId) ;
        return list;
    }
    /**
     * 删除留言
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Log(title = "留言", businessType = BusinessType.DELETE)
    public Map<String,Object> delete(@PathVariable Long id){
        boolean flag;
        //只能删除自己的留言，如果不是管理员，则不能删除
        if (!getLoginUser().getUser().isAdmin() ){
            //根据id查询留言，判断留言id与参数id是否一致，一致则删除，否则返回失败
            UserMessage userMessage = userMessageService.getUserMessageById(id);
            if (userMessage == null)
                flag = false;
            else if( userMessage.getUserId().equals(getLoginUser().getUser().getUserId()))
                flag = userMessageService.delete(id);
            else
                flag = false;
        }else
            flag = userMessageService.delete(id);
        return resp(flag,"删除成功","删除失败");
    }
    /**
     * 回复留言
     * @param userMessage
     * @return
     */
    @PostMapping("/reply")
    public AjaxResult reply(@RequestBody UserMessage userMessage){

        return toAjax( userMessageService.reply(userMessage));
    }
    // 统一返回
    private Map<String,Object> resp(boolean flag, String ok, String no){
        Map<String,Object> res = new HashMap<>();
        res.put("code",flag?200:500);
        res.put("msg",flag?ok:no);
        return res;
    }


}
