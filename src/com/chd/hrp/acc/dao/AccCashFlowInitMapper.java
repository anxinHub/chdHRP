/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.entity.AccCashFlowInit;
import com.chd.hrp.acc.entity.HrpAccSelect;

/**
* @Title. @Description.
* 现金流量初始帐<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashFlowInitMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 添加AccCashFlowInit
	 * @param AccCashFlowInit entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量添加AccCashFlowInit
	 * @param  AccCashFlowInit entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlowInit分页
	 * @param  entityMap RowBounds
	 * @return List<AccCashFlowInit>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccCashFlowInit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlowInit所有数据
	 * @param  entityMap
	 * @return List<AccCashFlowInit>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryAccCashFlowInit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlowInitByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCashFlowInit queryAccCashFlowInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 删除AccCashFlowInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量删除AccCashFlowInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 更新AccCashFlowInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量更新AccCashFlowInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccCashFlowInit> queryCashFlowSubj(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//查询凭证状态，判断是否可以标注
	public String[] queryVouchStateByDetailId(Map<String,Object> entityMap)throws DataAccessException;
	
	//根据凭证明细ID查询辅助核算的条数
	public int queryVouchCheckCountByDetailId(Map<String,Object> entityMap)throws DataAccessException;

	public List<HrpAccSelect> queryAccSubj(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询 账表中 科目的期初帐是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryLederExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询  会计科目  是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询  现金流量项目  是否存在 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccCashFlowExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改时    批量删除修改数据（专用）
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteUpdateDate(List<Map<String, Object>> entityList) throws DataAccessException;
	
}
