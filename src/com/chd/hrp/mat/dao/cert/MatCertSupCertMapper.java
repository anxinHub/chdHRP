package com.chd.hrp.mat.dao.cert;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatCertSupCertMapper extends SqlMapper {
	/**
	 * 主查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 主查询 （带分页）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCert(Map<String, Object> mapVo,
			RowBounds rowBounds) throws DataAccessException;
	/**
	 * 证件查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCertInfo(
			Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 材料查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCertInv(
			Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 材料查询（带分页）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCertInv(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 新增
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void saveMatSupCert(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除供应商证件附件
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void deleteMatCertSupFile(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除供应商证件
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void deleteMatCertSup(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新证件状态
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateMatCertSupState(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询证件类型信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCertType(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据编码查询证件信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatSupCertByCode(Map<String, Object> mapVo) throws DataAccessException;
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
	 * @throws DataAccessException
	 */
	public void updateMatSupCert(Map<String, Object> mapVo) throws DataAccessException;

}
