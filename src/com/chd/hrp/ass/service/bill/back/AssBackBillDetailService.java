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
 * 红冲发票明细表
 * @Table:
 * ASS_BACK_BILL_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackBillDetailService extends SqlService {
	List<Map<String, Object>> queryByAssBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryByAll(Map<String,Object> entityMap)throws DataAccessException;
}
