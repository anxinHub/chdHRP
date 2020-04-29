/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.dao;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 成本_分摊类别
 * @Table:
 * COST_PARA_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface CostParaNaturMapper extends SqlMapper{

	List<Map<String, Object>> queryCostParaNaturPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	
}
