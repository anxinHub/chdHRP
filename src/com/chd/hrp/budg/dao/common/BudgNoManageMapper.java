/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 生成table_code表的下一个单号（使用于出入库业务）
 * @param entityMap
 * @return
 */	
 

public interface BudgNoManageMapper extends SqlMapper{

	public String queryDay(Map<String, Object> map) throws  DataAccessException;

	public String queryCheckCode(Map<String, Object> map) throws DataAccessException;

	public int queryIsExists(Map<String, Object> map) throws DataAccessException;

	public int updateMaxNo(Map<String, Object> map) throws DataAccessException;

	public String queryMaxCode(Map<String, Object> map) throws DataAccessException;
	
}
