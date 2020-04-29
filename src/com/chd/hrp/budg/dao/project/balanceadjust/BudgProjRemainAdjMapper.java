/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.balanceadjust;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

 

public interface BudgProjRemainAdjMapper extends SqlMapper{
	
	/**
	 * 查询可用余额 作为调整资金回显
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryUsableAmount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核  更改数据
	 * @param listVo
	 * @return
	 */
	public int updateAdjSate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据项目 查询资金来源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBudgSourceByProj(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * @Description 
	 * 根据项目ID 资金来源ID  查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String,Object> queryProjMessage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据条件组合   查询数据明细  不分页
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryProjDetailByCondition(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据条件组合   查询数据明细  分页
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryProjDetailByCondition(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 修改查询明细数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> querBudgProjBalanceDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 修改查询明细数据
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> querBudgProjBalanceDetail(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 根据单号查询数据状态
	 * @param map
	 * @return
	 */
	public String queryState(Map<String, Object> map) throws DataAccessException;
	/**
	 * 根据明细表数据  更新年度账表数据
	 * @param reDetList
	 */
	public void updateProjYear(List<Map<String, Object>> reDetList) throws DataAccessException;
	
	/**
	 * 销审  更改数据
	 * @param listVo
	 * @return
	 */
	public void updateCancelProjYear(List<Map<String, Object>> reDetList) throws DataAccessException;
	
	
}
