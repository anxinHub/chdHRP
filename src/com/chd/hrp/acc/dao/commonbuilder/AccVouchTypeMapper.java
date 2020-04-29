/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccVouchType;

/**
* @Title. @Description.
* 凭证类型<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 凭证类型<BR> 添加AccVouchType
	 * @param AccVouchType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量添加AccVouchType
	 * @param  AccVouchType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchType分页
	 * @param  entityMap RowBounds
	 * @return List<AccVouchType>
	 * @throws DataAccessException
	*/
	public List<AccVouchType> queryAccVouchType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchType所有数据
	 * @param  entityMap
	 * @return List<AccVouchType>
	 * @throws DataAccessException
	*/
	public List<AccVouchType> queryAccVouchType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccVouchType queryAccVouchTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 删除AccVouchType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量删除AccVouchType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 凭证类型<BR> 更新AccVouchType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量更新AccVouchType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccVouchType> queryAccVouchTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	
	//校验是否被引用 
	public int existsVouchByType(Map<String, Object> entityMap)throws DataAccessException;

	public int existsByTypeName(Map<String, Object> entityMap);

	public int existsByTypeCode(Map<String, Object> entityMap);
}
