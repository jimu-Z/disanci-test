package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.CommonInteract;
import org.apache.ibatis.annotations.Param;

/**
 * 通用点赞/收藏Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface CommonInteractMapper 
{
    // 基础CRUD
    int deleteByPrimaryKey(Long id);
    int insertCommonInteract(CommonInteract record);

    CommonInteract selectCommonInteractById(Long id);
    int updateCommonInteract(CommonInteract record);
  List<CommonInteract>  selectCommonInteractList(CommonInteract record);

    // 自定义方法
    /** 查询用户互动记录（判断是否已点赞/收藏） */
    CommonInteract selectInteractByUser(
            @Param("userId") Long userId,
            @Param("targetType") Integer targetType,
            @Param("targetId") Long targetId,
            @Param("interactType") Integer interactType
    );
    /** 删除用户互动记录 */
    int deleteInteractByUser(
            @Param("userId") Long userId,
            @Param("targetType") Integer targetType,
            @Param("targetId") Long targetId,
            @Param("interactType") Integer interactType
    );
    /** 根据目标ID批量删除互动记录 */
    int deleteInteractByTargetIds(
            @Param("targetType") Integer targetType,
            @Param("targetIds") Long[] targetIds
    );

    int deleteCommonInteractByIds(Long[] ids);

    int deleteCommonInteractById(Long id);
}
