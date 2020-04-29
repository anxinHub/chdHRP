package com.chd.hrp.hr.service.sysstruc;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface HrFiiedTabStrucService extends SqlService{

	String queryHrFiiedTabStrucTree(Map<String, Object> entityMap);

	String queryHrFiiedData(Map<String, Object> entityMap);

	String saveHrFiiedData(Map<String, Object> entityMap);

	String deleteHrFiiedData(List<Map> listVo) throws Exception;

	String queryColAndTabName(Map<String, Object> entityMap);
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
	List<Map<String,Object>> queryHrFiiedDataByPrint(Map<String, Object> entityMap) throws DataAccessException; 
}
