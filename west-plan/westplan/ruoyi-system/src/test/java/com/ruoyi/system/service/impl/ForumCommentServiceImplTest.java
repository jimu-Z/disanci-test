package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ForumComment;
import com.ruoyi.system.mapper.ForumCommentMapper;
import com.ruoyi.system.mapper.ForumPostMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;

public class ForumCommentServiceImplTest {

    private ForumCommentServiceImpl service;
    private ForumCommentMapper forumCommentMapper;
    private ForumPostMapper forumPostMapper;

    @Before
    public void setUp() throws Exception {
        service = new ForumCommentServiceImpl();
        forumCommentMapper = Mockito.mock(ForumCommentMapper.class);
        forumPostMapper = Mockito.mock(ForumPostMapper.class);
        RedisCache redisCache = Mockito.mock(RedisCache.class);

        inject(service, "forumCommentMapper", forumCommentMapper);
        inject(service, "forumPostMapper", forumPostMapper);
        inject(service, "redisCache", redisCache);
    }

    @Test
    public void deleteForumCommentById_shouldDecreasePostCommentCountByDeletedRows() {
        ForumComment root = new ForumComment();
        root.setId(10L);
        root.setPostId(99L);
        root.setUserId(2L);

        Mockito.when(forumCommentMapper.selectByPrimaryKey(10L)).thenReturn(root);
        Mockito.when(forumCommentMapper.selectChildCommentByParentId(Mockito.anyLong()))
                .thenReturn(Collections.emptyList());
        Mockito.when(forumCommentMapper.deleteCommentByParentIds(Mockito.anyList()))
                .thenReturn(3);

        SysUser user = new SysUser();
        user.setUserId(2L);
        LoginUser loginUser = new LoginUser();
        loginUser.setUser(user);

        try (MockedStatic<SecurityUtils> securityUtils = Mockito.mockStatic(SecurityUtils.class)) {
            securityUtils.when(SecurityUtils::getLoginUser).thenReturn(loginUser);
            securityUtils.when(() -> SecurityUtils.hasRole("super_admin")).thenReturn(false);
            securityUtils.when(() -> SecurityUtils.hasRole("common_admin")).thenReturn(false);

            int rows = service.deleteForumCommentById(10L);

            Assert.assertEquals(3, rows);
            Mockito.verify(forumPostMapper, Mockito.times(3))
                    .decrementCount(99L, "comment_count");
        }
    }

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
