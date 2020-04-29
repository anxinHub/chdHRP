/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgoperation;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 运营尺度
 * @Table:
 * BUDG_OPERATION_MEASURE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgOperationMeasureMapper extends SqlMapper{

	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询数据是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量 查询添加数据  编码是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCodeExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 批量 查询添加数据  名称是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryNameExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
}
