/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.subjaccount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubjLedger;

/**
* @Title. @Description.
* 科目总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupAccSubjLedgerMapper extends SqlMapper{
	

	/**
	 * @Description 
	 * 科目总账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupAccSubjLedger(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目总账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupAccSubjLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目明细账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupAccSubjLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目明细账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupAccSubjLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目三栏明细账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupThreeColumnLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目三栏明细账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupThreeColumnLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 科目余额表<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectGroupBalanceLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目余额表<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> collectGroupBalanceLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出备查簿<BR>
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> collectGroupAccExpendFinancial(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * 
	 * --根据页面传递条件 查询科目
	*/
	public List<Map<String,Object>> queryGroupSubjByCody(Map<String,Object> entityMap) throws DataAccessException;
	
	//----------------------------------------查询科目余额表使用 -------------------------------------------------
	/**
	 * 
	 * --查询账表期初数据
	*/
	public List<Map<String,Object>> queryGroupLdegerBALOS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表本期发生
	*/
	public List<Map<String,Object>> queryGroupLdegerTHIS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表累计发生
	*/
	public List<Map<String,Object>> queryGroupLdegerSUM(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表期末数据
	*/
	public List<Map<String,Object>> queryGroupLdegerENDOS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表期末数据
	*/
	public List<Map<String,Object>> queryGroupVouchData(Map<String,Object> entityMap) throws DataAccessException;

	//----------------------------------------查询科目余额表使用 -------------------------------------------------

	public List<Map<String,Object>> queryGroupAccSubjLederDetail(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryGroupAccSubjLederCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryGroupAccSubjByPlan(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总查询统计凭证号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryGroupAccVouchCountSum(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目余额表  辅助核算项明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryGroupAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总表查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryGroupAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryGroupAccSubjTree(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryGroupAccColumnsLedgerDetail(Map<String, Object> mapVo);
	
	public Map<String, Object> queryGroupAccColumnsLedgerBegin(Map<String, Object> mapVo);

}
