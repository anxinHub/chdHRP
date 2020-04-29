/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.adjust;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgProjAdj;
import com.chd.hrp.budg.entity.BudgProjDetailYear;
/**
 * 
 * @Description:
 * 状态（STATE），取自系统字典表
“01新建、02已提交、03已审核”
 * @Table:
 * BUDG_PROJ_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjAdjMapper extends SqlMapper{
	//查询调整添加页面信息
	public Map<String, Object> queryAdjAdd(Map<String, Object> entityMap)throws DataAccessException;
	
	//查询数据状态
	public String queryState(Map<String, Object> mapVo) throws DataAccessException;
	
	//提交  撤回 更新数据状态
	public void updateSubmitState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	//审核销审更新数据状态
	public void updateReviewState(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 修改查询  查询明细表    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjAdjDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryBudgProjAdjDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 查询  查询年度明细表 返回年度明细   
	 * @param entityMap 
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryYearDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改 年度明细表 
	 * @param entityMap 
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public void updateYearDetailBatch(List<Map<String, Object>> upadateYearDetailList) throws DataAccessException;
	
	/**
	 * 添加数据到年度明细表中
	 * @param addYearDetailList
	 */
	public void addYearDetailBatch(List<Map<String, Object>> addYearDetailList) throws DataAccessException;
	
}
