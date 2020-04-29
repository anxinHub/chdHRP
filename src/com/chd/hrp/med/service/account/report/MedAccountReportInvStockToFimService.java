/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.account.report;

import java.util.Map;

/**
 * 
 * @Description:
 * 药品库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountReportInvStockToFimService {

	String collectMedAccountReportInvStockTofim(Map<String, Object> mapVo);

	String queryMedAccountReportInvStockToFimColumns(Map<String, Object> mapVo); 
	
}
