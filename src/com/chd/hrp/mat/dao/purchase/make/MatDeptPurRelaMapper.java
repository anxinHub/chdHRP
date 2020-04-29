/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.purchase.make;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRequireDetail;

/**
 * 
 * @Description:
 * 04205 采购仓库需求计划关系 
 * @Table:
 * MAT_REQUIRE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatDeptPurRelaMapper extends SqlMapper{

	/**
	 * @Description 
	 * 是否已生成<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsByStoreCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 是否已生成（批量判断）<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsByStoreList(List<Map<String,Object>> entityList)throws DataAccessException;
}
