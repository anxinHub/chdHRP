/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.sell.in;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_IN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellInHouseService extends SqlService {
	public String initAssSellInCardHouse(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String initAssSellInHouse(Map<String,Object> entityMap)throws DataAccessException;

	Map<String, Object> printAssSellInHouseData(Map<String, Object> map) throws DataAccessException;

	public String initAssSellInBatchCardHouse(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo) throws DataAccessException;

	public List<String> queryAssSellInHouseStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page);
}
