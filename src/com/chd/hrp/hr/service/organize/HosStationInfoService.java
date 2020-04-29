package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface HosStationInfoService {

	/**
	 * 添加
	 * @param entityMap
	 * @return
	 */
	String addStationInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据编码查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HosStation queryByCode(Map<String, Object> entityMap)  throws DataAccessException;
	/**
	 * 修改
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateStationInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchStationInfo(List<HosStation> entityList) throws DataAccessException;
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryStationInfo(Map<String, Object> entityMap)  throws DataAccessException;
	/**
	 * 岗位树
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryStationInfoTree(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryStationInfoPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 部门信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryDeptInfo(Map<String, Object> entityMap) throws DataAccessException;

}
