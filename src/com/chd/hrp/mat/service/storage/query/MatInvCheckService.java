/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;

import java.util.List;
import java.util.Map;

/**
 * 材料验收统计 
 * @author siran
 *
 */
public interface MatInvCheckService {
	/**
	 * 查询材料验收明细
	 * @param page
	 * @return
	 */
	String queryMatInvCheckDetail(Map<String, Object> page);
	/**
	 * 材料验收明细打印
	 * @param page
	 * @return
	 */
	List<Map<String,Object>> queryMatInvCheckDetailPrint(Map<String, Object> entityMap);
	/**
	 * 查询材料类别
	 * @param mapVo
	 * @return
	 */
	String queryMatType(Map<String, Object> mapVo);
	
	
}
