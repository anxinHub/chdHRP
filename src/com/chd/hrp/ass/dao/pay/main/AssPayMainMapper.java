/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.pay.main;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 付款主表
 * @Table:
 * ASS_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPayMainMapper extends SqlMapper{
	public int updatePayMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
//付款打印主表
	public List<Map<String, Object>> queryaAssPayBatch(Map<String, Object> map);
//付款打印从表
	public List<Map<String, Object>> queryAssPay_detail(Map<String, Object> map);

	public Map<String, Object> querAssPayByPrint(Map<String, Object> map);

	public List<String> queryAssPayState(Map<String, Object> mapVo)throws DataAccessException;

	public Map<String, Object> queryAssPayDYMain(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssPayDetailDY(
			Map<String, Object> entityMap);
}
