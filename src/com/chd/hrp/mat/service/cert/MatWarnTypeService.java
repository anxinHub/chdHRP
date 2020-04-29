package com.chd.hrp.mat.service.cert;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

public interface MatWarnTypeService {

	/**
	 * 主查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询（根据编码查询）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatWarnTypeByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @param request
	 * @param response
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> deleteMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatWarnTypeState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
}
