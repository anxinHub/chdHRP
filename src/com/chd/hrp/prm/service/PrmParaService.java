package com.chd.hrp.prm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmPara;

public interface PrmParaService {

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmParaByCode
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryPrmParaValue(Map<String, Object> entityMap, String para_code) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addPrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmPara分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryPrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmParaByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public PrmPara queryPrmParaByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              删除PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deletePrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量删除PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              更新PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updatePrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量更新PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              导入PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String importPrmPara(Map<String, Object> entityMap) throws DataAccessException;

}
