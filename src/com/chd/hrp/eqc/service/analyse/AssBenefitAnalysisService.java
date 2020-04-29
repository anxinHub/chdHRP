
/*
 *
 */
 package com.chd.hrp.eqc.service.analyse;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 09设备操作员 ASS_EQEquipOperator Service接口
*/
public interface AssBenefitAnalysisService extends SqlService {
	
	/**
	 * 资产收益分析报表 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssProfitAnalysis(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 投资收益报表 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryInvestmentBenefitAnalys(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 本量利分析 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCvpAnalysis(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 设备收支明细 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssIncomAndCost(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 贵重医疗设备使用效益分析表 查询数据 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssUseBenefitAnalys(Map<String, Object> page) throws DataAccessException;

	
}
