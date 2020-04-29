package com.chd.hrp.sys.dao.portal;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SysPortalNoticeMapper extends SqlMapper {
	
	/**
	 * 根据主键  查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 发布、取消发布 更改状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateSysNoticeState(Map<String, Object> mapVo) throws DataAccessException;
	
}
