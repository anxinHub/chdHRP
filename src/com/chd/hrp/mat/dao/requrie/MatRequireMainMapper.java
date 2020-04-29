/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.requrie;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRequireMain;
/**
 * 
 * @Description:
 * MAT_REQUIRE_MAIN
 * @Table:
 * MAT_REQUIRE_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatRequireMainMapper extends SqlMapper{
	
	/**
	 * 中止计划
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAbortMatDeptPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 提交单据
	 * @param listVo
	 * @return
	 */
	public int updateSubmitMatDeptPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 取消提交
	 * @param listVo
	 * @return
	 */
	public int updateUnSubmitMatDeptPlan(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 审核页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryConfirm(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核页面查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryConfirm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 取消审核
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAuditCancle(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 退回科室
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateReturn(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 汇总页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatRequireMain> queryCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总页面查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatRequireMain> queryCollect(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查看计划单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatRequireMain> queryCollectDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<MatRequireMain> queryCollectDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 保存&提交插入mat_require_main表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addCollect(Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 保存&提交插入对应关系表 MAT_REQUIRE_RELA 
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int addBatchReal(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 更新 保存&提交的单据 的状态  state = 3
	 * @param listVo1
	 * @throws DataAccessException
	 */
	public void updateBatchCollect(List<Map<String, Object>> listVo1) throws DataAccessException;
	
	/**
	 * 汇总计划查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryCollectQ(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryCollectQ(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 汇总查询--明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryCollectDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总查询   汇总单号查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryCollectStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获得自增序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int getNextReqId(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMatRequireMainByReqCode(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> queryMatRequireMainDetailByReqCode(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> queryMatApplyRelaReq(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 删除对应关系表
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteMatApplyRelaReq(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<Map<String, Object>> queryMatApplyRelaReqById(Map<String, Object> entityMap) throws DataAccessException;

	
	
	
}
