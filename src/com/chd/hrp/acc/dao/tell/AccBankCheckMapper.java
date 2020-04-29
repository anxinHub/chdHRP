/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBankCheck;



/**
* @Title. @Description.
* 银行对账单<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankCheckMapper extends SqlMapper{
	
	/**
	 * @Description
	 * 银行对账单<BR> 添加AccBankCheck
	 * @param AccBankCheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addAccBankCheckInstall(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量添加AccBankCheck
	 * @param  AccBankCheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return List<AccBankCheck>
	 * @throws DataAccessException
	*/
	public List<AccBankCheck> queryAccBankCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheck所有数据
	 * @param  entityMap
	 * @return List<AccBankCheck>
	 * @throws DataAccessException
	*/
	public List<AccBankCheck> queryAccBankCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询所有数据并合计借贷方 AccBankCheck分页
	 * @param  entityMap RowBounds
	 * @return List<AccBankCheck>
	 * @throws DataAccessException
	*/
	public List<AccBankCheck> queryAccBankCheckAndSum(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询所有数据并合计借贷方 打印queryAccBankCheckAndSumPrint
	 * @param  entityMap
	 * @return List<AccBankCheck>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccBankCheckAndSumPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询所有数据并合计借贷方 AccBankCheck
	 * @param  entityMap
	 * @return List<AccBankCheck>
	 * @throws DataAccessException
	*/
	public List<AccBankCheck> queryAccBankCheckAndSum(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheckByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccBankCheck queryAccBankCheckByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 删除AccBankCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量删除AccBankCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 银行对账单<BR> 更新AccBankCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccBankCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行对账单<BR> 批量更新AccBankCheck
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccBankCheck(List<Map<String, Object>> entityMap)throws DataAccessException;

	public AccBankCheck queryAccBankCheckCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int saveAccBankBalInit(Map<String,Object> entityMap)throws DataAccessException;
	
	public String selectAccBankBalInit(Map<String,Object> entityMap)throws DataAccessException;
	
	//获取科目字典用于导入
	public List<Map<String, Object>> queryAccSubjForImpl(Map<String, Object> map) throws DataAccessException;
	//获取结算方式字典用于导入
	public List<Map<String, Object>> queryAccPayTypeForImpl(Map<String, Object> map) throws DataAccessException;
}
