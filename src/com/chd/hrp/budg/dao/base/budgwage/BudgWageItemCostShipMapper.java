/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgwage;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 工资项目与预算支出科目对应关系
 * @Table:
 * BUDG_WAGE_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWageItemCostShipMapper extends SqlMapper{
	
	/**
	 * 添加页面 工资项目 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryWageItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询支出预算科目是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
}
