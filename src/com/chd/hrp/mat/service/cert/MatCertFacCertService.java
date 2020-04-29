package com.chd.hrp.mat.service.cert;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

public interface MatCertFacCertService {
	
	/**
	 * 主查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatFacCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @param request
	 * @param response
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveMatFacCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) throws DataAccessException;
	/**
	 * 删除
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> deleteMatFacCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询证件信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatFacCertInfo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询材料信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatFacCertInv(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改证件状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatCertFacState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询证件类型信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCertType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询数据回冲更新页面
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatFacCertById(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新
	 * @param mapVo
	 * @param request
	 * @param response
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatFacCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) throws DataAccessException;
	
}
