package com.chd.hrp.sys.service.portal;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface SysPortalTitleSetService {
	/**
	 *  保存  门户栏目配置数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addSysPortalTitleSet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量添加 门户栏目配置数据
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchSysPortalTitleSet(List<Map<String,Object>> list) throws DataAccessException;
	/**
	 * 删除 门户栏目配置数据
	 * @param deleteMap
	 * @throws DataAccessException
	 */
	public String  deleteSysPortalTitleSet(Map<String, Object> deleteMap) throws DataAccessException;

}
