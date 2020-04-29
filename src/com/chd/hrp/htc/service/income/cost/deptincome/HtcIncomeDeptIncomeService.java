package com.chd.hrp.htc.service.income.cost.deptincome;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.income.cost.deptincome.HtcIncomeDeptIncome;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcIncomeDeptIncomeService {

	/**
	 * 
	 */
	public String addHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * 
	 */
	public String queryHtcIncomeDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcIncomeDeptIncome queryHtcIncomeDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String deleteHtcIncomeDeptIncome (Map<String,Object> entityMap)throws DataAccessException; 
	
	/**
	 * 
	 */
	public String deleteBatchHtcIncomeDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcIncomeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
