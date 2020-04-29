package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiWorkitemConf;

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

public interface AphiWorkitemConfMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchWorkitemConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemConf> queryWorkitemConf(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemConf> queryWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitemConf queryWorkitemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int clearWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiWorkitemConf> queryWorkitemConfAll(Map<String, Object> entityMap) throws DataAccessException;
}
