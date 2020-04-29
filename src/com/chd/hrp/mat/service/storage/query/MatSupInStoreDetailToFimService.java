/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

 
public interface MatSupInStoreDetailToFimService {
	/**
	 * 供应商采购汇总查询to财务 
	 * @param mapVo
	 * @return
	 * @throws ParseException 
	 * @throws DataAccessException 
	 */

	String queryMatSupInStoreDetailToFim(Map<String, Object> mapVo) throws DataAccessException, ParseException; 
	/**
	 * 供应商采购汇总查询to财务
	 * @param mapVo
	 * @return
	 */
	
	List<Map<String,Object>> queryMatSupInStoreDetailToFimPrint(Map<String, Object> mapVo);
	/**
	 * 查询发生过入库业务的 财务分类的编码和名称
	 * @param mapVo
	 * @return
	 * @throws ParseException 
	 */
	String queryOccurMatFimTypeDictForHead(Map<String, Object> mapVo) throws ParseException; 
	
	
}
