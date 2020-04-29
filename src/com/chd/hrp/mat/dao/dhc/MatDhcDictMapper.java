/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.dhc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDhcDictMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询数据<BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDhcDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加数据<BR>
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStore(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDept(List<Map<String,Object>> entityMap)throws DataAccessException;
}
