/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.matpayquery;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 04105 物资材料表
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatPayQueryService {
	
	/**
	 * 入库发票查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInBillReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入库发票打印查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> printMatInBillReport(Map<String, Object> entityMap) throws DataAccessException;
}
