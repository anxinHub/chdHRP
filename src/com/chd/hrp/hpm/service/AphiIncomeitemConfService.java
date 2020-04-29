package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeitemConf;

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

public interface AphiIncomeitemConfService {

	/**
	 * 
	 */
	public String addIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitemConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemConf queryIncomeitemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteIncomeitemConf(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String copyIncomeitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String hpmIncomeitemConfImport(Map<String, Object> entityMap)throws DataAccessException;
}
