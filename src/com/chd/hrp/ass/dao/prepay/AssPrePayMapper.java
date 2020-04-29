/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.prepay;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 预付款核定表
 * @Table:
 * ASS_PRE_PAY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPrePayMapper extends SqlMapper{
	public int updateBatchPayMoney(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryByVenAndAss(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryByVenAndAssSource(Map<String,Object> entityMap)throws DataAccessException;

	public int updateCurMoney(Map<String, Object> entityMap)throws DataAccessException;
}
