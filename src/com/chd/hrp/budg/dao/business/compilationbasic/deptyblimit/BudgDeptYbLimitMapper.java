/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.deptyblimit;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室医保额度控制
 * @Table:
 * BUDG_DEPT_YB_LIMIT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptYbLimitMapper extends SqlMapper{
	
	/**
	 * 医保类型下拉框 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgYBTY(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室 下拉框 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 医保类型是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询科室医保额度控制数据 是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 科室编码 查询 科室ID
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询 预算科室中DEPT_TYPE=02的直接医疗科室
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addGenerate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据年度、科室、医保类型信息 查询 上年医保收入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyLastYbIncome(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据年度、科室、医保类型信息 查询 全院医保额度
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyControlLimit(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 分结数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> qureyResolveData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 历史数据比例分解
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateResolveRate(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;

	
	
}
