/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageTax;

/**
* @Title. @Description.
* 个人所得税税率<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 添加AccWageTax
	 * @param AccWageTax entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量添加AccWageTax
	 * @param  AccWageTax entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询AccWageTax分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageTax>
	 * @throws DataAccessException
	*/
	public List<AccWageTax> queryAccWageTax(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询AccWageTax所有数据
	 * @param  entityMap
	 * @return List<AccWageTax>
	 * @throws DataAccessException
	*/
	public List<AccWageTax> queryAccWageTax(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询AccWageTaxByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageTax queryAccWageTaxByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 删除AccWageTax
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量删除AccWageTax
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 个人所得税税率<BR> 更新AccWageTax
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量更新AccWageTax
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询最后一天终点数
	 * @param  entityMap 
	 * @return AccWageTax
	 * @throws DataAccessException
	*/
	public List<AccWageTax> queryAccWageTaxEnds(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
