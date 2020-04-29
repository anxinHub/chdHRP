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
* 医保结算差额分摊<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccMedicareShareMapper extends SqlMapper{

	/**
	 * @Description 
	 * 医保结算差额分摊<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccMedicareShareVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 医保结算差额分摊<BR> 根据类别获取科目本月贷方发生额
	 * @param  entityMap 
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedSubjCreditMoneyListByType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 医保结算差额分摊<BR> 根据类别获取科目本月贷方发生总额
	 * @param  entityMap 
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Double queryMedSubjSumMoneyByType(Map<String,Object> entityMap)throws DataAccessException;
}
