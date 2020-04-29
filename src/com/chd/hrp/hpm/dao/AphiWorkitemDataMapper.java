package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.entity.AphiWorkitemData;

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

public interface AphiWorkitemDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchWorkitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemData> queryWorkitemData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiWorkitemData> queryWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<Map<String, Object>> queryWorkitemDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemConf> getWorkitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitemData queryWorkitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateWorkitemData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiWorkitemData> queryWorkitemDataAll(Map<String, Object> entityMap) throws DataAccessException;
}
