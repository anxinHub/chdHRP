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
import com.chd.hrp.acc.entity.AccEmpAccount;

/**
* @Title. @Description.
* 职工账号<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpAccountMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工账号<BR> 添加AccEmpAccount
	 * @param AccEmpAccount entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量添加AccEmpAccount
	 * @param  AccEmpAccount entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount分页
	 * @param  entityMap RowBounds
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	*/
	public List<AccEmpAccount> queryAccEmpAccount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount所有数据
	 * @param  entityMap
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	*/
	public List<AccEmpAccount> queryAccEmpAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount所有数据
	 * 职工联表不控制是否停用,只根据职工id和变更号联表查询 
	 * @param  entityMap
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	 */
	public List<AccEmpAccount> queryAccEmpAccountList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccount所有数据  分页
	 * 职工联表不控制是否停用,只根据职工id和变更号联表查询 
	 * @param  entityMap RowBounds
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	 */
	public List<AccEmpAccount> queryAccEmpAccountList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccountByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEmpAccount queryAccEmpAccountByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 查询AccEmpAccountByEmp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEmpAccount queryAccEmpAccountByEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 删除AccEmpAccount
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量删除AccEmpAccount
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工账号<BR> 更新AccEmpAccount
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccEmpAccount(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 批量更新AccEmpAccount
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccEmpAccount(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工账号<BR> 
	 * @param  entityMap RowBounds
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	*/
	public List<AccEmpAccount> queryAccEmpAccountIndex(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工账号<BR> 
	 * @param  entityMap
	 * @return List<AccEmpAccount>
	 * @throws DataAccessException
	*/
	public List<AccEmpAccount> queryAccEmpAccountIndex(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccEmpAccount queryAccEmpAccountIndexByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int queryAccEmpAccountByBank(Map<String,Object> entityMap)throws DataAccessException;
	
	public int queryAccEmpAccountByType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int queryAccEmpAccountCount(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccEmpAccount> queryAccEmpAccountByInter(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccEmpAccountPrint(Map<String,Object> entityMap) throws DataAccessException;

	public void updateAccEmpAttrIdNumber(List<Map<String, Object>> addList);
	
}
