/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.allyear;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubjLedger;

/**
* @Title. @Description.
* 全年账簿
* @Author: gaopei 
*/


public interface AccAllYearMapper extends SqlMapper{

	/**
	 * @Description 
	 * 科目明细账  
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectAllYearBySubjDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目辅助明细账  （例如：项目明细账）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> collectAccZcheckDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 全年账簿最后查询   科目余额表
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	//public List<Map<String, Object>> collectBalanceLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	 

}
