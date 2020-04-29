/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationplan.budg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptMonthMapper extends SqlMapper{
	
	/**
	 * 根据主键 查询数据是否已存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 *  预算分解 页面 查询数据 不分页  / 查询 计算数据  (计算是调用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算分解 页面 查询数据 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 所选年度、指标 的 所有科室上年业务量 总和
	 * @param lastMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySumLastYearWork(Map<String, Object> lastMap) throws DataAccessException;
	
	/**
	 * 查询 所传年度、指标 、科室的上年业务量
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearWorkLoad(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 指标、科室编码 查询 指标科室对应关系 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 查询 当前年月科室业务预算
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryBudgWorkDeptMonthByYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量修改
	 * @param mapVo
	 * @return
	 */
	public int updateBatchBudgWorkDeptMonth(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * 批量修改 预算值
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据 科室、月份、自定义分解系数 计算分解比例(科室年)
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> item)throws DataAccessException;

}
