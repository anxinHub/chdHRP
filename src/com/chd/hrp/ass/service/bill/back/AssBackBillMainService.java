/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.bill.back;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 红冲发票主表
 * @Table:
 * ASS_BACK_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackBillMainService extends SqlService {
	String updateBackBillConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	Map<String, Object> queryAssBackBillDY(Map<String, Object> map)
			throws DataAccessException;

	String updateNotToExamineBackBillMain(List<Map<String, Object>> listVo);

	String queryimport(Map<String, Object> page)throws DataAccessException;

	List<String> queryAssBackBillState(Map<String, Object> mapVo)throws DataAccessException;
	
	String queryAssInAndAssChange(Map<String, Object> map)throws DataAccessException;

	String importInAndChangeBack(Map<String, Object> map)throws DataAccessException;

	String queryimports(Map<String, Object> page);
}
