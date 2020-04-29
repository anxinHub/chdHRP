/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.service.books.subjaccount;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccSubjLedgerService {
	/**
	 * 查询科目总账
	 * */ 
	public String collectAccSubjLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目明细账
	 * */
	public String collectAccSubjLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询三栏明细账
	 * */
	public String collectThreeColumnLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 全年账簿打印
	 **/
	public List<Map<String, Object>> collectThreeColumnLedgerDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目余额表
	 * */
	public String collectBalanceLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;



	
	public String collectAccExpendFinancial(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> collectAccExpendFinancialPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccSubjLederDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccSubjLederCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjLederCheckPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccSubjByPlan(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 科目汇总查询统计凭证号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccVouchCountSum(Map<String, Object> mapVo) throws DataAccessException;
	/** 
	 * 科目余额表  辅助核算明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目余额表  辅助核算明细打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubjLedgerCheckDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总表明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科目  按subj_code查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryAccSubjTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询数据 按筛选条件进行查询
	 * @param mapVo
	 * @return
	 */
	public String queryAccColumnsLedgerDetail(Map<String, Object> mapVo);
	
	//多栏明细账  打印
	public List<Map<String, Object>> queryAccColumnsLedgerDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> collectThreeColumnLedgerDetailPrint(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String,Object> collectBalanceLedgerDetailPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryAccSubjLederDetailPrint(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String,Object> collectAccSubjLedgerPrint(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String,Object> collectAccSubjLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> collectAccSubjLedgerSumMainPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//科目余额表  普通打印
	public List<Map<String, Object>> collectBalanceLedgerDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 科目总账按天 查凭证详情
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryKMZZTVouchNo(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccVouchCountSumDetailPrint(Map<String,Object> entityMap)throws DataAccessException;
	

}
