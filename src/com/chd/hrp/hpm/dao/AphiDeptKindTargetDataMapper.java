package com.chd.hrp.hpm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptKindTargetData;
import com.chd.hrp.hpm.entity.AphiHospTargetData;
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

public interface AphiDeptKindTargetDataMapper  extends SqlMapper{
	
	/**
	 * 
	 */
	public int addDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchDeptKindTargetData(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptKindTargetData> queryDeptKindTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptKindTargetData> queryDeptKindTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public AphiDeptKindTargetData queryDeptKindTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int shenhe(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * add by alfred
	 */
	public List<AphiDeptKindTargetData> getDeptKindTargetValue(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * add by alfred
	 */
	public List<AphiDeptKindTargetData> getDeptKindTargetValueByTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptKindTargetData> queryTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptKindTargetData> queryDeptKind(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryDeptKindTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;
}
