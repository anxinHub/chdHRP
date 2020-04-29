package com.chd.hrp.htc.service.relative.cost.deptincome;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.relative.cost.deptincome.HtcRelativeDeptIncome;
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

public interface HtcRelativeDeptIncomeService {

	/**
	 * 
	 */
	public String addHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * 
	 */
	public String queryHtcRelativeDeptIncome(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcRelativeDeptIncome queryHtcRelativeDeptIncomeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String deleteHtcRelativeDeptIncome (Map<String,Object> entityMap)throws DataAccessException; 
	
	/**
	 * 
	 */
	public String deleteBatchHtcRelativeDeptIncome(List<Map<String,Object>> list)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcRelativeDeptIncome(Map<String,Object> entityMap)throws DataAccessException;
}
