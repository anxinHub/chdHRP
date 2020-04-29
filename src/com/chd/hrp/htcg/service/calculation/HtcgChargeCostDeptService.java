package com.chd.hrp.htcg.service.calculation;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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

public interface HtcgChargeCostDeptService {

	public String addHtcgChargeCostSchemeRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgChargeCostSchemeRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initHtcgChargeCostDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgChargeCostSchemeRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public String deleteBathcHtcgChargeCostDept(List<Map<String, Object>> list)throws DataAccessException;
}
