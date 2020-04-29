/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.storage.out;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedOutMain;
/**
 * 
 * @Description:
 * MED_OUT_MAIN
 * @Table:
 * MED_OUT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedOutMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMedOutMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_inv_hold 表 返回药品 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMainByInvHold(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_inv_hold 表 返回药品 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMainByInvHold(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_fifo_balance 表 返回药品 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMainByFifoBalance(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询med_fifo_balance 表 返回药品 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMainByFifoBalance(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_out_main 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMain(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询med_out_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedOutMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInMain 表 返回药品 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInMainByIsDir(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInMain 表 返回药品 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInMainByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInDetail 表 返回药品 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInDetailByIsDir(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MedInDetail 表 返回药品 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MED_DEPT_MEDCH 表 返回药品 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedDeptMatchByMatchd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MED_DEPT_MEDCH 表 返回药品分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedDeptMatchByMatchd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @param <T>
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMedOutMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              下一篇
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryMedOutMainNext(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              上一篇
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryMedOutMainPre(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              审核
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAuditBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *             确认
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateConfirmBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 确认前单据状态校验<BR>  
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedOutStateForConfirm(List<Map<String,Object>> entityList) throws DataAccessException;

	//入库主表模板打印
   public Map<String, Object> queryMedOutPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
   
   public List<Map<String, Object>> queryMedOutPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		
		//入库明细表模板打印
   public List<Map<String, Object>> queryMedOutPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMedApplyMainOut(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @param rowBounds
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMedApplyMainOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询科室申领明细数据
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMedApplyDetailOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 申领单汇总生成出库单--组装出库主表
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryOutMainByAppCollect(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 申领单汇总生成出库单--组装出库明细表
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryOutDetailByAppCollect(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询药品批次列表
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMedInvBatchList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询药品批次列表--分页
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMedInvBatchList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryOutDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryOutDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
