package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 财政基本补助收入支出表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AccItialIncomeService{
	
	/**
	 * @Description 
	 * 查询初始收入<BR> 查询AccItialIncome所有数据
	 * @param  entityMap
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public String queryAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 添加初始收入<BR> 添加AccItialIncome
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加初始收入<BR> 批量添加AccItialIncome
	 * @param  AccItialIncome entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addBatchAccItialIncome(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除初始收入<BR> 删除AccItialIncome
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteBatchAccItialIncome(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 删除初始收入<BR> 删除AccItialIncomeByCode
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String deleteAccItialIncome(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询导入凭证信息<BR> 查询AccItialIncome所有数据
	 * @param  entityMap
	 * @return List<AccItialIncome>
	 * @throws DataAccessException
	*/
	public String queryAccVouchImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印  初始收入
	public List<Map<String, Object>> queryAccItialIncomePrint(Map<String,Object> entityMap)throws DataAccessException;
	
}
