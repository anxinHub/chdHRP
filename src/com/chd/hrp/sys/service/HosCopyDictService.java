/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Copy;

/**
 * 单位账套维护
 * @author gaopei
 *
 */

public interface HosCopyDictService {

 
	public String addSysHosCopyDict(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String querySysHosCopyDict(Map<String,Object> entityMap) throws DataAccessException;
	
    public String deleteSysHosCopyDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	 
	public String updateSysHosCopyDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String,Object> queryHosCopyDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String querySysHosCopyRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addBatchSysHosCopyRela(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String deleteBatchSysHosCopyRela(List<Map<String, Object>> entityList)throws DataAccessException;
	
	 
}
