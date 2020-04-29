/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.emp;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccBank;

/**
* @Title. @Description.
* 银行信息<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBankService {

	/**
	 * @Description 
	 * 银行信息<BR> 添加AccBank
	 * @param AccBusiType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量添加AccBank
	 * @param  AccBusiType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBank分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBank(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccBankPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 查询AccBankByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccBank queryAccBankByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 删除AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量删除AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 更新AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行信息<BR> 批量更新AccBank
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccBank(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
