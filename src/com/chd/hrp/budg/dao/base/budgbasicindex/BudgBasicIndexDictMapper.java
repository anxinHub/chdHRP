/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgbasicindex;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgBasicIndexDeptSet;
import com.chd.hrp.budg.entity.BudgBasicIndexDict;
/**
 * 
 * @Description:
 * 基本指标
 * @Table:
 * BUDG_BASIC_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgBasicIndexDictMapper extends SqlMapper{
	/**
	 * 判断指标名称是否被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/***
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgBasicIndexDeptSet(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgBasicIndexDeptSet(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * @Description 
	 * 添加基本指标对应科室维护<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public int addBatchBudgBasicIndexDeptSet(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 删除基本指标对应科室维护<BR>
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBudgBasicIndexDeptSet(Map<String, Object> map) throws DataAccessException ;
	

}
