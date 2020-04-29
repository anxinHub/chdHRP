
/*
 *
 */
 package com.chd.hrp.eqc.dao.query;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 20设备使用记录 ASS_EQUseRecord DB管理
* Table("ASS_EQUseRecord")
*/
public interface AssEqUseRecordQueryMapper extends SqlMapper{
	/**
	 * 使用记录查询-服务项目  查询  不分页
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEqUseMain(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 使用记录查询-服务项目  查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEqUseMain(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 使用记录查询-服务细项 查询 不分页
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEqUseDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 使用记录查询-服务细项 查询 分页
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEqUseDetail(Map<String, Object> mapVo ,	RowBounds rowBounds) throws DataAccessException;
	
}
