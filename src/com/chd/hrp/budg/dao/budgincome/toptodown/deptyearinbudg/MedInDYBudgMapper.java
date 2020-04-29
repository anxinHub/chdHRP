/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.toptodown.deptyearinbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface MedInDYBudgMapper extends SqlMapper{
	
	/**
	 * 添加页面 科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询预算科室是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算方法下:
	 * 查询科室年度医疗预算中同级科室信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCountData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算方法下:
	 *	查询 所选年度、科目 的 所有科室上年收入 总和 
	 * 
	 */
	public Map<String, Object> querySumLastYearIncome(Map<String, Object> lastMap) throws DataAccessException;
	/**
	 * 计算方法下:
	 *	查询 所选年度、科目 的 所有科室 总数 
	 * 
	 */
	public Integer querySumDept(Map<String, Object> mapVo) throws DataAccessException;

	public Map<String, Object> queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException;

	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;

	public Map<String, Object> queryGetWay(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询数据状态
	 * @param item
	 * @return
	 */
	public String queryState(Map<String, Object> item) throws DataAccessException;
	/**
	 * 下发 撤回  取消确认(自上而下) 状态更新
	 * @param entityList
	 */
	public void issuedOrRetractUpdate(List<Map<String, Object>> entityList);
	/**
	 * 确认(通过,不通过)(自上而下) 状态更新
	 * @param entityList
	 */
	public void passOrDisPassUpdate(List<Map<String, Object>> entityList);
	
	/**
	 * 根据 科室、自定义分解系数 计算分解比例(科室年)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;

	public int querySumDeptAll(Map<String, Object> mapVo) throws DataAccessException;
	
}
