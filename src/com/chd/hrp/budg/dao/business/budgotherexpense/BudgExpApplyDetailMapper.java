/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgotherexpense;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 费用申报明细
 * @Table:
 * BUDG_EXP_APPLY_DETAIL
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgExpApplyDetailMapper extends SqlMapper{

	List<Map<String, Object>> queryBudgExpApplyDetail(Map<String, Object> mapVo)throws DataAccessException;
	
}
