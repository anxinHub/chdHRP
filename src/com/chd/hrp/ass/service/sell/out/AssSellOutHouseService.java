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
 * 050901 资产有偿调拨出库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_OUT_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutHouseService extends SqlService {

	String updateSellOutConfirm(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String queryBySellInImport(Map<String, Object> page);

	String queryBySellInImportView(Map<String, Object> page);

	Map<String, Object> printAssSellOutHouseData(Map<String, Object> map) throws DataAccessException;

	List<String> queryAssSellOutStates(Map<String, Object> mapVo);

	String queryDetails(Map<String, Object> page);

}
