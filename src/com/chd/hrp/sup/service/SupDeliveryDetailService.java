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
 * 100102 送货单明细表
 * @Table:
 * SUP_DELIVERY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface SupDeliveryDetailService extends SqlService {

	public String queryDetail(Map<String,Object> entityMap)throws DataAccessException;
	public String queryDetailWithDelivery(Map<String,Object> entityMap)throws DataAccessException;
	public List<?> queryExistsById(Map<String,Object> entityMap)throws DataAccessException;
	public String queryDetailByStore(Map<String, Object> entityMap) throws DataAccessException;

}
