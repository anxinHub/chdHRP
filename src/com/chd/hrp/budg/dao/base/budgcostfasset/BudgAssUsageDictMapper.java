
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgcostfasset;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.budg.entity.BudgAssUsageDict;
/**
 * 
 * @Description:
 * 050104 资产用途
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgAssUsageDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050104 资产用途<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssUsageDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050104 资产用途<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssUsageDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050104 资产用途<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssUsageDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050104 资产用途<BR> 
	 * @param  entityMap
	 * @return AssUsageDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssUsageDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050104 资产用途<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssUsageDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050104 资产用途<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssUsageDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050104 资产用途<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssUsageDict> queryAssUsageDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050104 资产用途<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssUsageDict> queryAssUsageDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050104 资产用途<BR> 
	 * @param  entityMap
	 * @return AssUsageDict
	 * @throws DataAccessException
	*/
	public BudgAssUsageDict queryAssUsageDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
