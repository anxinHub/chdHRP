/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.service;

import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.InfoDict;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface InfoDictService {

	/**
	 * @Description 添加InfoDict
	 * @param InfoDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加InfoDict
	 * @param InfoDict
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addBatchInfoDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询InfoDict分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询InfoDictByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public InfoDict queryInfoDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 删除InfoDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除InfoDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String deleteBatchInfoDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新InfoDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新InfoDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateBatchInfoDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 导入InfoDict
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String importInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询InfoDictByMenu
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryInfoDictByMenu(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 通过医院id 获取医院信息以及对应的集团信息
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryHosInfoToGroupInfo(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosInfoList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHosInfoListPrint(Map<String, Object> entityMap) throws DataAccessException;

}
