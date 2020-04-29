/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.tell;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccTell;

/**
* @Title. @Description.
* 出纳账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTellService {

	/**
	 * @Description 
	 * 出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量添加AccTell
	 * @param  AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTell分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTell(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 银行出纳账<BR> 打印
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBankByAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金出纳账<BR> 打印
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTellByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccTell queryAccTellByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 删除AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量删除AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 更新AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量更新AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 导入AccTell
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCashAccount(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 现金出纳账
	 * @param  entityMap 
	 * @return Map
	 * @throws DataAccessException
	*/
	public String queryCashAccountByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR>  摘要字典查询
	 * @param  entityMap 
	 * @return Map
	 * @throws DataAccessException
	*/
	public String queryAccTellSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	//出纳账<BR> 添加ACC_TELL_SUMMARY 摘要字典 
	public String addAccTellSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量添加ACC_TELL_SUMMARY 摘要字典 
	 * @param  AccTell entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccTellSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量删除ACC_TELL_SUMMARY
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccTellSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 更新ACC_TELL_SUMMARY
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccTellSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR>  导入摘要字典
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/ 
	public String importAccTellSummary(Map<String, Object> mapVo);
	
	public List<Map<String,Object>> queryCashAccountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> collectAccCheckPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 模板打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryAccBankByAccountForTemplatePrint(Map<String, Object> map) throws DataAccessException;
	//总账对账表查询
	public String queryAllCheckQuery(Map<String,Object> entityMap) throws DataAccessException;
	//总账对账打印
	public List<Map<String,Object>> queryAllCheckQueryPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
