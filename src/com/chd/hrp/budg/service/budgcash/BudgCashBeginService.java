/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcash;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgCashBegin;
/**
 * 
 * @Description:
 * 期初货币资金
 * @Table:
 * BUDG_CASH_BEGIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashBeginService {
	
	/**
	 * 期初货币资金
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addBudgCashBegin(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 *  查询   期初货币资金
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public String queryBudgCashBegin(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 *主页面跳转 根据主键  查询对象
	 * @param map
	 * @return
	 */
	public BudgCashBegin queryBudgCashBeginByCode(Map<String, Object> map) throws DataAccessException;
	
	/**
	 *主页面跳转 根据账套  查询对象
	 * @param map
	 * @return
	 */
	public String queryBudgCashFlowBegin(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *主页面跳转 根据主键  查询系统启用时间
	 * @param map
	 * @return
	 */
	public String queryStartDate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 */
	public String queryCashItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 换行添加数据 期初现金流量累计
	 */
	public String addBudgCashFlowBegin(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 修改保存 期初现金流量累计
	 * @param mapVo
	 * @return
	 */
	public String updateBudgCashFlowBegin(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 批量删除 期初现金流量累计
	 * @param mapVo
	 * @return
	 */
	public String deleteBudgCashFlowBegin(List<Map<String, Object>> listVo,Map<String, Object> initMap) throws DataAccessException;
	
	/**
	 *  记账  更改数据状态
	 * @param mapVo
	 * @return
	 */
	public String chargeBudgCashBeginState(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 *  反记账  更改数据状态
	 * @param mapVo
	 * @return
	 */
	public String unChargeBudgCashBeginState(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 最新导入
	 */
	public String importBudgCashFlowBegin(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *查询数据状态
	 */
	public String queryBudgCashBeginState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或修改数据 期初现金流量累计
	 */
	public String addOrUpdateBudgCashFlowBegin(List<Map<String, Object>> listVo,Map<String, Object> initMap);
}
