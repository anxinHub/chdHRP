package com.chd.hrp.mat.service.info.basic;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 证件材料查询
 * @Table:
 * MAT_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatInvCertQueryService {
	
	/**
	 * @Description 
	 * 证件材料查询-查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvCertQuery(Map<String,Object> entityMap) throws DataAccessException;
	
}
