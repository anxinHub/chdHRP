/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.inassets;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 051101 盘点任务单(无形资产)
 * @Table:
 * ASS_CHECK_PLAN_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckPlanInassetsService extends SqlService {
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
	public String auditCheckInassets(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审盘点单数据（仓库、科室）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditCheckInassets(Map<String,Object> entityMap)throws DataAccessException;
}
