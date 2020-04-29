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
import com.chd.hrp.ass.entity.bill.back.AssBackBillStage;
/**
 * 
 * @Description:
 * 051601 红冲发票卡片分期付款
 * @Table:
 * ASS_BACK_BILL_STAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssBackBillStageService extends SqlService {
	List<Map<String, Object>> queryByAssBackBillNo(Map<String,Object> entityMap)throws DataAccessException;
	
	List<AssBackBillStage> queryByConfirmAssBill(Map<String,Object> entityMap)throws DataAccessException;	
}
