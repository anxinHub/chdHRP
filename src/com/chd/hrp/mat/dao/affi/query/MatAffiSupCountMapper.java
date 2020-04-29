package com.chd.hrp.mat.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 代销-供应商采购汇总查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAffiSupCountMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 供应商采购汇总查询
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiSupCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 供应商采购汇总查询  分页查询 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiSupCount(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
