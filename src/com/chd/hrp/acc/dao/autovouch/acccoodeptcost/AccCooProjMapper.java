/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 基本数字表<BR>
* @Author: XuXin
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCooProjMapper extends SqlMapper{
	
	public void addAccCooProj(Map<String, Object> mapVo) throws DataAccessException;
	
	public void updateAccCooProj(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String, Object> queryAccProjByCode(Map<String, Object> mapVo) throws DataAccessException;

	public int deleteAccCooProj(List<Map<String, Object>> list)throws DataAccessException;

	public List<Map<String, Object>> queryAccCooProj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccCooProj(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccProjUse(@Param(value="list") List<Map<String, Object>> list, @Param(value="map") Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccProjByList(@Param(value="list") List<Map<String, Object>> list, @Param(value="map") Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptAllInfoDict(Map<String, Object> mapVo);
	
	public int insertAccProjMainByImport(List<Map<String, Object>> list)throws DataAccessException;
	
}
