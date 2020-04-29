/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.adjust;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgProjAdj;
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
 

public interface BudgProjAdjService extends SqlService {
	/**
	 * @Description 
	 * 查询调整添加页面信息
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String queryAdjAdd(Map<String, Object> entityMap)
			throws DataAccessException;
	/**
	 * @Description 
	 * 生成 预算调整单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String setBudgApplyCode(Map<String, Object> mapVo)
			throws DataAccessException;
	
	/**
	 * 提交    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSubmitState(List<Map<String, Object>> entityList)
			throws DataAccessException;
	/**
	 * 撤回提交    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateRecallState(List<Map<String, Object>> listVo)
			throws DataAccessException;
	/**
	 * 审核     
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateReviewState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 销审     
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 修改查询  查询明细表    
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjAdjDetail(Map<String, Object> entityMap) throws DataAccessException;
	

}
