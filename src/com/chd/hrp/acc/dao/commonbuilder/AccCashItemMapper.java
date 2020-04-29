/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashItem;
import com.chd.hrp.acc.entity.AccCashType;

/**
* @Title. @Description.
* 现金流量项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 添加AccCashItem
	 * @param AccCashItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量添加AccCashItem
	 * @param  AccCashItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccCashItem>
	 * @throws DataAccessException
	*/
	public List<AccCashItem> queryAccCashItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItem所有数据
	 * @param  entityMap
	 * @return List<AccCashItem>
	 * @throws DataAccessException
	*/
	public List<AccCashItem> queryAccCashItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItemByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCashItem queryAccCashItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 删除AccCashItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量删除AccCashItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 现金流量项目<BR> 更新AccCashItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量更新AccCashItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR>继承数据 查询AccCashItem数据
	 * @param  entityMap
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<AccCashItem> queryAccCashItemByExtend(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 所属该现金流量类的 现金流量项
	 * @param accCashType
	 * @return
	 */
	public List<AccCashItem> queryAccCashItemExtend(AccCashType accCashType);

	public List<AccCashItem> queryAccCashItemByVouch(Map<String, Object> entityMap, RowBounds rowBounds);

	public AccCashItem queryAccCashItemByCodeName(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCashItem queryAccCashItemByName(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCashItem queryAccCashItemCodeByAutoCash(Map<String,Object> entityMap)throws DataAccessException;
}
