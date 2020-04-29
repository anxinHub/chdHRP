/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.bill;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 发票主表
 * @Table:
 * ASS_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBillMainService extends SqlService {  
	String updateBillConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
/**
 * 付款发票打印
 * @param map
 * @return
 * @throws DataAccessException
 */
	Map<String, Object> queryAssBillDY(Map<String, Object> map)
			throws DataAccessException;
	String updateReAudit(List<Map<String, Object>> listVo);
	String queryimport(Map<String, Object> page);
	List<String> queryAssBillState(Map<String, Object> mapVo)throws DataAccessException;
	
	String queryAssInAndAssChange(Map<String, Object> mapVo)throws DataAccessException;
	
	String importInAndChange(Map<String, Object> mapVo)throws DataAccessException;
	String queryimports(Map<String, Object> page);
	/**
	 * 生成发票单
	 * @param page
	 * @return
	 */
	String queryInMainBill(Map<String, Object> page);
	String importInAndMap(Map<String, Object> mapVo);
}
