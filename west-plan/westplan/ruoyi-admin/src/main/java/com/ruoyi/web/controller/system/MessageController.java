package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Message;
import com.ruoyi.system.service.IMessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/system/message")
public class MessageController extends BaseController {

        @Resource
        private IMessageService messageService;

        // ========== 管理员接口 ==========
        @PreAuthorize("@ss.hasPermi('system:alumni_honor:reply')")
        @GetMapping("/admin/list")
        public TableDataInfo adminList( Message message){
            startPage();
            List<Message> list =messageService.getAll(message);

            return getDataTable(list);
        }
        @PreAuthorize("@ss.hasPermi('system:alumni_honor:reply')")
        @PostMapping("/admin/reply")
        public AjaxResult reply(@RequestBody Message message){

            return toAjax( messageService.reply(message));
        }

        /**
         * 给学校留言
         * @param message
         * @return
         */
        @Log(title = "留言", businessType = BusinessType.INSERT)
        @PostMapping()
        public AjaxResult add(@RequestBody Message message ){
            // 自动获取用户IP作为标识
            Long userId = getLoginUser().getUser().getUserId();
            message.setUserId(userId);
            message.setNickname(getLoginUser().getUser().getNickName());
            return toAjax( messageService.add(message));
        }


        @GetMapping("/my")
        public  List<Message>  my(  ){
            Long userId = getLoginUser().getUser().getUserId();
            List<Message> list =messageService.getMy(userId) ;
            return list;
        }

        @DeleteMapping("/{id}")
        @Log(title = "留言", businessType = BusinessType.DELETE)
        public Map<String,Object> delete(@PathVariable Long id){
            boolean flag;
            //只能删除自己的留言，如果不是管理员，则不能删除
            if (!getLoginUser().getUser().isAdmin() ){
                //根据id查询留言，判断留言id与参数id是否一致，一致则删除，否则返回失败
                Message message = messageService.getMessageById(id);
                if (message == null)
                    flag = false;
                else if( message.getUserId().equals(getLoginUser().getUser().getUserId()))
                    flag = messageService.delete(id);
                else
                    flag = false;
            }else
             flag = messageService.delete(id);
            return resp(flag,"删除成功","删除失败");
        }



        // 统一返回
        private Map<String,Object> resp(boolean flag, String ok, String no){
            Map<String,Object> res = new HashMap<>();
            res.put("code",flag?200:500);
            res.put("msg",flag?ok:no);
            return res;
        }
    }

