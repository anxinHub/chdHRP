package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptDutyData;

/**
 * 科室岗位系数数据准备
 * @author Administrator
 *
 */
public interface AphiDeptDutyDataService {

	/**
	 * 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHpmDeptDutyData(Map<String, Object> entityMap)throws DataAccessException ;
	
	/**
	 * 查询(打印)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHpmDeptDutyDataPrint(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHpmDeptDutyData(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 获取编码
	 * @param mapVo
	 * @return
	 */
	AphiDeptDutyData queryDeptDutyDataByCode(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 修改
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHpmDeptDutyData(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 删除
	 * @param checkIds 
	 * @param mapVo
	 * @return
	 */
	String deleteHpmDeptDutyData(Map<String, Object> entityMap, String checkIds)throws DataAccessException ;

	/**
	 * 生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String initHpmDeptDutyData(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 计算
	 * @param mapVo
	 * @return
	 */
	String collectHpmDeptDutyData(Map<String, Object> entityMap)throws DataAccessException ;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	String hpmDeptDutyDataImport(Map<String, Object> map)throws DataAccessException ;
	
	
	
	
}
