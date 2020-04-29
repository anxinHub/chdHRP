package com.chd.hrp.hpm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptEmpData;

/**
 * 科室职工数据准备
 * @author Administrator
 *
 */
public interface AphiDeptEmpDataService {

	/**
	 * 查询 
	 * @param mapVo
	 * @return
	 */
	public String queryHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询(打印) 
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryHpmDeptEmpDataPrint(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 修改
	 * @param mapVo
	 * @return
	 */
	public String updateHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 删除
	 * @param checkIds 
	 * @param mapVo
	 * @return
	 */
	public String deleteHpmDeptEmpData(Map<String, Object> entityMap, String checkIds)throws DataAccessException;

	/**
	 * 生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String initHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 *  计算
	 * @param mapVo
	 * @return
	 */
	public String collectHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 查询编码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AphiDeptEmpData queryDeptEmpDataByCode(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String hpmDeptEmpDataImport(Map<String, Object> entityMap)throws DataAccessException;
	
	

}
