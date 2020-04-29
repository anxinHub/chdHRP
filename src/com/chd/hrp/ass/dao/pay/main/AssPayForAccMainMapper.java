/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
  
package com.chd.hrp.ass.dao.pay.main;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.pay.main.AssPayForAccMain;


public interface AssPayForAccMainMapper extends SqlMapper{

	/**
	 * @Description 
	 * 查询结果集state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPayForAccMain> queryAssPayForAccMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集state
1:未审核；2审核；3:记帐
PAY_BILL_TYPE: 0付款 1 退款<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPayForAccMain> queryAssPayForAccMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 审核、消审、确认、取消确认
	 * @param entityList
	 */
	public void updateAssPayState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	
}
