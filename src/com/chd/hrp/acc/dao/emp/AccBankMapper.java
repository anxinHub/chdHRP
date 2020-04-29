/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBank;

/**
* @Title. @Description.
* 银行信息<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 银行信息<BR> 添加AccBank
	 * @param AccBusiType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量添加AccBank
	 * @param  AccBusiType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBank分页
	 * @param  entityMap RowBounds
	 * @return List<AccBank>
	 * @throws DataAccessException
	*/
	public List<AccBank> queryAccBank(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBank所有数据
	 * @param  entityMap
	 * @return List<AccBank>
	 * @throws DataAccessException
	*/
	public List<AccBank> queryAccBank(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBankByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccBank queryAccBankByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccBank queryAccBankBySortCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 删除AccBank
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量删除AccBank
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 银行信息<BR> 更新AccBank
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量更新AccBank
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccBankPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
