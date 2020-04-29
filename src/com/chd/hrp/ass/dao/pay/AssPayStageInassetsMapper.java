/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.pay;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产分期付款表_专用设备
 * @Table:
 * ASS_PAY_STAGE_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssPayStageInassetsMapper extends SqlMapper{
	List<Map<String, Object>> queryByAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
	
	Integer queryStageInassetsMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	int updateBatchPayMoney(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	int updateDelBatchBillMoney(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	int updateBatchBillMoney(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	int updateBatchBackPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	int updateBatchBackBill(List<Map<String, Object>> entityMap)throws DataAccessException;
}
