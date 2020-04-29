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
/**
 * 
 * @Description:
 * 入库明细查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatInSupBillSumService{
	
	
	/**
	 * 供应商入库汇总查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInSupBillSum(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 供应商入库打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInSupBillSumPrint(Map<String, Object> entityMap) throws DataAccessException;
	
}
