package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpDutyData;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiEmpDutyDataService {

	/**
	 * 添加
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String addEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 生成
	 * @param map
	 * @return
	 * @throws DataAccessException 
	 */
	public String initEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * @param map
	 * @return
	 * @throws DataAccessException 
	 */
	public String queryEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询(打印)
	 * @param map
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryEmpDutyDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询编码
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public AphiEmpDutyData queryEmpDutyDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteEmpDutyData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 更新
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String updateEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String importHpmEmpDutyData(Map<String, Object> map)throws DataAccessException;

}
