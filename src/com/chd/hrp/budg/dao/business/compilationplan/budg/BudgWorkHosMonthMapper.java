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
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosMonthMapper extends SqlMapper{
	/**
	 * 查询 所传 指标的 上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *根据主键 查询数据是否已存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 导入时  查询预算指标是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int qureyBudgIndexExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 汇总科室月份数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 当前年月医院月份业务预算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkHosMonthByYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchBudgWorkHosMonth(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 批量修改 预算值
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询 分解计算的 医院月份数据 不分页
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryResolveData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 分解计算的 医院月份数据 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryResolveData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 月份、自定义分解系数 计算分解比例(医院月) 不分页
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> item) throws DataAccessException;
	
	
	
}
