/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.pay.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_BACK_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackPayMainMapper extends SqlMapper{
	public int updatePayMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * 退款主表打印
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssBackPayBatch(
			Map<String, Object> map);
	/**
	 * 退款从表打印
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryAssBackPay_detail(
			Map<String, Object> map);

	public Map<String, Object> querAssBackPayByPrint(Map<String, Object> map);

	public List<String> queryAssBackPayState(Map<String, Object> mapVo)throws DataAccessException;
}
