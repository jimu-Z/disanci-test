package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.CommonInteract;

/**
 * 通用点赞/收藏Service接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface ICommonInteractService 
{
    /**
     * 查询通用点赞/收藏
     * 
     * @param id 通用点赞/收藏主键
     * @return 通用点赞/收藏
     */
    CommonInteract selectCommonInteractById(Long id);

    /**
     * 查询通用点赞/收藏列表
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 通用点赞/收藏集合
     */
    List<CommonInteract> selectCommonInteractList(CommonInteract commonInteract);

    /**
     * 新增通用点赞/收藏
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 结果
     */
    int insertCommonInteract(CommonInteract commonInteract);

    /**
     * 修改通用点赞/收藏
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 结果
     */
    int updateCommonInteract(CommonInteract commonInteract);

    /**
     * 批量删除通用点赞/收藏
     * 
     * @param ids 需要删除的通用点赞/收藏主键集合
     * @return 结果
     */
    int deleteCommonInteractByIds(Long[] ids);

    /**
     * 删除通用点赞/收藏信息
     * 
     * @param id 通用点赞/收藏主键
     * @return 结果
     */
    int deleteCommonInteractById(Long id);
}
