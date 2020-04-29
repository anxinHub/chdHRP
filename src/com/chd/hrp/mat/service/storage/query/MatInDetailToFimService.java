/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;
import java.util.List;
import java.util.Map;

 
public interface MatInDetailToFimService {
	/**
	 * 供应商采购汇总查询to财务
	 * @param mapVo
	 * @return
	 */
	String queryMatInSupCount(Map<String, Object> mapVo);  
	
	/**
	 * 供应商采购汇总财务分类打印
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryMatInSupCountPrint(Map<String, Object> mapVo);

	String queryOccurMatFimTypeDict(Map<String, Object> mapVo); 
}
