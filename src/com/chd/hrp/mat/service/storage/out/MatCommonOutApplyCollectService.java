/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.service.storage.out;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description: 04312 科室申请单主表
 * @Table: MAT_APPLY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface MatCommonOutApplyCollectService extends SqlService {
	
	/**
	 * @Description 处理请购单主查询--按材料汇总查询<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryInvCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 处理请购单明细查询--以材料为主显示每个科室的申请情况<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryInvCollectDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量退回<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addByBack(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--主表数据组装<BR>
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryOutMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryOutDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--代销明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询管理部门对应的仓库信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryStoreByDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--主表数据组装<BR>
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryTranMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryTranDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--代销明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryAffiTranDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成科室需求计划--主表数据组装<BR>
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryReqMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成科室需求计划--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryReqDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 汇总生成科室需求计划<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addMatReqByApplyCollect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 汇总生成采购计划<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addMatPurByApplyCollect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 按材料汇总页面--汇总生成采购计划<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addMatPurByApplyInvCollect(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查看关闭的材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatApplyCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 取消关闭材料
	 * @param listVo
	 * @return
	 */
	public String updateMatApplyCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;

}
