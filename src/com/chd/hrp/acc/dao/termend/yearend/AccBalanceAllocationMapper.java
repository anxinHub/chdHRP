/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend.yearend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 结余分配<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccBalanceAllocationMapper extends SqlMapper{

	/**
	 * @Description 
	 * 结余分配<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAccBalanceAllocationVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 结余分配<BR> 根据科目获取年末余额
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double queryEndMoneyBySubj(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 结余分配<BR> 获取科目本年未记账借方发生额
	 * @param  entityMap 
	 * @return Double
	 * @throws DataAccessException
	*/
	public Double queryVouchDebitMoneyBySubj(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 结余分配<BR> 根据科目和余额方向获取辅助核算余额（返回自动凭证格式）
	 * @param entityMap
	 * @return list
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCheckEndMoneyBySubjDire(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结余分配<BR> 获取科目期末余额
	 * @param entityMap
	 * @return list
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySubjEndMoneyList(Map<String, Object> entityMap) throws DataAccessException;
}
