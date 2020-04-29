package com.chd.hrp.med.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 证件材料查询
 * @Table:
 * MED_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedInvCertQueryMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInvCertQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedInvCertQuery(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
