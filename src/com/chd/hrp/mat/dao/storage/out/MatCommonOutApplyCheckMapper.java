/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.storage.out;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室申请单主表
 * @Table: 
 * MAT_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatCommonOutApplyCheckMapper extends SqlMapper{

	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMatApplyMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMatApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	 /**
	 * @Description 
	 * 批量退回科室<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatApplyBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	 /**
	 * @Description 
	 * 批量关闭材料<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatCommonOutApplyCheckCloseInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	public Long queryMatOutMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 出库单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatOutDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatOutMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatOutMainByAppBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatOutMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiOutSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiOutDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiOutByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销出库单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiOutByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销出库单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiOutDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 调拨单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatTranMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 调拨单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatTranDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatTranMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatTranMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 代销调拨单主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiTranMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销调拨单明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatAffiTranDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiTranMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加代销调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatAffiTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销调拨单主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiTranMainByApp(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除代销调拨单明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatAffiTranDetailByApp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 科室需求计划主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatRequireMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求计划明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatRequireDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatRequireMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatRequireMainByAppBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加科室需求计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMatRequireDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除科室需求计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatRequireMainByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除科室需求计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMatRequireDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/*生成各个单据所需数据库语句----------------------end-------------------------------------*/
	
	/**
	 * @Description 
	 * 查询对应的单据列表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatApplyRela(Map<String,Object> entityMap)throws DataAccessException;
	
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
	public Map<String, Object> queryMatOutPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
	//入库明细表模板打印
	public List<Map<String, Object>> queryMatOutPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	//判断科室申请审核是否已经生成科室需求计划
	public List<Map<String, Object>> queryMatOutRequirelExists(Map<String,Object> entityMap) throws DataAccessException;
		
	//科室申领审核主表模板打印
    public Map<String, Object> queryApplyCheckPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;	
    
	//科室申领审核主表批量打印
    public  List<Map<String, Object>> queryApplyCheckPrintTemlateByMainInBatch(Map<String,Object> entityMap) throws DataAccessException;	
    /**
	 * @Description 
	 * 批量作废<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateByNullifyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<?> queryDetailN(Map<String, Object> entityMap);

	public List<?> queryDetailN(Map<String, Object> entityMap,
			RowBounds rowBounds);
	
}
