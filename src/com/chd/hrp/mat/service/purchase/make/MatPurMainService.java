package com.chd.hrp.mat.service.purchase.make;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description:
 * 04114 采购计划编制
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatPurMainService extends SqlService{
	
	/**
	 * @Description 
	 * 采购计划编制<BR>中止采购计划
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatPurPlanState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return String 
	 * @throws DataAccessException
	 */
	public String queryMatRequireMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按材料ID 查询材料当前库存明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvCurAmountDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 按科室计划单ID 查询科室需求计划主表
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMatRequireMainByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 按一个或多个科室计划单ID 查询科室计划单明细 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatRequireDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 按需求计划生成 添加采购计划 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addProdMatPurMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 计划数量明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatRequireAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 科室需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatReqByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * 安全库存导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurSafe(Map<String, Object> entityMap) throws DataAccessException;
	//采购模板打印（包含主从表）
	public String queryMatMakeByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	//采购模板打印（按供应商汇总明细打印） 
	public String queryMatMakeByDetailPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * 查询 仓库需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatReqStoreMain(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 组装汇总的仓库明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreCollectData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 仓库需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMatReqDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 组装汇总的科室明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptCollectData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看供应商信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySupDetails(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 组装明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPurDetailCollectData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 关闭材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatPurCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 取消关闭材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatPurCancleCloseInv(List<Map<String, Object>> entityList)  throws DataAccessException;
	/**
	 * 查看关闭的材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购数量来源查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurAmountRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改材料数量对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatPurAmountRela(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, Object> queryMatPurMain(Map<String, Object> map) throws DataAccessException;

	Map<String, Object> queryMatPurMainDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String addMatPurPlanBySecuLimit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMatPurDetailInv(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateMatPurRelaTables(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 采购计划修改 查询材料近三个月,六个月入出库数量
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String queryMatInvRecentInOutAmount(Map<String, Object> entityMap)throws DataAccessException, ParseException;
	

	//public String queryPurDetailOrder(List<Map<String, Object>> listVo) throws DataAccessException;
}
