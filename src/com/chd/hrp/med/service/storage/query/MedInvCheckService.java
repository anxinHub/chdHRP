/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.query;

import java.util.Map;

/**
 * 药品验收统计 
 * @author siran
 *
 */
public interface MedInvCheckService {
	/**
	 * 查询药品验收明细
	 * @param page
	 * @return
	 */
	String queryMedInvCheckDetail(Map<String, Object> page);
	/**
	 * 查询药品类别
	 * @param mapVo
	 * @return
	 */
	String queryMedType(Map<String, Object> mapVo);
	
	
}
