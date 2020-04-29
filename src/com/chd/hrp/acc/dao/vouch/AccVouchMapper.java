/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccVouch;

/**
 * @Title. @Description. 凭证主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccVouchMapper extends SqlMapper {

	/**
	 * @Description 凭证主表<BR>
	 *              添加AccVouch
	 * @param AccVouch
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAccVouch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              批量添加AccVouch
	 * @param AccVouch
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAccVouch(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	//public List<AccVouch> queryAccVouch(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch出纳相关数据分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchCashier(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch审核数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	//public List<AccVouch> queryAccVouchAudit(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchAudit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 已记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchAccounting(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 未记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchUnAccounting(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchAccountingCount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch现金流量标注 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchCashFlow(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch所有数据
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	//public List<AccVouch> queryAccVouch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch中出纳相关的所有数据
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchCashier(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch中出纳相关的所有数据
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchAccounting(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 未记账数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchUnAccounting(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch记账 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccVouchAccountingCount(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch现金流量标注 统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchCashFlow(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch中审核相关的数据所有数据
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	//public List<AccVouch> queryAccVouchAudit(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccVouchAudit(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouch统计数据相关分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchStatistics(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询AccVouchByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public AccVouch queryAccVouchByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              删除AccVouch
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAccVouch(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              批量删除AccVouch
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchAccVouch(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              更新AccVouch
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAccVouch(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateAccVouchUser(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              批量更新AccVouch
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchAccVouch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 作废、取消作废，更新票据号状态
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAccPaperDetailState(List<Map<String, Object>> entityMap) throws DataAccessException;
	

	
	/**
	 * @Description 凭证主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryAccVouchNextval(Map<String, Object> entityMap) throws DataAccessException;
	
	//签字、审核
	public int updateBatchAccVouchCashier(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//取消签字
	public int updateBatchAccVouchUnCashier(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	//取消审核
	public int updateBatchAccVouchUnAudit(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	
	public int updateBatchAccVouchCashierNotBank(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateBatchAccVouchCashierBank(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateBatchAccVouchLabel(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public List<AccVouch> queryAccVouchSubjCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public int updateBatchAccVouchCashFlow(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateAccVouchCashFlowBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询 现金日记账
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccCashJournal(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 凭证主表<BR>
	 *              查询 现金日记账
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AccVouch> queryAccCashJournal(Map<String, Object> entityMap) throws DataAccessException;
	
	

	//凭证签字、作废、审核、记账（查询流程）
	public List<Map<String,Object>> queryVouchState(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询签字、作废、审核、记账是否已被修改
	public Integer queryAccVouchList(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *   查询记账主页面数据           
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchAccount(Map<String, Object> entityMap) throws DataAccessException;
	

	//执行记账和取消记账存储过程
	public Integer updateBatchAccountingAccVouch(Map<String, Object> entityMap) throws DataAccessException;
	//查询记账报告
	public List<Map<String, Object>> queryAccVouchAccountingReport(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              查询断号凭证
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchNeaten(Map<String, Object> entityMap) throws DataAccessException;

	public AccVouch queryAccVouchDetailByVouchid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证主表<BR>
	 *              修改凭证之间的断号
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchAccVouchLen(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 凭证主表,出纳签字，凭证审核<BR>
	 *     按凭证分录展示
	 * @param entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccVouchPayTypeCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccVouchPayTypeCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryAccVouchFlowByNodeId(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public Integer queryAccVouchFlow(Map<String, Object> entityMap) throws DataAccessException;

	
	
	public List<Map<String, Object>> accAccountingCheckIndex1(Map<String, Object> entityMap) throws DataAccessException;

	
	public List<Map<String, Object>> accAccountingCheckIndex2(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> accAccountingCheckIndex3(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> accAccountingCheckIndex4(Map<String, Object> entityMap) throws DataAccessException;

	
	public int accUnAccountingCheckIndex1(Map<String, Object> entityMap) throws DataAccessException;

	public int accUnAccountingCheckIndex2(Map<String, Object> entityMap) throws DataAccessException;

	public int accUnAccountingCheckIndex3(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<String> queryAccVouchDetailByVouchIdList(Map<String, Object> entityMap) throws DataAccessException;

	public List<String> queryAccVouchListByBank(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int addBatchAccVouchOfRed(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AccVouch> queryAccVouchDetailCountByVouchId(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AccVouch> queryAccVouchCashByVouchDetailId(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccVouchAdjunct(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//审核、签字、取消判断凭证状态
	public int queryAccVouchAuditCashByCheck(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("node_id") int node_id,@Param("list") List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//取消审核、取消签字 
	public int queryAccVouchUnAuditCashByCheck(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("node_id") int node_id,@Param("list") List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	//审核判断现金银行凭证
	public int queryAccVouchAuditByCheck(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("list") List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAccCashJournalPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<String> queryAccVouchAconDH(Map<String, Object> entityMap) throws DataAccessException;
	
	public int deleteBatchAccVouchByDiff(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int updateAuditAll(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryVouchByState99(Map<String, Object> entityMap) throws DataAccessException;
}
