package com.chd.hrp.mat.dao.cert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatWarnTypeMapper extends SqlMapper {
	
	/**
	 * 主查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 主查询（分页）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatWarnType(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据编码查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatWarnTypeByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void saveMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void deleteMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新状态
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateMatWarnTypeState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
}
