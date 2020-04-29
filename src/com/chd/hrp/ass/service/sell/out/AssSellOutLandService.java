/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.sell.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表(土地)
 * @Table:
 * ASS_SELL_OUT_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutLandService extends SqlService {
	public String updateSellOutConfirm(List<Map<String, Object>> entityMap,List<Map<String, Object>> cardEntityMap)throws DataAccessException;

	public String queryBySellInImport(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryBySellInImportView(Map<String, Object> entityMap)throws DataAccessException;

	Map<String, Object> printAssSellOutLandData(Map<String, Object> map) throws DataAccessException;

	public List<String> queryAssSellOutLandStates(Map<String, Object> mapVo);

	public String queryDetails(Map<String, Object> page);
}
