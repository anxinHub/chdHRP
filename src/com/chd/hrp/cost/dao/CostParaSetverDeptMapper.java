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
 * 成本_分摊受益科室
 * @Table:
 * COST_PARA_SETVER_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface CostParaSetverDeptMapper extends SqlMapper{
	/**
	 * @Description 
	 *  自动生成 批量添加
	 * @param  CostPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchByGenerate(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostParaSetverDeptPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
