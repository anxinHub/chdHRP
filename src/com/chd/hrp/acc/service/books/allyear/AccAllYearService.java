/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.books.allyear;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * 全年账簿
 * @author gaopei
 *
 */ 
public interface AccAllYearService {
	 
	/**
	 * 查询科目明细账  
	 * */
	//科目明细  查询
	public String collectAllYearBySubjDetail(Map<String,Object> entityMap)throws DataAccessException;
	//科目明细账 普通打印
	public List<Map<String, Object>> collectAllYearBySubjDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;
	//科目明细账   模板打印
	public Map<String, Object> collectAllYearBySubjDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 自定义辅助明细账 （例如 项目明细账）
	 */
	//自定义辅助明细账   查询
	public String collectAccZcheckDetail(Map<String,Object> entityMap)throws DataAccessException;
	//自定义辅助明细账 普通打印
	public List<Map<String, Object>> collectAccZcheckDetailPrintDate(Map<String,Object> entityMap)throws DataAccessException;
	//自定义辅助明细账   模板打印
	public Map<String, Object> collectAccZcheckDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科目余额表
	 * */
	public String collectBalanceLedgerDetail(Map<String,Object> entityMap)throws DataAccessException;
	

}
