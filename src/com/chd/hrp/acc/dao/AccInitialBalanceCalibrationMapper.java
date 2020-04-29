/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccInitialBalanceCalibration;

/**
* @Title. @Description.
* 初始余额查询<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccInitialBalanceCalibrationMapper extends SqlMapper{
	
	
	
	/**
	 * @Description 
	 * 初始余额查询<BR> 
	 * @param  entityMap
	 * @return List<InitialBalanceCalibration>
	 * @throws DataAccessException
	*/
	public List<AccInitialBalanceCalibration> collectInitialBalanceCalibration(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 初始余额查询-打印<BR> 
	 * @param  entityMap
	 * @return List<InitialBalanceCalibration>
	 * @throws DataAccessException
	 */
	public List<AccInitialBalanceCalibration> collectInitialBalanceCalibrationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 余额调节表查询<BR> 
	 * @param  entityMap
	 * @return List<InitialBalanceCalibration>
	 * @throws DataAccessException
	*/
	public List<AccInitialBalanceCalibration> collectBalanceAdjust(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> collectBalanceAdjustByPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
