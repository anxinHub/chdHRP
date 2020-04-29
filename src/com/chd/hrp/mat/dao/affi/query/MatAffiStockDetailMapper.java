package com.chd.hrp.mat.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 代销-库存明细查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAffiStockDetailMapper extends SqlMapper{
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 库存明细查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiStorageQueryStockDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 库存明细查询
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAffiStorageQueryStockDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
