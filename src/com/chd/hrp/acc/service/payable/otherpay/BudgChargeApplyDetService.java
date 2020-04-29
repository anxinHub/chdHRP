/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
  
package com.chd.hrp.acc.service.payable.otherpay;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 借款明细
 * @Table:
 * BUDG_BORR_BEGAIN_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgChargeApplyDetService {
	String queryForUpdate(Map<String,Object> entityMap)throws DataAccessException;
	
	String deleteBudgChargeApplyDet(Map<String,Object> entityMap)throws DataAccessException;
	
}
