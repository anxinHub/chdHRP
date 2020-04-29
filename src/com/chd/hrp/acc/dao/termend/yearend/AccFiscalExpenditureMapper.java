/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.yearend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 财政基本补助支出结转<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccFiscalExpenditureMapper extends SqlMapper{

	/**
	 * @Description 
	 * 财政基本补助支出结转<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccFiscalExpenditureVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财政基本补助支出结转<BR> 获取财政收入科目本年发生总金额
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double querySumCreditMoney(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 财政基本补助支出结转<BR> 获取财政支出科目本年发生总金额
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double querySumDebitMoney(Map<String,Object> entityMap)throws DataAccessException;
	
}
