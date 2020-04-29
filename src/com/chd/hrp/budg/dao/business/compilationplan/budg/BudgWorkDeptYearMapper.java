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
 * 科室年度业务预算
 * @Table:
 * BUDG_WORK_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptYearMapper extends SqlMapper{
	
	/**
	 * 查询 所传 科室的 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 查询 所选年度、指标 的 所有科室上年业务量 总和
	 * @param lastMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySumLastYearWork(Map<String, Object> lastMap) throws DataAccessException;
	
	/**
	 * 根据 指标  查询指标对应的 科室数量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIndexDeptCount(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算时 查询计算的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexDeptSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 指标 查询 其取值方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成 科室年度业务概率预算 运营尺度数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryProbBudgRate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自下而上 科室年度业务预算查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkDeptYearDown(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 自下而上 科室年度业务预算查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkDeptYearDown(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 预算指标下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndex(Map<String, Object> entityMap, RowBounds rowBounds)  throws DataAccessException;
	
	/**
	 * 根据指标 关联查询科室下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndexDeptSet(Map<String, Object> entityMap,RowBounds rowBounds)  throws DataAccessException;
	
	/**
	 * 查询数据  预算分解页面 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkDeptYearResolve(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询数据  预算分解页面 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkDeptYearResolve(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getGrowRate(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 根据 科室、自定义分解系数 计算分解比例(科室年)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 科室年度业务预算删除前判断状态
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsExistNoNewBuilt(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public String queryState(Map<String, Object> item) throws DataAccessException;
	/**
	 * 下发 撤回 更新数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void issuedOrRetractUpdate(List<Map<String, Object>> entityList) throws DataAccessException; 
	
	/**
	 * 确认  更新数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void passOrDisPassUpdate(List<Map<String, Object>> entityList) throws DataAccessException; 
	
	/**
	 * 获取费用项目对应科目存在于科室支出预算中数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryYearDeptSubjDataExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 科室年度业务预算删除前判断状态
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryYearDeptExpensesDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
