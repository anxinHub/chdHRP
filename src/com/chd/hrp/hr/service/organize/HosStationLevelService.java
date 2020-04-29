package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.entity.orangnize.HosStationLevel;

public interface HosStationLevelService {
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 */
	String addHrStationLevel(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据编码查询
	 * @param entityMap
	 * @return
	 */
	HosStationLevel queryHrStationLevelByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 */
	String updateHrStationLevel(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除
	 * @param listVo
	 * @return
	 */
	String deleteHrStationLevel(List<HosStationLevel> listVo) throws DataAccessException;
	/**
	 * 查询
	 * @param page
	 * @return
	 */
	String queryStationLevel(Map<String, Object> page) throws DataAccessException;
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询是否被使用
	 * @param listVo
	 * @return
	 */
	List<HosStationLevel> queryListLevel(List<HosStationLevel> listVo) throws DataAccessException;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
List<Map<String,Object>> queryStationLevelByPrint(Map<String, Object> page)throws DataAccessException;

}
