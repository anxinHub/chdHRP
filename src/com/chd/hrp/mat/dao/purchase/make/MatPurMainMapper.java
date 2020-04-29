package com.chd.hrp.mat.dao.purchase.make;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 04114 采购计划编制
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell  
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatPurMainMapper extends SqlMapper{
	
	//主页面查询
	public List<Map<String, Object>> queryMain(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 采购计划编制<BR>批量添加 采购计划编制明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>批量删除 采购计划明细
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatPurDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>分页查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatPurDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>批量删除 采购计划主表
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatPurMain(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>中止采购计划
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPurPlanState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * 当前年月单据号管理表中是否存在数据
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryIsExists(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取最大的流水号
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMaxCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新 mat_no_manage表
	 * @param entityMap
	 * @return int 
	 */
	public int updateMatNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 向 单据号表中插入数据
	 * @param entityMap
	 * @return int 
	 * @throws DataAccessException
	 */
	public int addMatNoMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return List 
	 * @throws DataAccessException
	 */
	public List<?> queryMatRequireMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return List 
	 * @throws DataAccessException
	 */
	public List<?> queryMatRequireMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 按科室计划单ID 查询科室需求计划主表
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMatRequireMainByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 按科室计划单ID 查询科室计划单明细 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatRequireDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 分页查询
	 * 按科室计划单ID 查询科室计划单明细 
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMatRequireDetailByCode(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询 材料当前库存明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMatInvCurAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>
	 * @param entityMap
	 * @return int 科室需求采购计划关系 添加 
	 * @throws DataAccessException
	*/
	public int addBatchMatReqPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>
	 * @param entityMap
	 * @return int  修改科室需求计划单状态 
	 * @throws DataAccessException
	*/
	public int updateMatRequireMain (List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询 计划数量明细
	 * @param entityMap
	 * @return List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryReqAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>修改 科室需求采购计划关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchMatReqPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>删除 科室需求采购计划关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatReqPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>修改科室需求计划单状态为已审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatRequireMainState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>按采购计划单ID 查询科室需求采购计划对应关系
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatStoreReqPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatDeptReqPurRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 采购计划编制<BR>按需求计划单ID 查询需求明细 
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	
	public List<Map<String,Object>> queryMatReqByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>按需求计划单ID 分页查询需求明细 
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	
	public List<Map<String,Object>> queryMatReqByCode(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;

	/**
	 * 库存安全导入--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatPurSafe(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 采购管理--删除明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteDetailByPurId(Map<String, Object> entityMap) throws DataAccessException;
	
	//主表序列
	public Long queryMatPurNextId() throws DataAccessException;
	//明细序列
	public Long queryMatPurDetailNextId() throws DataAccessException;
	
	public Long existsMatPurMainStateBatch(List<Map<String,Object>> entityMap) throws DataAccessException;

	//入库主表模板打印
	public Map<String, Object> queryMatMakePrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatMakePrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//入库明细表模板打印
	public List<Map<String, Object>> queryMatMakePrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	//模板打印（按供应商汇总明细打印）
	public List<Map<String, Object>> queryMatMakeDetailPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	* 查询 仓库需求计划
	* @param entityMap
	* @return List 
	* @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatReqStoreMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatReqStoreMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 组装汇总的仓库明细数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryStoreNewDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 更新仓库需求计划状态 is_closed = 3
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateStoreReqState(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 更新仓库需求计划状态 is_closed= 0
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateStoreReqStateZero(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	* 查询 科室需求计划
	* @param entityMap
	* @return List 
	* @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatReqDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatReqDeptMain(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 组装汇总的科室明细数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptNewDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 更新科室需求计划状态 is_closed = 3
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int updateDeptReqState(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 更新科室需求计划状态 is_closed = 0
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int updateDeptReqStateZero(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 删除页面删除的明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteForUpdate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量修改明细数据
	 * @param updateList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchMatPurDetail(List<Map<String, Object>> updateList) throws DataAccessException;
	
	/**
	 * 查询明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查看供应商信息
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> querySupDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> querySupDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 组装明细数据
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPurDetailCollectData(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 组装明细数据  不汇总材料
	 * @param detailList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPurDetailNotCollectData(List<Map<String, Object>> detailList) throws DataAccessException;
	/**
	 * 关闭材料
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMatPurCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 取消关闭材料
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMatPurCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 查看关闭的材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatPurCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购数量来源查询
	 * @param entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatPurAmountRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>批量修改采购数量
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatPurDetailAmountBatch(List<Map<String,Object>> entityList) throws DataAccessException;
	/**
	 * 更改单据的状态
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatApplyCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<Map<String, Object>> queryMatApplyDetailList(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public void addMatPurPlanBySecuLimit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除对应关系表
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deleteMatApplyDetailInv(List<Map<String, Object>> entityList) throws DataAccessException;
	public List<Map<String, Object>> queryMatApplyDetailListById(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatStoreReqPurRelaById(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatDeptReqPurRelaById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 取消关闭材料
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMatApplyCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	public List<Map<String, Object>> queryMatPurExistsNoDetails(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 采购计划明细查询(模板打印)
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryPurDetailForTemplatePrint(
			Map<String, Object> mapVo);
	/**
	 * 采购计划修改  查询当前材料近三个月,六个月入出库数量
	 * @param entityMap
	 * @return
	 */
	public List<?> queryMatInvRecentInOutAmount(Map<String, Object> entityMap);
	
}
