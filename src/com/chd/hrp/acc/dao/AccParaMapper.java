/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccPara;

/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccParaMapper extends SqlMapper{
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询AccPara所有数据
	 * @param  entityMap
	 * @return List<AccPara>
	 * @throws DataAccessException
	*/
	public List<AccPara> queryAccPara(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新AccPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccPara(@Param("map") Map<String,Object> map,@Param("list") List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccParaInit(Map<String,Object> map) throws DataAccessException;
	
}
