/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgFunPara;
/**
 * 
 * @Description:
 * 自定义分解系数
 * @Table:
 * budg_resolve_data_dict
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgResolveDataDictMapper extends SqlMapper{
	/**
	 * 校验编码是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 校验名称是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询  自定义分解系数 是否被引用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsUsed(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量查询  自定义分解系数 是否被引用
	 * @param entityList
	 * @return
	 */
	public String queryIsUsedBatch(List<Map<String, Object>> entityList) throws DataAccessException;

	
}
