/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.requrie.store;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * 仓库需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedStoreRequirePlanService extends SqlService{
	/**
	 * 仓库需求计划编制--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStorePlan(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 仓库需求计划编制--明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室需求计划生成--科室需求计划中仓库列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreByDept(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室需求计划生成--仓库与材料对应关系中申领仓库
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreByInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室需求计划生成 -- 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptGDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 提交
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSubmitMedStorePlan(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 取消提交
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateUnSubmitMedStorePlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 中止单据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String abortMedStoreRequriedPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String submitMedStoreRequriedConfirm(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 取消审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String unSubmitMedStoreRequriedConfirm(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 组装汇总数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectDeptData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 复制购置计划
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMedStorePlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	Map<String, Object> printMedStoreRequireMainData(Map<String, Object> map) throws DataAccessException;
	
	
	/**
	 * 仓库消耗导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public String queryDeptExpend(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * 仓库配套导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public String queryDeptSupport(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * 安全库存导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public String queryStoreSafe(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * 复制购置计划单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 *//*
	public String copyMedDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;

	*/

	
}
