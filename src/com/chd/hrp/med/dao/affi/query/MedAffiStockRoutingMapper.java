package com.chd.hrp.med.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 代销-库存分布查询 
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAffiStockRoutingMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 代销-库存分布查询 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiStockRouting(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 
	 * 代销-库存分布查询  分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiStockRouting(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
