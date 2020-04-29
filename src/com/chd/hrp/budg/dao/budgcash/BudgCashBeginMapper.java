/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcash;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
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
 

public interface BudgCashBeginMapper extends SqlMapper{
	/**
	 * 添加期初货币资金
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addBudgCashBegin(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据主键查询 单条期初货币资金
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public BudgCashBegin queryBudgCashBeginByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据主键查询 期初货币资金是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgCashBeginDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 期初货币资金
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgCashBegin(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改 期初货币资金
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgCashBegin(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 *主页面跳转 根据账套  查询期初现金流量累计结果集
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryBudgCashFlowBegin(Map<String, Object> mapVo) throws DataAccessException;
	
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
	public List<Map<String, Object>> queryCashItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据是否已存在
	 * @param entityMap
	 * @return
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 换行添加 期初现金流量累计
	 * 
	 */
	public int addBudgCashFlowBegin(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量 更新 期初现金流量累计
	 * 
	 */
	public void updateBudgCashFlowBegin(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 批量 删除 期初现金流量累计
	 * 
	 */
	public void deleteBudgCashFlowBegin(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 *添加成功后  根据现金流量项目ID查询流向
	 * 
	 */
	public String queryCashDire(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 *
	 */
	public String queryBudgCashBeginState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 记账  更改数据状态
	 *
	 */
	public void updateBudgCashBeginState(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgCashFlowBeginDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询现金存量预算表 和现金存量执行数据表中是否存在数据
	 * @param mapVo
	 * @return
	 */
	public int queryDataExistFromBudgCash(Map<String, Object> mapVo)  throws DataAccessException;
	public int queryDataExistFromBudgCashExe(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
	 * @param entityMap
	 * @return
	 */
	public Double queryCashBeginExist(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
