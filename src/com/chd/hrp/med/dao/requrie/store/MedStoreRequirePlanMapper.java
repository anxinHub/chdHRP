/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.requrie.store;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRequireDetail;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * 仓库需求计划编制页面
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreRequirePlanMapper extends SqlMapper{
	/**
	 * 仓库需求计划编制--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStorePlan(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryStorePlan(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 科室需求计划生成--科室需求计划中仓库列表
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStoreByDept(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryStoreByDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室需求计划生成--仓库与材料对应关系中申领仓库
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStoreByInv(Map<String, Object> entityMap)  throws DataAccessException;
	public List<Map<String,Object>> queryStoreByInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室需求计划生成 -- 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptGDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室需求计划生成  重新明细组装数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptNewDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	
	/**
	 * 提交单据
	 * @param detailList
	 * @throws DataAccessException
	 */
	public void updateSubmitMedStorePlan(List<Map<String, Object>> detailList) throws DataAccessException;
	
	/**
	 * 取消提交单据
	 * @param detailList
	 * @throws DataAccessException
	 */
	public void updateUnSubmitMedStorePlan(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 中止单据
	 * @param detailList
	 * @throws DataAccessException
	 */
	public void abortMedStoreRequriedPlan(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 审核
	 * @param detailList
	 * @throws DataAccessException
	 */
	public void submitMedStoreRequriedConfirm(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 取消审核
	 * @param detailList
	 * @throws DataAccessException
	 */
	public void unSubmitMedStoreRequriedConfirm(List<Map<String, Object>> detailList) throws DataAccessException;
	
	/**
	 * 更新明细状态
	 * @param relaList
	 * @throws DataAccessException
	 */
	public void updateColsed(List<Map<String, Object>> relaList) throws DataAccessException;
	/**
	 * 删除时更细明细状态  is_closed = 0
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateDeptDetailClosed(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
	
	/**
	 * 仓库需求计划编制--仓库消耗导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public List<?> queryDeptExpend(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * 仓库需求计划编制--库房导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public List<?> queryDeptSupport(Map<String, Object> entityMap) throws DataAccessException;
	
	*//**
	 * 库存安全导入--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 *//*
	public List<?> queryStoreSafe(Map<String, Object> entityMap) throws DataAccessException;

	
	*/
	
}
