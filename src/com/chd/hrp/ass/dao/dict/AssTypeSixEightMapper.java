
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssTypeSixEight;
/**
 * 
 * @Description:
 * 050104 68分类字典
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 


public interface AssTypeSixEightMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateAssTypeSixEightIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return AssUsageDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteAssTypeSixEightBySuperCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050104 68分类字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssTypeSixEight> queryAssTypeSixEight(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050104 68分类字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssTypeSixEight> queryAssTypeSixEight(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<AssTypeSixEight> queryAssSixEightChild(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return AssUsageDict
	 * @throws DataAccessException
	*/
	public AssTypeSixEight queryAssTypeSixEightByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 判断编码or名称重复  zhaon
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AssTypeSixEight queryAssTypeSixEightByCodeOrName(Map<String, Object> mapVo)throws DataAccessException;
	
	
	
	
}
