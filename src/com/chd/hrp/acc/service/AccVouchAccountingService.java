/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchAccountingService {

	/**
	 * @Description 
	 * 凭证主表<BR> 查询AccVouch记账相关数据分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccVouchAccounting(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 未记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public String queryAccVouchUnAccounting(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public String queryAccVouchAccountingCount(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String updateBatchAccountingAccVouch(Map<String, Object> entityMap)throws DataAccessException;

	public String updateBatchUnAccountingAccVouch(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 *  查询记账功能主页面
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchAccount(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchAccountPrint(Map<String,Object> entityMap) throws DataAccessException;

	//查询记账报告
	public String queryAccVouchAccountingReport(Map<String, Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchAccountingReportPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询记账报告
	public Integer queryAccVouchFlow(Map<String, Object> entityMap)throws DataAccessException;
		
}
