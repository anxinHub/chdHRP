package com.chd.hrp.acc.service.tell;

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/



import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBankCheck;

/**
* @Title. @Description.
* 银行对账单<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankCheckService {

	/**
	 * @Description  
	 * 银行对账单<BR> 添加AccBankCheck
	 * @param AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addAccBankCheckInstall(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 银行对账单<BR> 批量添加AccBankCheck
	 * @param  AccBankCheck entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBankCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck查询
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccBankCheckAndSumPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheckByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccBankCheck queryAccBankCheckByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量删除AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量更新AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 导入AccBankCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Map<String, Object> importAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccTellDayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccBankCheckCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
