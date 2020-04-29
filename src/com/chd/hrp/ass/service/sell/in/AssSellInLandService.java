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
 * 050901 资产有偿调拨入库单主表(土地)
 * @Table:
 * ASS_SELL_IN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellInLandService extends SqlService {
	public String initAssSellInCardLand(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateReAudit(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String initAssSellInLand(Map<String,Object> entityMap)throws DataAccessException;

	Map<String, Object> printAssSellInLandData(Map<String, Object> map) throws DataAccessException;

	public String initAssSellInBatchCardLand(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo) throws DataAccessException;

	public List<String> queryAssSellInLandStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page);
	
}
