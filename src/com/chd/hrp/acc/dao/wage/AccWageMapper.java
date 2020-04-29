/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWage;

/**
* @Title. @Description.
* 工资套<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资套<BR> 添加AccWage
	 * @param AccWage entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 批量添加AccWage
	 * @param  AccWage entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWage(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccWage分页
	 * @param  entityMap RowBounds
	 * @return List<AccWage>
	 * @throws DataAccessException
	*/
	public List<AccWage> queryAccWage(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资套<BR> 查询AccWage所有数据
	 * @param  entityMap
	 * @return List<AccWage>
	 * @throws DataAccessException
	*/
	public List<AccWage> queryAccWage(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccWageByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWage queryAccWageByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 删除AccWage
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 批量删除AccWage
	 * @param  mapVo 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWage(Map<String, Object> mapVo)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资套<BR> 更新AccWage
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWage(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套<BR> 批量更新AccWage
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWage(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccWage queryAccWageByCal(Map<String,Object> entityMap)throws DataAccessException;

	public void deleteByAccWage(Map<String, Object> mapVo);
	
}
