/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.monthend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTermendTemplateDept;

/**
* @Title. @Description.
* 医疗风险基金提取<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccFundExtractMapper extends SqlMapper{
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 添加凭证模板
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccFundExtractVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 获取收入科目的总金额
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Double querySumIncomSubjMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 医疗风险基金提取<BR> 科室比例查询
	 * @param  entityMap
	 * @return List<AccVouch>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplateDept> queryAccFundExtractDept(Map<String,Object> entityMap) throws DataAccessException;
}
