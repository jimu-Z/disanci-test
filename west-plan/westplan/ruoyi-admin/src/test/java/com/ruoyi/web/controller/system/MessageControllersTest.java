package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.IMessageService;
import com.ruoyi.system.service.IUserMessageService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Map;

public class MessageControllersTest {

    private LoginUser normalLoginUser;

    @Before
    public void setUp() {
        SysUser user = new SysUser();
        user.setUserId(2L);
        normalLoginUser = new LoginUser();
        normalLoginUser.setUser(user);
    }

    @Test
    public void messageDelete_shouldReturnFail_whenMessageNotFound() throws Exception {
        IMessageService messageService = Mockito.mock(IMessageService.class);
        TestMessageController controller = new TestMessageController(normalLoginUser);
        inject(controller, "messageService", messageService);
        Mockito.when(messageService.getMessageById(100L)).thenReturn(null);

        Map<String, Object> result = controller.delete(100L);

        Assert.assertEquals(500, result.get("code"));
    }

    @Test
    public void userMessageDelete_shouldReturnFail_whenMessageNotFound() throws Exception {
        IUserMessageService userMessageService = Mockito.mock(IUserMessageService.class);
        TestUserMessageController controller = new TestUserMessageController(normalLoginUser);
        inject(controller, "userMessageService", userMessageService);
        Mockito.when(userMessageService.getUserMessageById(101L)).thenReturn(null);

        Map<String, Object> result = controller.delete(101L);

        Assert.assertEquals(500, result.get("code"));
    }

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field field = null;
        Class<?> current = target.getClass();
        while (current != null) {
            try {
                field = current.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
        field.setAccessible(true);
        field.set(target, value);
    }

    private static class TestMessageController extends MessageController {
        private final LoginUser loginUser;

        private TestMessageController(LoginUser loginUser) {
            this.loginUser = loginUser;
        }

        @Override
        public LoginUser getLoginUser() {
            return loginUser;
        }
    }

    private static class TestUserMessageController extends UserMessageController {
        private final LoginUser loginUser;

        private TestUserMessageController(LoginUser loginUser) {
            this.loginUser = loginUser;
        }

        @Override
        public LoginUser getLoginUser() {
            return loginUser;
        }
    }
}
