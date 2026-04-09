package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.AlumniFeeling;
import com.ruoyi.system.domain.dto.FeelingWithUser;
import org.apache.ibatis.annotations.Param;

/**
 * 校友工作感悟Mapper接口
 * 
 * @author ruoyi
 * @date 2026-02-01
 */
public interface AlumniFeelingMapper 
{
    /**
     * 查询校友工作感悟
     * 
     * @param id 校友工作感悟主键
     * @return 校友工作感悟
     */
    AlumniFeeling selectAlumniFeelingById(Long id);

    /**
     * 查询校友工作感悟列表
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 校友工作感悟集合
     */
    List<AlumniFeeling> selectAlumniFeelingList(AlumniFeeling alumniFeeling);

    /**
     * 新增校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    int insertAlumniFeeling(AlumniFeeling alumniFeeling);

    /**
     * 修改校友工作感悟
     * 
     * @param alumniFeeling 校友工作感悟
     * @return 结果
     */
    int updateAlumniFeeling(AlumniFeeling alumniFeeling);

    /**
     * 删除校友工作感悟
     * 
     * @param id 校友工作感悟主键
     * @return 结果
     */
    int deleteAlumniFeelingById(Long id);

    /**
     * 批量删除校友工作感悟
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAlumniFeelingByIds(Long[] ids);
    /**
     * 查询感悟总数
     */
    Integer selectFeelingCount(AlumniFeeling alumniFeeling);


    /**
     * 更新点赞数
     */
    int updateLikeCount(@Param("id") Long id, @Param("count") Integer count);

    /**
     * 更新评论数
     */
    int updateCommentCount(@Param("id") Long id, @Param("count") Integer count);

    void incrementViewCount(Long id);

    List<FeelingWithUser> selectFeelingWithUserList(AlumniFeeling alumniFeeling);
}
