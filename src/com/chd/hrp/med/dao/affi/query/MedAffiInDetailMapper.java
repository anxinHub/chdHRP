package com.chd.hrp.med.dao.affi.query;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 代销-入库明细查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAffiInDetailMapper extends SqlMapper {
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 入库明细查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiInDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 
	 * 入库明细查询 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAffiInDetail(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
