/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.termend.yearend;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 年度授权/直接支出预算下达数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public interface AccBudgTargetService {

	/**
	 * @Description 添加
	 * @param AccBudgTarget entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> addAccBudgTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 修改
	 * @param AccBudgTarget entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> updateAccBudgTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccBudgTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 精准查询
	 * @param  entityMap
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryAccBudgTargetByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 删除
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> deleteAccBudgTarget(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 消审/消审
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> auditAccBudgTarget(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 获取实际支出金额
	 * @param  Map
	 * @return Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> updateAccBudgTargetForMoney(Map<String, Object> entityMap)throws DataAccessException;
}
