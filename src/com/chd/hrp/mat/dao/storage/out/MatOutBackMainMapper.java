/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.storage.out;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatOutMain;
/**
 * 
 * @Description:
 * MAT_OUT_MAIN   
 * @Table:
 * MAT_OUT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0   
 */
 

public interface MatOutBackMainMapper extends SqlMapper{
	
	/**
	 * @Description 主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public Long queryMatOutBackMainSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_inv_hold 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutMainByInvHold(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_inv_hold 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutMainByInvHold(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_fifo_balance 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutMainByFifoBalance(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询mat_fifo_balance 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutMainByFifoBalance(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_out_main 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutBackMain(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询mat_out_main 表  分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatOutBackMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInMain 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInMainByIsDir(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInMain 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInMainByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInDetail 表 返回材料 用来计算库存 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInDetailByIsDir(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MatInDetail 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatInDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MAT_DEPT_MATCH 表 返回材料 不分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatDeptMatchByMatchd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询MAT_DEPT_MATCH 表 返回材料分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatDeptMatchByMatchd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @param <T>
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMatOutBackMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              下一篇
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryMatOutBackMainNext(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 主表<BR>
	 *              上一篇
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryMatOutBackMainPre(Map<String, Object> entityMap) throws DataAccessException;
	
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
	public int existsMatOutBackStateForConfirm(List<Map<String,Object>> entityList) throws DataAccessException;

	//入库主表模板打印
   public Map<String, Object> queryMatOutBackPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
   
   public List<Map<String, Object>> queryMatOutBackPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		
		//入库明细表模板打印
   public List<Map<String, Object>> queryMatOutBackPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMatApplyMainOut(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @param rowBounds
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMatApplyMainOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询科室申领明细数据
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMatApplyDetailOut(Map<String,Object> entityMap) throws DataAccessException;

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
	 * @Description 查询材料批次列表
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMatInvBatchList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询材料批次列表--分页
	 * @param entityMap
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryMatInvBatchList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryOutBackDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryOutBackDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
