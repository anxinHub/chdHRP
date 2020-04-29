package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptNature;

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

public interface AphiDeptNatureMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptNature(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptNature> queryDeptNature(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptNature> queryDeptNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptNature queryDeptNatureByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptNature(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptNature(Map<String, Object> entityMap) throws DataAccessException;
}
