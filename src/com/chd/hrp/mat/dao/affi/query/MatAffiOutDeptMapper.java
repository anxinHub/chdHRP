package com.chd.hrp.mat.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 代销-科室领用明细
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAffiOutDeptMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 科室领用明细 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 科室领用明细 分页查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiOutDept(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
}
