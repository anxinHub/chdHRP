/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.tell;
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


public interface AccBankAccountService { 

	/**
	 * @Description 
	 * 银行对账单<BR> 添加AccBankAccount
	 * @param AccBankAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBankAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量添加AccBankAccount
	 * @param  AccBankAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccBankAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankAccount分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBankAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankAccountByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccBankCheck queryAccBankAccountByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 打印
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccBankAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 银行对账单<BR> 删除AccBankAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBankAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量删除AccBankAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBankAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 更新AccBankAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBankAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量更新AccBankAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBankAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 导入AccBankAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccBankAccount(List<String[]> entityMap)throws DataAccessException;

	String selectAccBankBalInit(Map<String, Object> entityMap)
			throws DataAccessException;
	
}
