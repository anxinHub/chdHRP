/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.purchase.query;
import java.util.*;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * MAT_REQUIRE_MAIN
 * @Table:
 * MAT_REQUIRE_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPurchaseQueryService{
	
	//采购计划汇总查询(材料)
	public String queryMatPurInvReport(Map<String, Object> entityMap) throws DataAccessException;
	
	//打印采购计划汇总(材料)
	public List<Map<String, Object>> printMatPurInvReport(Map<String, Object> entityMap) throws DataAccessException;
}
