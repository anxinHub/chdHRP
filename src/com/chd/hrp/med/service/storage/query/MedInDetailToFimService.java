/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.query;
import java.util.Map;

 
public interface MedInDetailToFimService {
	/**
	 * 供应商采购汇总查询to财务
	 * @param mapVo
	 * @return
	 */
	String queryMedInSupCount(Map<String, Object> mapVo); 
	
	
}
