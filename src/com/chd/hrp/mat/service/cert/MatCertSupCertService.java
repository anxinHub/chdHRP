package com.chd.hrp.mat.service.cert;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

public interface MatCertSupCertService {
	
	/**
	 * 主查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSupCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @param request
	 * @param response
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveMatSupCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) throws DataAccessException;
	/**
	 * 删除
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> deleteMatSupCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询证件信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSupCertInfo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询材料信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSupCertInv(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改证件状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatCertSupState(Map<String, Object> mapVo) throws DataAccessException;
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
	public Map<String, Object> queryMatSupCertById(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新
	 * @param mapVo
	 * @param request
	 * @param response
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateMatSupCert(Map<String, Object> mapVo,
			HttpServletRequest request, HttpServletResponse response) throws DataAccessException;
	
}
