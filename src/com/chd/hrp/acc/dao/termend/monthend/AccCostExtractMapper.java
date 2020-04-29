/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.monthend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 支出费用提取<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCostExtractMapper extends SqlMapper{

	/**
	 * @Description 
	 * 支出费用提取<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccCostExtractVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 支出费用提取<BR> 获取支出科目的总金额(根据subj_code_type=5001/5301决定是医疗支出还是管理支出)
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double querySumCostSubjMoneyByType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 支出费用提取<BR> 获取支出科目部门金额(根据subj_code_type=5001/5301决定是医疗支出还是管理支出)
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptCostMoneyByType(Map<String,Object> entityMap)throws DataAccessException;
}
