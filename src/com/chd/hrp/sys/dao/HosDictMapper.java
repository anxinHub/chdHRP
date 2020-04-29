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
import com.chd.hrp.sys.entity.HosDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosDictMapper extends SqlMapper{
	
	
	
	/**
	 * @Description 查询StoreDict所有数据
	 * @param  entityMap
	 * @return List<StoreDict>
	 * @throws DataAccessException
	*/
	public List<HosDict> queryHosDict(Map<String,Object> entityMap) throws DataAccessException;
	
   /** 
	* 用于集团化管理   集团单位选择器--集团单位字典 查询
	* @Description 查询GroupStoreDict所有数据
	* @param  entityMap
	* @return List<StoreDict>
	* @throws DataAccessException
	*/
	public List<HosDict> queryGroupHosDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
