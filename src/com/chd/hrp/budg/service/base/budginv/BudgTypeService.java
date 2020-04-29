/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budginv;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgType;

/**
 * 
 * @Description:
 * 04103 物资分类字典
 * @Table:
 * MAT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgTypeService {
	

	/**
	 * @Description 查询MatType菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgTypeByTree(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBudgType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBudgType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBudgType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String saveBudgType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return MatType
	 * @throws DataAccessException
	*/
	public BudgType queryBudgTypeById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatType
	 * @throws DataAccessException
	*/
	public BudgType queryBudgTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatType
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryBudgTypeByCodeName(Map<String,Object> entityMap)throws DataAccessException;
}
