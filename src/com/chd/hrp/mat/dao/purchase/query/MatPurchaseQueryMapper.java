/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.purchase.query;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPurchaseQueryMapper extends SqlMapper{
	
	//采购计划汇总查询(科室)
	public List<Map<String, Object>> queryMatPurInvReport(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatPurInvReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
