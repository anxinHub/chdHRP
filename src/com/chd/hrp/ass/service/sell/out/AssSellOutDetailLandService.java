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
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailLand;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单明细(土地)
 * @Table:
 * ASS_SELL_OUT_DETAIL_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutDetailLandService extends SqlService {
	public List<AssSellOutDetailLand> queryByAssSellOutNo(Map<String,Object> entityMap)throws DataAccessException;
}
