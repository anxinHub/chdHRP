/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.sell.out.source;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产有偿调拨出库单资金来源(其他固定资产)
 * @Table:
 * ASS_SELL_OUT_SOURCE_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssSellOutSourceOtherMapper extends SqlMapper{
	List<Map<String, Object>> queryBySellOutNoAndAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
}
