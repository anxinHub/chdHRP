/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgawardsitem;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 奖金项目与预算支出科目对应关系
 * @Table:
 * BUDG_AWARDS_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAwardsItemCostShipMapper extends SqlMapper{
	
	/**
	 * 奖金项目 下拉框（添加页面 用）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAwardsItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
}
