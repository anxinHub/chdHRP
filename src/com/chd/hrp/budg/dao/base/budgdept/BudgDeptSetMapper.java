
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgdept;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 付费机制
 * @Table:
 * HEALTH_INSURANCE_PAY_MODE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgDeptSetMapper extends SqlMapper{
	
	/**
	 * 查询部门ID否存在或已停用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDeptIdExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询付费机制编码是否存在或已停用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryTypeCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据主键查询数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;;
	
}
