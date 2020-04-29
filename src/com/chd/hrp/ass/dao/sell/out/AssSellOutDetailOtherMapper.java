/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.sell.out;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailOther;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单明细(其他固定资产)
 * @Table:
 * ASS_SELL_OUT_DETAIL_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutDetailOtherMapper extends SqlMapper{
	public List<AssSellOutDetailOther> queryBySellOutNo(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssSellOutDetailOther> queryByImportSellIn(Map<String,Object> entityMap)throws DataAccessException;
}
