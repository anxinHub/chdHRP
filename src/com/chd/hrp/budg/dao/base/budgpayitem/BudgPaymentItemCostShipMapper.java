/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgpayitem;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 支出项目与支出预算科目对应关系
 * @Table:
 * BUDG_PAYMENT_ITEM_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgPaymentItemCostShipMapper extends SqlMapper{
	
	/**
	 * 根据支出项目编码 查询支出项目ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryItemCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 支出项目 下拉框 （添加页面用）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPaymentItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
}
