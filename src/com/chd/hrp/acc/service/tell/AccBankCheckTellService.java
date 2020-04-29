package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccBankCheckTellService {
	
	/**
	 * @Description 
	 * 银行对账：银行对账单<BR> 查询AccBankCheck
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String collectAccBankCheckTell(Map<String,Object> entityMap)throws DataAccessException;//存储过程
	public String queryAccBankCheckTell(Map<String,Object> entityMap)throws DataAccessException;//普通调用
	
	/**
	 * @Description 
	 * 银行对账：银行对账单<BR> 打印AccBankCheck
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/	
	public List<Map<String, Object>> collectqueryAccBankCheckTellPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账：银行对账单<BR> 添加AccBankCheck
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/	
	public String addAccBankCheckTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账：银行对账单<BR> 删除AccBankCheck
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/	
	public String deleteBatchAccBankCheckTell(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账：银行对账单<BR>修改AccBankCheck
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateAccBankCheckTell(Map<String,Object> entityMap)throws DataAccessException;
	public String addAccBankCheckTellBatch(List<Map<String,Object>> entityList)throws DataAccessException;
	
}
