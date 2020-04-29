/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.sup.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 100101 送货单主表
 * @Table:
 * SUP_DELIVERY_MAIN
 * @Author: bell  
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface SupDeliveryMainService extends SqlService {
	
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	public String generate(Map<String,Object> entityMap)throws DataAccessException;
	public String generateAffi(Map<String,Object> entityMap)throws DataAccessException;
	public String generateInDetail(Map<String,Object> entityMap)throws DataAccessException;
	public String updateDeliveryDetailGenerate(Map<String,Object> entityMap)throws DataAccessException;
	public String addOrUpdateBatch(Map<String,Object> entityMap)throws DataAccessException;
	public List<String> queryDeliveryOrderRelaExists(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 更新订单状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateMatOrderState(Map<String, Object> entityMap) throws DataAccessException;
	public void updateMatAffiOrderState(Map<String, Object> mapVo)  throws DataAccessException;

}
