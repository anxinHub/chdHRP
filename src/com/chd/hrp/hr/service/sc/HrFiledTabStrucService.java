package com.chd.hrp.hr.service.sc;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;

import com.chd.base.SqlService;
import com.chd.base.util.Plupload;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;

public interface HrFiledTabStrucService extends SqlService{

	String queryHrFiledTabStrucTree(Map<String, Object> entityMap);

	String queryHrFiledData(Map<String, Object> entityMap);

	String saveHrFiledData(Map<String, Object> entityMap);

	String deleteHrFiledData(List<Map> listVo) throws Exception;

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
	List<Map<String,Object>> queryHrFiledDataByPrint(Map<String, Object> entityMap) throws DataAccessException;

	String queryFiledTypeSelect(Map<String, Object> mapVo) throws DataAccessException;

	String updateHrfiledView(Map<String, Object> entityMap)throws DataAccessException;

	HrFiledTabStruc queryHrFiledTabStruc(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 导入旧版本数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String copyHrTableStrucByOld(Map<String, Object>  entityMap) throws DataAccessException;

	String updateFiledTabStrucSQL(Map<String, Object> entityMap) throws DataAccessException;

	String queryFiledColsByCode(Map<String, Object>  entityMap) throws DataAccessException;

	String queryDBTableTree(Map<String, Object> entityMap) throws DataAccessException;
	
	String queryHrFiledTabStrucExport(Map<String, Object> entityMap) throws DataAccessException;

	void readJsonFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode,
			Map<String, Object> entityMap)throws DataAccessException, IOException;

}
