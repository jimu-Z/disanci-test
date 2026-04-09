package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.CommonInteractMapper;
import com.ruoyi.system.domain.CommonInteract;
import com.ruoyi.system.service.ICommonInteractService;

/**
 * 通用点赞/收藏Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
@Service
public class CommonInteractServiceImpl implements ICommonInteractService 
{
    @Autowired
    private CommonInteractMapper commonInteractMapper;

    /**
     * 查询通用点赞/收藏
     * 
     * @param id 通用点赞/收藏主键
     * @return 通用点赞/收藏
     */
    @Override
    public CommonInteract selectCommonInteractById(Long id)
    {
        return commonInteractMapper.selectCommonInteractById(id);
    }

    /**
     * 查询通用点赞/收藏列表
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 通用点赞/收藏
     */
    @Override
    public List<CommonInteract> selectCommonInteractList(CommonInteract commonInteract)
    {
        return commonInteractMapper.selectCommonInteractList(commonInteract);
    }

    /**
     * 新增通用点赞/收藏
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 结果
     */
    @Override
    public int insertCommonInteract(CommonInteract commonInteract)
    {
        commonInteract.setCreateTime(DateUtils.getNowDate());
        return commonInteractMapper.insertCommonInteract(commonInteract);
    }

    /**
     * 修改通用点赞/收藏
     * 
     * @param commonInteract 通用点赞/收藏
     * @return 结果
     */
    @Override
    public int updateCommonInteract(CommonInteract commonInteract)
    {
        return commonInteractMapper.updateCommonInteract(commonInteract);
    }

    /**
     * 批量删除通用点赞/收藏
     * 
     * @param ids 需要删除的通用点赞/收藏主键
     * @return 结果
     */
    @Override
    public int deleteCommonInteractByIds(Long[] ids)
    {
        return commonInteractMapper.deleteCommonInteractByIds(ids);
    }

    /**
     * 删除通用点赞/收藏信息
     * 
     * @param id 通用点赞/收藏主键
     * @return 结果
     */
    @Override
    public int deleteCommonInteractById(Long id)
    {
        return commonInteractMapper.deleteCommonInteractById(id);
    }
}
