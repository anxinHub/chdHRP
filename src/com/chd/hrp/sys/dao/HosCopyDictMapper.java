/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Copy;

/**
 * 单位账套维护
 * @author gaopei
 *
 */

public interface HosCopyDictMapper extends SqlMapper{
	 
	public int addSysHosCopyDict(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int addBatchSysHosCopyDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	 
	public List<?> querySysHosCopyDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
 
	public List<?> querySysHosCopyDict(Map<String,Object> entityMap) throws DataAccessException;
	 
    public <T> T queryHosCopyDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int deleteBatchSysHosCopyDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int deleteSysHosCopyDict(Map<String, Object>  entityMap)throws DataAccessException;
    
	public int updateSysHosCopyDict(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int updateBatchSysHosCopyDict(List<Map<String, Object>> entityMap)throws DataAccessException;

	//对应关系操作设置
	public List<?> querySysHosCopyRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<?> querySysHosCopyRela(Map<String,Object> entityMap) throws DataAccessException;
	//对应关系保存
	public int addBatchSysHosCopyRela(List<Map<String, Object>> entityList)throws DataAccessException;

	public int deleteBatchSysHosCopyRela(List<Map<String, Object>> entityList)throws DataAccessException;
 
}
