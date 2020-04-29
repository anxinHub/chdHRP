/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.requrie.reqsearch;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.mat.entity.MatRequireMain;
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
 

public interface MatRequireReqSearchService{
	
	//科室需求计划汇总查询(科室)
	public String queryMatDeptStatQToDept(Map<String, Object> entityMap) throws DataAccessException;
	
	//打印科室需求计划汇总(科室)
	public List<Map<String, Object>> printMatDeptStatQToDept(Map<String, Object> entityMap) throws DataAccessException;
}
