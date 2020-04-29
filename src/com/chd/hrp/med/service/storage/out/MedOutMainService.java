/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.out;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface MedOutMainService extends SqlService {


	/**
	 * @Description 
	 * 查询med_inv_hold 表 返回药品 用来计算库存<BR>
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutMainByInvHold(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询med_fifo_balance 表 返回药品 用来计算库存 不分页<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutMainByFifoBalance(Map<String,Object> entityMap) throws DataAccessException;
	public String queryMedOutMainByFifoBalanceOld(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 配套导入结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutDetailByMatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 定向出库结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutDetailByIsDir(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutDetailHistory(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 复制功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 冲账功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String offsetMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 消审功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核功能<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmOutMedOutMain(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInMainByIsDir(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInDetailByIsDir(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedDeptMatchByMatchd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMedOutMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedOutDetailByOutId(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取下一张或上一张入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMedOutMainBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException;

	
/*	//入库模板打印（包含主从表）
    public String queryMedOutByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;*/
    //入库模板打印（包含主从表）
    public Map<String,Object> queryMedOutByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
    //合并冲账
    public String mergeOffsetMedOutMain(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领主表数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMedApplyMainOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询科室申领明细数据
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMedApplyDetailOut(Map<String,Object> entityMap) throws DataAccessException;

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
	 * @Description 查询药品批次列表
	 * @param entityMap
	 * @return String
	 * @throws Exception
	 */
	public String queryMedInvBatchList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 选择药品返回List
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
	public String queryOutDetails(Map<String, Object> entityMap) throws DataAccessException;

	public void updateMedApplyOutRela(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查看出库单是否是科室申请生成的
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryMedOutMainIsApply(Map<String, Object> mapVo) throws DataAccessException;
	
}
