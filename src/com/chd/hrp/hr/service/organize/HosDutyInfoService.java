package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosDuty;

public interface HosDutyInfoService {
	/**
	 * 增加
	 * @param entityMap
	 * @return
	 */
	String addHrDuty(Map<String, Object> entityMap);
	/**
	 * 根据编码查信息
	 * @param entityMap
	 * @return
	 */
	HosDuty queryHrDutyByCode(Map<String, Object> entityMap);
	/**
	 * 更新职务信息表
	 * @param entityMap
	 * @return
	 */
	String updateHrDuty(Map<String, Object> entityMap);
	/**
	 * 批量删除
	 * @param entityList
	 * @return
	 */
	String deleteBatchHrDuty(List<HosDuty> entityList);
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */
	String queryHrDuty(Map<String, Object> entityMap);
	
	String queryHrDutyTree(Map<String, Object> entityMap);
	
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 */
	String importDate(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrDutyPrint(Map<String, Object> entityMap) throws DataAccessException;

}
