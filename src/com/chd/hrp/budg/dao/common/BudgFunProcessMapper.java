/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 

public interface BudgFunProcessMapper extends SqlMapper{
	
	/**
	 * 函数 取值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> execFunSql(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 函数编码 查询函数信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryFunInfo(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
