/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.dao;

import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

/**
 * @Title. @Description. 建账套后初始化内置数据<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface InitProcMapper extends SqlMapper {

	/**
	 * 当建账套的时候初始化内置数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveInitCopyProc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 当建账套的时候初始化内置数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveInitHosProc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 当建账套的时候初始化内置数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveInitSysProc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 当建会计年度的时候初始化内置数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveInitYearProc(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 当建集团的时候初始化内置数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveInitGroupProc(Map<String, Object> entityMap) throws DataAccessException;

}
