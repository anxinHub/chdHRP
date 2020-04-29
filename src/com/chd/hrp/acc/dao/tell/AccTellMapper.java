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
import com.chd.hrp.acc.entity.AccTell;



/**
* @Title. @Description.
* 出纳账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTellMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 出纳账<BR> 添加AccTell
	 * @param AccTell entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addAccTellByBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量添加AccTell
	 * @param  AccTell entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTell分页
	 * @param  entityMap RowBounds
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public List<AccTell> queryAccTell(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTell所有数据
	 * @param  entityMap
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public List<AccTell> queryAccTell(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询AccTellByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccTell queryAccTellByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 删除AccTell
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量删除AccTell
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 出纳账<BR> 更新AccTell
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 批量更新AccTell
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccTell(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccTell> queryCashAccount(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public void queryAccCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public  List<AccTell>  queryAccCheckList() throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 现金出纳账-数据分页
	 * @param  entityMap 
	 * @return Map
	 * @throws DataAccessException
	*/
	public List<AccTell> queryCashAccountByCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryCashAccountPrint(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 出纳账<BR> 现金出纳账
	 * @param  entityMap 
	 * @return Map
	 * @throws DataAccessException
	*/
	public List<AccTell> queryCashAccountByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 修改
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int update(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询银行未达账(结果集带合计借贷方) AccTell分页
	 * @param  entityMap RowBounds
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public List<AccTell> queryAccTellAndSum(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 出纳账<BR> 查询银行未达账(结果集带合计借贷方) AccTell所有数据
	 * @param  entityMap
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public List<AccTell> queryAccTellAndSum(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR> 查询流水号
	 * @param  entityMap
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public long queryAccTellMaxTellNumber(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 出纳账<BR> 更新流水号
	 * @param  entityMap
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public int updateAccTellMaxTellNumber(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳账<BR>查询重置后流水号
	 * @param  entityMap
	 * @return List<AccTell>
	 * @throws DataAccessException
	*/
	public List<AccTell> queryAccTellMaxTellResettingNumber(Map<String,Object> entityMap) throws DataAccessException;

	public int queryAccTellByInit(Map<String, Object> entityMap)throws DataAccessException;
	
	public AccTell queryAccTellDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccTell> queryAccBrokenTell(Map<String,Object> entityMap) throws DataAccessException;

	public List<AccTell> queryAccBrokenTellList(Map<String,Object> entityMap) throws DataAccessException;

	//出纳账管理  摘要字典查询  (不分页)
	public List<AccTell> queryAccTellSummary(Map<String,Object> entityMap) throws DataAccessException;
	//出纳账管理  摘要字典查询 (分页)
	public List<AccTell> queryAccTellSummary(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 //批量添加  摘要字典
	public int addBatchAccTellSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	//添加  摘要字典
	public int addAccTellSummary(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 出纳账<BR> 批量删除ACC_TELL_SUMMARY
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTellSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 出纳账<BR> 更新ACC_TELL_SUMMARY
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccTellSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	//判断摘要是否存在
	public int SearchAccTellSummaryByExists(Map<String,Object> entityMap)throws DataAccessException;
	
	//判断摘要是否存在   用于导入判断  
    public AccTell SearchAccTellSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
		
    public List<Map<String,Object>> queryAccTellAndSumPrint(Map<String,Object> entityMap) throws DataAccessException;
    //模板打印
	public List<Map<String, Object>> queryCashAccountByCodeForTemplatePrint(Map<String, Object> map);
	
	
	//对账查询打印
	public List<Map<String,Object>> queryAccCheckPrint(Map<String,Object> entityMap) throws DataAccessException;

	public Long queryAccTellNextSeq() throws DataAccessException;
	
	
	
	//添加自动凭证日志表记录
	public int saveAutoVouchLog(List<Map<String, Object>> entityMap)throws DataAccessException;
    //总账查询
	public List<Map<String,Object>> queryAllCheckQuery(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> querSubjCode(Map<String, Object> entityMap) throws DataAccessException;
}
