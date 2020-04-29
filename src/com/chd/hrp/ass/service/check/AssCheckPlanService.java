package com.chd.hrp.ass.service.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssCheckPlanService extends SqlService{
	/**
	 * @Description 
	 * 审核盘点任务单数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String audit(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审盘点任务单数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAudit(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 审核盘点任务单数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String finish(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审盘点任务单数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unFinish(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String generate(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String generateDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点单（仓库）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addGenerateStore(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点单（科室）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addGenerateDept(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点明细（仓库）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addGenerateStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成盘点明细（科室）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addGenerateDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 盘点单树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 审核盘点单数据（仓库、科室）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditCheck(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审盘点单数据（仓库、科室）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatch(List<Map<String,Object>> entityMap,List<Map<String,Object>> entityMapS,List<Map<String,Object>> entityMapG,List<Map<String,Object>> entityMapO)throws DataAccessException;

	
	
	public String queryAssCheckS(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAssCheckD(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAssCheckSdetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAssCheckDdetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCheckPlanStore(Map<String, Object> map)throws DataAccessException;

	public String queryCheckPlanDept(Map<String, Object> map)throws DataAccessException;
}
