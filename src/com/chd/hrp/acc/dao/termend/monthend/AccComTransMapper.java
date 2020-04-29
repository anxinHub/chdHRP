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
* 通用结转<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccComTransMapper extends SqlMapper{

	/**
	 * @Description 
	 * 通用结转<BR> 添加AccVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAccComTransVouch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 通用结转<BR> 获取科目本期发生
	 * @param entityMap
	 * @return list
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryComTransSubjMoneyList(Map<String, Object> entityMap) throws DataAccessException;
}
