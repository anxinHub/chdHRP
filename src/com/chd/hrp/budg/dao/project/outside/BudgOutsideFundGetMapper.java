/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.outside;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgOutsideFundGet;
/**
 * 
 * @Description:
 * 外拨经费到账
 * @Table:
 * BUDG_OUTSIDE_FUND_GET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgOutsideFundGetMapper extends SqlMapper{

	List<BudgOutsideFundGet> queryBudgOutsideFundGet(Map<String, Object> entityMap, RowBounds rowBounds);

	List<BudgOutsideFundGet> queryBudgOutsideFundGet(Map<String, Object> entityMap);

	int addBudgOutsideFundGet(Map<String, Object> entityMap);

	int updateBudgOutsideFundGet(Map<String, Object> entityMap);

	int addbudgProjYear(Map<String, Object> map);

	int updatebudgProjYearbyYearPidSid(Map<String, Object> map);

	List<Map<String, Object>> queryOutsideFundGetByUniqueness(Map<String, Object> mapVo);
	
}
