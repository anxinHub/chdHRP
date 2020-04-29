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
import com.chd.hrp.acc.entity.AccWageTaxSet;

/**
* @Title. @Description.
* 个人所得税起征点<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 添加AccWageTaxSet
	 * @param AccWageTaxSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量添加AccWageTaxSet
	 * @param  AccWageTaxSet entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 查询AccWageTaxSet分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageTaxSet>
	 * @throws DataAccessException
	*/
	public List<AccWageTaxSet> queryAccWageTaxSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 个人所得税起征点<BR> 查询AccWageTaxSet所有数据
	 * @param  entityMap
	 * @return List<AccWageTaxSet>
	 * @throws DataAccessException
	*/
	public List<AccWageTaxSet> queryAccWageTaxSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 查询AccWageTaxSetByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageTaxSet queryAccWageTaxSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 删除AccWageTaxSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量删除AccWageTaxSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 更新AccWageTaxSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量更新AccWageTaxSet
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
