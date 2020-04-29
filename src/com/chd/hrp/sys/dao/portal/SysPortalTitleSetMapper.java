package com.chd.hrp.sys.dao.portal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.SysPortalTitleSet;

public interface SysPortalTitleSetMapper extends SqlMapper {
	
	/**
	 * 保存  门户栏目配置数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addSysPortalTitleSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 批量保存  门户栏目配置数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchSysPortalTitleSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
