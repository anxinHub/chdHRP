/*
 *
 */package com.chd.hrp.htcg.dao.info;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcgRecipeTypeMapper extends SqlMapper{
	
    /**医嘱分类添加方法
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public int addHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;
	/**医嘱分类批量添加方法
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public int addBatchHtcgRecipeType(List<Map<String,Object>> list)throws DataAccessException;
	
	/**医嘱分类查询
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public List<HtcgRecipeType> queryHtcgRecipeType(Map<String, Object> entityMap);
	
	/**医嘱分类分页查询
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public List<HtcgRecipeType> queryHtcgRecipeType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**医嘱分类查询bycode
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public HtcgRecipeType queryHtcgRecipeTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 医嘱分类删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 医嘱分类批量删除
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchHtcgRecipeType(List<Map<String,Object>> list)throws DataAccessException;

	/**
	 * 医嘱分类修改
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;

}
