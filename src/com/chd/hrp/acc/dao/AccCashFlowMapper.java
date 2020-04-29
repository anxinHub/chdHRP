/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashFlow;

/**
* @Title. @Description.
* 现金流量标注<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashFlowMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 添加AccCashFlow
	 * @param AccCashFlow entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量添加AccCashFlow
	 * @param  AccCashFlow entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlow分页
	 * @param  entityMap RowBounds
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	*/
	public List<AccCashFlow> queryAccCashFlow(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlow所有数据
	 * @param  entityMap
	 * @return List<AccCashFlow>
	 * @throws DataAccessException
	*/
	public List<AccCashFlow> queryAccCashFlow(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlowByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCashFlow queryAccCashFlowByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 删除AccCashFlow
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量删除AccCashFlow
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 现金流量标注<BR> 更新AccCashFlow
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量更新AccCashFlow
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccCashFlow> queryCashFlowSubj(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//查询凭证状态，判断是否可以标注
	public String[] queryVouchStateByDetailId(Map<String,Object> entityMap)throws DataAccessException;
	
	//根据凭证明细ID查询辅助核算的条数
	public int queryVouchCheckCountByDetailId(Map<String,Object> entityMap)throws DataAccessException;
	
	// 分录金额与标注金额是否相等 
	public int queryCheckByCashFlowExistsMoney(Map<String, Object> map)throws DataAccessException;
	
	public List<AccCashFlow> queryAccCashFlowByVouchId(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccCashFlow> queryAccCashFlowListByVouchId(Map<String,Object> entityMap) throws DataAccessException;
	
	//是否含记账凭证
	public int existsHaveAccount(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//批量删除现金流量
	public int deleteAccCashFlowByVouch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//批量插入现金流量
	public int addAccCashFlowByVouch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
}
