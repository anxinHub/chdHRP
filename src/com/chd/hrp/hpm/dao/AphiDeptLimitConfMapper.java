package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptLimitConf;

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

public interface AphiDeptLimitConfMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchDeptLimitConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptLimitConf> queryDeptLimitConf(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptLimitConf> queryDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptLimitConf queryDeptLimitConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 清空数据库数据
	 */
	public int clearDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException;
}
