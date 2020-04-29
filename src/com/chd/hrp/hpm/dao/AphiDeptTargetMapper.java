package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptTarget;

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

public interface AphiDeptTargetMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchDeptTarget(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTarget> queryDeptTarget(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTarget> queryDeptTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptTarget queryDeptTargetByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptTarget(Map<String, Object> entityMap) throws DataAccessException;
}
