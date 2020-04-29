/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccLeder;

/**
* @Title. @Description.
* 财务账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccLederService {
	
	
	/**
	 * @Description 
	 * 系账表<BR> 添加AccLeder
	 * @param AccPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccLeder(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 账表<BR> 批量添加AccLeder
	 * @param AccPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLeder分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccLeder(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccLederPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLederByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccLeder queryAccLederByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财务账表<BR> 查询AccLederByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public boolean queryAccLederBySubjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 财务账表<BR> 导入AccLeder
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccLeder(Map<String,Object> entityMap)throws DataAccessException;
	
	public String getGridTitle(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryProjDictDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryStoreDictDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String querySupDictDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String querySourceDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHosInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCheckItem(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryModByModCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryGridTitle(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> queryTitle(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccLederCheckList(Map<String,Object> entityMap)throws DataAccessException;
	//查询试算平衡
	public List<AccLeder> queryAccLederIndex(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryAccLederIndexPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> queryAccCheckItem(Map<String,Object> entityMap)throws DataAccessException;

	public String addBatchAccLederImport(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 财务账表<BR> 查询queryAccLederBySubjId
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccLederBySubjId(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteAccLederBySubjId(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String verifyAccLederIndex(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 辅助核算初始账打印
	 */
	public List<Map<String,Object>> queryAccLederCheckListPrint(Map<String,Object> paraMap) throws DataAccessException;
}
