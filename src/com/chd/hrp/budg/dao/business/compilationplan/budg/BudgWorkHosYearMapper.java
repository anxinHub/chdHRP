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
 * 医院年度业务预算
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosYearMapper extends SqlMapper{
	
	/**
	 * 从 医院业务执行数据(年度) BUDG_WORK_HOS_EXECUTE_YEAR 表中查询 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询指标编码的  取值方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryGetWay(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 生成运营尺度数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryProbBudgRate(	Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 医院年度业务预算查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkHosYear(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 医院年度业务预算查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkHosYear(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据主键查询 数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 查询 汇总科室年度数据（自下而上  汇总用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室意见汇总
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public void sumDeptSuggest(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算指标下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndex(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 所传 收入科目编码 查询其取值方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexGetWay(Map<String, Object> entityMap) throws DataAccessException;
	
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
	public List<Map<String, Object>> getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度业务预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;

}
