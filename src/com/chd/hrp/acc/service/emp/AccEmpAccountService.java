/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.emp;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccEmpAccount;

/**
* @Title. @Description.
* 职工账号<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpAccountService {

	/**
	 * @Description 
	 * 职工账号<BR> 添加AccEmpAccount
	 * @param AccEmpAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量添加AccEmpAccount
	 * @param  AccEmpAccount entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEmpAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEmpAccountIndex(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccountByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccEmpAccount queryAccEmpAccountByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询queryAccEmpAccountByEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccEmpAccount queryAccEmpAccountByEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 删除AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量删除AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 更新AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量更新AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccEmpAccount queryAccEmpAccountIndexByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccEmpAccountCount(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryAccEmpAccountByInter(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 导入 AccEmpAccount
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccEmpAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
