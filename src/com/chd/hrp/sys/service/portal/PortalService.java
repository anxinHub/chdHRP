package com.chd.hrp.sys.service.portal;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PortalService {
	
	/**
	 * 物流模块 栏目信息查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySysPortalInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 用户门户栏目显示 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String querySysPortalTitle(Map<String, Object> mapVo) throws DataAccessException;
	
	
	public String querySysShowPortalInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	public String querySysShowPortalData(Map<String, Object> mapVo);
	String queryOrgChart(Map<String, Object> map);

}
