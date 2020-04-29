/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.elsecost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 其他支出预算调整
 * @Table:
 * BUDG_ELSE_COST_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseCostAdjMapper extends SqlMapper{
	/**
	 * 审核、销审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public  int  auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
   
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
	public String queryMaxCodeFromAdj(Map<String, Object> mapVo);
	
	/**
	 * 其他支出预算调整 状态查询（审核、销审 校验数据用 ）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgElseCostAdjState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 页面跳转前查询审核表中是否存在当前预算年度数据
	 * @param mapVo
	 * @return
	 */
	public int queryCheckDataExists(Map<String, Object> mapVo)  throws DataAccessException;
	
}
