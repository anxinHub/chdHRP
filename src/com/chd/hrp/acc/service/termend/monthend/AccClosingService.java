/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.service.termend.monthend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 结账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public interface AccClosingService {
	
	/**
	 * @Description 
	 * 结账<BR> 期间查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosing(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 校验是否可以开始结账
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosingCheckStart(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 未记账凭证查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosingCheckVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 核对总账与辅助账
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosingCheckLederToCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 核对总账与凭证
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosingCheckLederToVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 获取当前会计期间
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String getAccClosingYearMonth(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 获取科目的辅助核算名称
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String getAccClosingCheckNamesBySubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结账<BR> 结账
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String confirmAccClosing(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 结账<BR> 反结账
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String reconfirmAccClosing(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 结账<BR> 获取当前结账涉及到的凭证数量
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccClosingCountVouch(Map<String,Object> entityMap) throws DataAccessException;
}
