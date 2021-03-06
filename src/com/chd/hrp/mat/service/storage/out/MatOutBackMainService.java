/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.out;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface MatOutBackMainService extends SqlService {


	/**
	 * @Description 
	 * 查询mat_inv_hold 表 返回材料 用来计算库存<BR>
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutMainByInvHold(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询mat_fifo_balance 表 返回材料 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutMainByFifoBalance(Map<String,Object> entityMap) throws DataAccessException;
	public String queryMatOutMainByFifoBalanceOld(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 配套导入结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutDetailByMatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 定向出库结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutDetailHistory(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutBackMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 复制功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMatOutBackMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
 
	/**
	 * @Description 
	 * 消审功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatOutBackMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatOutBackMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmOutMatOutBackMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInMainByIsDir(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInDetailByIsDir(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDeptMatchByMatchd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMatOutBackMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatOutBackDetailByOutId(Map<String,Object> entityMap) throws DataAccessException;
	public String queryMatOutBackDetailByCode(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取下一张或上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatOutBackMainBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException;

	
/*	//入库模板打印（包含主从表）
    public String queryMatOutByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;*/
    //入库模板打印（包含主从表）
    public Map<String,Object> queryMatOutBackByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
    //合并冲账
    public String mergeOffsetMatOutMain(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMatApplyMainOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领明细数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMatApplyDetailOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 申领单汇总生成出库单--组装出库主表
	 * @param entityMap
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> queryOutMainByAppCollect(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 申领单汇总生成出库单--组装出库明细表
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryOutDetailByAppCollect(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询材料批次列表
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMatInvBatchList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 选择材料返回List
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryOutInvListByChoiceInv(List<Map<String,Object>> entityList) throws DataAccessException;
	/**
	 * 出库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryOutBackDetails(Map<String, Object> entityMap) throws DataAccessException;
	
}
