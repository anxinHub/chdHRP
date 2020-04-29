/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiType;

public interface AccBusiTypeMapper extends SqlMapper{
	
	/**
	 * 查询业务类型
	 * 不分页
	 * 
	 * */
	public List<AccBusiType> queryAccBusiType(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询业务类型
	 * 分页
	 * 
	 * */
	public List<AccBusiType> queryAccBusiType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 更改业务类型是否自动凭证
	 * 
	 * */
	public int updateAccBusiTypeForIsVouch(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询业务类型
	 * 不分页
	 * 
	 * */
	public List<AccBusiType> queryAccBusiTypeTree(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
