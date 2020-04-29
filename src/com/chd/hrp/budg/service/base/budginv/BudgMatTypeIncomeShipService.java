/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budginv;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 末级物资分类对应预算收入科目。对应关系中要求物资分类对收入科目，为一对一或一对多。
 * @Table:
 * BUDG_MAT_TYPE_INCOME_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMatTypeIncomeShipService extends SqlService {
	/**
	 * 
	 * @param mapVo
	 * @return 添加页面物资分类
	 * @throws DataAccessException
	 */
	public String queryMatTypeIncome(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * queryCostSubjByCode
	 * @param mapVo
	 * @return
	 */
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * queryBudgTypeDictByCode
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryBudgTypeDictByCode(Map<String, Object> mapVo) throws DataAccessException ;
	
}
