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
public interface AccSubjLedgerMapper extends SqlMapper{

	/**
	 * 根据所给会计科目获取对应末级最大科目编码
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String getSubjLastMaxCodeBySubjCode(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目总账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<AccSubjLedger> collectAccSubjLedger(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目总账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectAccSubjLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目明细账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectAccSubjLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目明细账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<AccSubjLedger> collectAccSubjLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目三栏明细账<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<AccSubjLedger> collectThreeColumnLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目三栏明细账<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> collectThreeColumnLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 科目余额表<BR> 查询AccSubjLedger分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<AccSubjLedger> collectBalanceLedgerDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目余额表<BR> 查询AccSubjLedger所有数据
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> collectBalanceLedgerDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出备查簿<BR>
	 * @param  entityMap
	 * @return List<AccSubjLedger>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> collectAccExpendFinancial(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * 
	 * --根据页面传递条件 查询科目
	*/
	public List<Map<String,Object>> querySubjByCody(Map<String,Object> entityMap) throws DataAccessException;
	
	//----------------------------------------查询科目余额表使用 -------------------------------------------------
	/**
	 * 
	 * --查询账表期初数据
	*/
	public List<Map<String,Object>> queryLdegerBALOS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表本期发生
	*/
	public List<Map<String,Object>> queryLdegerTHIS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表累计发生
	*/
	public List<Map<String,Object>> queryLdegerSUM(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表期末数据
	*/
	public List<Map<String,Object>> queryLdegerENDOS(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * --查询账表期末数据
	*/
	public List<Map<String,Object>> queryVouchData(Map<String,Object> entityMap) throws DataAccessException;

	//----------------------------------------查询科目余额表使用 -------------------------------------------------

	public List<Map<String,Object>> queryAccSubjLederDetail(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryAccSubjLederCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccSubjByPlan(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总查询统计凭证号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchCountSum(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目余额表  辅助核算项明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科目汇总表查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchCountSumDetail(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjTree(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccColumnsLedgerDetail(Map<String, Object> mapVo);
	public List<Map<String, Object>> queryAccColumnsLedgerDetail(Map<String, Object> mapVo, RowBounds rowbounds);
	
	public Map<String, Object> queryAccColumnsLedgerBegin(Map<String, Object> mapVo);
	/**
	 * 查凭证详情
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryKMZZTVouchNo(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAccBookSch(Map<String, Object> entityMap);

}

