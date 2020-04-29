/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.budgcontrol;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * @Table:
 * BUDG_PROJ_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjExeMapper extends SqlMapper{
	
	/**
	 * 项目下拉框  添加使用
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgProj(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资金来源下拉框  添加使用
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgSource(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出项目下拉框  添加使用
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgPaymentItem(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据主键  判断数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询  年度项目预算  是否存在 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgDataExist(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 根据 所填数据 年度 查询该预算年度是否 已结转 （已结转 则不允许操作）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIsCarried(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 *  查询 年度项目预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjYearData(	Map<String, Object> mapVo)  throws DataAccessException;;
	
	/**
	 * 汇总 存在对应年度项目预算明细数据 的 执行数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjDetailYearExistData(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 汇总 不存在对应年度项目预算明细数据 的 执行数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjDetailYearNotExistData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 确认 修改账表  年度项目预算
	 * @param list
	 * @throws DataAccessException
	 */
	public int updateBudgProjYear(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 确认 修改账表  年度项目预算明细 
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int updateBudgProjYearDetail(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 确认 添加账表  年度项目预算明细 
	 * @param detailListNE
	 * @return
	 * @throws DataAccessException
	 */
	public int addBudgProjYearDetail(List<Map<String, Object>> detailListNE) throws DataAccessException;
	
	/**
	 * 查询 预算系统启用年月
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgModStartYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	

	
	
	
}
