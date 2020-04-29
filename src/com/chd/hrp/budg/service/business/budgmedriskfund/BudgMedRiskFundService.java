/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgmedriskfund;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 提取医疗风险基金预算编制
 * @Table:
 * BUDG_MED_RISK_FUND
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedRiskFundService extends SqlService {

	/**
	 * 获取科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 生成 拷贝数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyBudgMedRiskFund(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 所传 科室 的 科室月份收入预算值  同时查询医疗风险基金的提取比例
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWorkload(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	


	
}
