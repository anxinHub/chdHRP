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

public interface MatCommonOutApplyCheckService extends SqlService {

	/**
	 * @Description 根据主表ID查询明细数据<BR>
	 * @param entityMap
	 * <BR> 参数为主键
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量退回<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String updateBackMatCommonOutApplyCheckBatch(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--主表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryOutMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryOutDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--保存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addOut(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成出库单--代销明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成代销出库单--保存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addAffiOut(Map<String, Object> entityMap) throws DataAccessException;

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
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryTranMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryTranDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--保存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addTran(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成调拨单--代销明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryAffiTranDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成代销调拨单--保存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addAffiTran(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成科室需求计划--主表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryReqMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成科室需求计划--明细表数据组装<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryReqDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成科室需求计划--保存<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addReq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量生成出库单<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String addOutBatch(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 汇总生成出库单--查询主表<BR>
	 * @param entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryOutMainByCollect(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 汇总生成出库单--查询明细<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryOutDetailByCollect(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询对应的单据列表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatApplyRela(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 删除对应的单据<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteMatApplyRela(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * @Description 查询对应的出库单主表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryRelaOutMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的出库单明细表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryRelaOutDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的代销出库单主表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryRelaAffiOutMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的代销出库单明细表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryRelaAffiOutDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的调拨单主表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryRelaTranMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的调拨单明细表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryRelaTranDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的代销调拨单主表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryRelaAffiTranMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的代销调拨单明细表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryRelaAffiTranDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 材料关闭<BR>
	 * @param entityList
	 * @return String
	 * @throws DataAccessException
	 */
	public String updateMatCommonOutApplyCheckCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 查询对应的科室需求计划主表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryRelaReqMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对应的科室需求计划明细表信息<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryRelaReqDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	// 入库模板打印（包含主从表）
	public String queryMatOutByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;

	// 判断科室申请审核是否已经生成科室需求计划
	public List<Map<String, Object>> queryMatOutRequirelExists(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String,Object> queryMatOutByPrintPage(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量作废<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateNullifyMatCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	String queryDetailN(Map<String, Object> entityMap)
			throws DataAccessException;
	
}
