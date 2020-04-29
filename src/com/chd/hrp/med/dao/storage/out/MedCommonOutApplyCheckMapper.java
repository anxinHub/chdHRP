/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.storage.out;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室申请单主表
 * @Table:
 * MED_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedCommonOutApplyCheckMapper extends SqlMapper{

	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMedApplyMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMedApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	 /**
	 * @Description 
	 * 批量退回科室<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedApplyBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	 /**
	 * @Description 
	 * 批量关闭药品<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedCommonOutApplyCheckCloseInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 出库单主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return T
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryOutMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 查询管理部门对应的仓库信息<BR> 
	 * @param  entityMap <BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryStoreByDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 调拨单主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return T
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryTranMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 获取选中的明细数据<BR> 
	 * @param  entityMap <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySelectDetailForOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 汇总生成出库单查询明细<BR> 
	 * @param  entityMap <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDetailByCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 科室需求计划主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return T
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryReqMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 科室需求计划明细表组装<BR> 
	 * @param  entityMap <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryReqDetail(Map<String,Object> entityMap)throws DataAccessException;

	/*生成各个单据所需数据库语句----------------------begin-----------------------------------*/
	/**
	 * @Description 
	 * 出库单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedOutMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 出库单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedOutDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedOutMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedOutMainByAppBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedOutMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedAffiOutSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedAffiOutDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiOutByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiOutByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 调拨单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedTranMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 调拨单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedTranDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedTranMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedTranMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 代销调拨单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedAffiTranMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销调拨单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedAffiTranDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiTranMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiTranMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedAffiTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 科室需求计划主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedRequireMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求计划明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedRequireDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedRequireMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedRequireMainByAppBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加科室需求计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedRequireDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedRequireMainByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除科室需求计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedRequireDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/*生成各个单据所需数据库语句----------------------end-------------------------------------*/
	
	/**
	 * @Description 
	 * 查询对应的单据列表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedApplyRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的出库单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryRelaOutMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的出库单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryRelaOutDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的代销出库单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryRelaAffiOutMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的代销出库单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryRelaAffiOutDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的调拨单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryRelaTranMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的调拨单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryRelaTranDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的代销调拨单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryRelaAffiTranMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的代销调拨单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryRelaAffiTranDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的科室需求计划主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryRelaReqMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对应的科室需求计划明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryRelaReqDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMedOutPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
	//入库明细表模板打印
	public List<Map<String, Object>> queryMedOutPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	//判断科室申请审核是否已经生成科室需求计划
	public List<Map<String, Object>> queryMedOutRequirelExists(Map<String,Object> entityMap) throws DataAccessException;
		
	//科室申领审核主表模板打印
    public Map<String, Object> queryApplyCheckPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;	
    
	//科室申领审核主表批量打印
    public  List<Map<String, Object>> queryApplyCheckPrintTemlateByMainInBatch(Map<String,Object> entityMap) throws DataAccessException;	
	
}
