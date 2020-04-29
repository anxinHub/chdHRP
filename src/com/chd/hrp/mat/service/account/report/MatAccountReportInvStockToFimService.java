/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.account.report;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAccountReportInvStockToFimService {

	String collectMatAccountReportInvStockTofim(Map<String, Object> mapVo);
	
	List<Map<String,Object>> collectMatAccountReportInvStockTofimPrint(Map<String, Object> mapVo);

	String queryMatAccountReportInvStockToFimColumns(Map<String, Object> mapVo);

	String collectMatStoreInvStock(Map<String, Object> mapVo);

	List<Map<String, Object>> collectMatStoreInvStockPrint(
			Map<String, Object> mapVo); 
	
}
