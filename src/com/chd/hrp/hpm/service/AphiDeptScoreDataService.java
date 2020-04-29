package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptScoreData;

/**
 * 科室绩效数据准备
 * @author Administrator
 *
 */
public interface AphiDeptScoreDataService {

	/**
	 * 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * 查询(打印)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHpmDeptScoreDataPrint(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 添加保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 修改保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 删除
	 * @param checkIds 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteHpmDeptScoreData(Map<String, Object> entityMap, String checkIds)throws DataAccessException;

	
	/**
	 * 查询编码
	 * @return
	 * @throws DataAccessException
	 */
	public AphiDeptScoreData queryDeptScoreDataByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String importHpmDeptScoreData(Map<String, Object> map)throws DataAccessException;
	
	
	
	
	

}
