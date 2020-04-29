/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCashItem;

/**
* @Title. @Description.
* 现金流量项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashItemService {

	/**
	 * @Description 
	 * 现金流量项目<BR> 添加AccCashItem
	 * @param AccCashItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量添加AccCashItem
	 * @param  AccCashItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashItemByVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 查询AccCashItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCashItem queryAccCashItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 删除AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量删除AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 更新AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 批量更新AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCashItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 导入AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCashItem(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 现金流量项目<BR> 继承AccCashItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String insertBatchAccCashItem(Map<String, Object> entityMap)throws DataAccessException;
	
}
