package com.chd.hrp.hpm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptAvgBonusData;
import com.chd.hrp.hpm.entity.AphiItem;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiDeptAvgBonusDataService {

	/**
	 * 
	 */
	public String addDeptAvgBonusData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String initDeptAvgBonusData(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 
	 */
	public String queryDeptAvgBonusData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public AphiDeptAvgBonusData queryDeptAvgBonusDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteDeptAvgBonusData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteDeptAvgBonusDataById(String[] ids)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateDeptAvgBonusData(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 查询奖金核算的表头
	 */
	public String queryDeptAvgBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询奖金核算的表头
	 */
	public List<AphiItem> getGridTitleMap(Map<String, Object> entityMap);
}
