/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budginv;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface BudgMatTypeIncomeShipMapper extends SqlMapper{
	public List<Map<String,Object>> queryMatTypeIncome(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String, Object> queryBudgTypeDictByCode(Map<String, Object> mapVo) throws DataAccessException;
}
