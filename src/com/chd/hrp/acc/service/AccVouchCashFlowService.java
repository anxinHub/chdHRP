/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.entity.AccVouch;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchCashFlowService {

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch现金流量标注 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public String queryAccVouchCashFlow(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryAccVouchCashFlowPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String saveAccVouchCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String deleteAccVouchCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryCashFlowSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	public String updateBatchAccVouchCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccCashFlow> queryAccCashFlowListByVouchId(Map<String, Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> saveAccVouchCashFlowBatch(Map<String, Object> entityMap)throws DataAccessException;
}
