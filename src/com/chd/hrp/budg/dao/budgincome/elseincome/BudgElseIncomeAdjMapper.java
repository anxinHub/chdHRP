/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.elseincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 其他收入预算调整
 * @Table:
 * BUDG_ELSE_INCOME_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseIncomeAdjMapper extends SqlMapper{
	/**
	 * 审核、销审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public  int  auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
   
	/**
	 * @param entityMap
	 * @return
	 *   删除文件单号
	 * @throws DataAccessException
	 */
	public int deleteBudgElseIncomeAdjFile(Map<String, Object> entityMap) throws  DataAccessException;
	/**
	 * @param entityMap
	 * @return
	 * 添加文件单号
	 * @throws DataAccessException
	 */
	public int addBudgElseIncomeAdjFile(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 其他收入预算 整前数据查看查询 
	 * @param entityMap
	 * @return
	 * 
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgElseIncomeAdjBefore(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *  其他收入预算 整前数据查看查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgElseIncomeAdjBefore(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询  上次预算下达日期
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLast_issue_data(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 备份 其他收入预算 数据 到 其他收入预算备份 表 BUDG_ELSE_INCOME_COPY
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int copyData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 删除 其他收入预算 备份数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteCopyData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 其他收入预算调整 状态查询（审核、销审 校验数据用 ）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgElseIncomeAdjState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 调整未下达
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBcState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 调整表中最大单号  年度  下达日期 添加页面使用
	 */
	public Map<String, Object> queryMaxCheckData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询数据状态 限制操作
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAdjState(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询审批表中最大单据号 
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMaxCodeFromCheck(Map<String, Object> map) throws DataAccessException;
	/**
	 * 查询 调整表中最大单号  年度  下达日期 修改页面使用
	 */
	public Map<String, Object> queryMaxCheckDataUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除数据后   还原最大单号
	 * @param mapVo
	 */
	public void updateMaxNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询调整表中最大单号
	 * @param mapVo
	 * @return
	 */
	public String queryMaxCodeFromAdj(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 页面跳转前查询审核表中是否存在当前预算年度数据
	 * @param mapVo
	 * @return
	 */
	public int queryCheckDataExists(Map<String, Object> mapVo) throws DataAccessException;

}
