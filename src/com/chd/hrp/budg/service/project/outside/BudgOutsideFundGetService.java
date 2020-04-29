/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.outside;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
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
 

public interface BudgOutsideFundGetService extends SqlService {

	String queryBudgOutsideFundGet(Map<String, Object> page);

	String addBudgOutsideFundGet(Map<String, Object> mapVo);

	String updateBudgOutsideFundGet(Map<String, Object> mapVo);

	String deleteBudgOutsideFundGet(List<Map<String, Object>> listVo);

	String confirmAddOrUpdateBudgOutsideFundGet(Map<String, Object> mapVo);

}
