/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.books.subjaccount;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;



public interface GroupAccSubjLedgerService {
	
	/**
	 * 科目总账 按月
	 * */ 
	//查询
	public String collectGroupAccSubjLedger(Map<String,Object> entityMap)throws DataAccessException;
	//普通打印
	public List<Map<String, Object>> collectGroupAccSubjLedgerPrintDate(Map<String, Object> entityMap) throws DataAccessException;
	//模板打印
	public Map<String,Object> collectGroupAccSubjLedgerPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科目总账  按天
	 * */
	//查询
	public String collectGroupAccSubjLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;
	//普通打印
	public List<Map<String, Object>> collectGroupAccSubjLedgerDetailPrintDate(Map<String, Object> entityMap) throws DataAccessException;
	//模板打印
	public Map<String,Object> collectGroupAccSubjLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询三栏明细账
	 * */
	public String collectGroupThreeColumnLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;
	//普通打印
	public List<Map<String, Object>> collectGroupThreeColumnLedgerDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;
	//模板打印
	public Map<String, Object> collectGroupThreeColumnLedgerDetailPrint(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询科目余额表
	 * */
	public String collectGroupBalanceLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;



	
	public String collectGroupAccExpendFinancial(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> collectGroupAccExpendFinancialPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryGroupAccSubjLederDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryGroupAccSubjLederCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccSubjLederCheckPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryGroupAccSubjByPlan(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 科目汇总查询统计凭证号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupAccVouchCountSum(Map<String, Object> mapVo) throws DataAccessException;
	/** 
	 * 科目余额表  辅助核算明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目余额表  辅助核算明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryGroupAccSubjLedgerCheckDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总表明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科目  按subj_code查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryGroupAccSubjTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询数据 按筛选条件进行查询
	 * @param mapVo
	 * @return
	 */
	public String queryGroupAccColumnsLedgerDetail(Map<String, Object> mapVo);
	
	//多栏明细账  打印
	public List<Map<String, Object>> queryGroupAccColumnsLedgerDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	public Map<String,Object> collectGroupBalanceLedgerDetailPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryGroupAccSubjLederDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

	

	
	
	public Map<String,Object> collectGroupAccSubjLedgerSumMainPrint(Map<String, Object> entityMap) throws DataAccessException;

	//科目余额表  普通打印
	public List<Map<String, Object>> collectGroupBalanceLedgerDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;

}
