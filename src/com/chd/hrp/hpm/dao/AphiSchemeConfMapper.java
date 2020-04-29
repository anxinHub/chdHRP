package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiSchemeConf;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiSchemeConfMapper extends SqlMapper {
	
	/**
	 * 查询方案应用详细信息
	 */
	public List<AphiSchemeConf> querySchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询方案应用详细信息
	 */
	public List<AphiSchemeConf> querySchemeConf(Map<String, Object> entityMap, RowBounds rowbounds) throws DataAccessException;

	/**
	 * 添加方案应用信息
	 */
	public int addSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 修改方案应用信息
	 */
	public int updateSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除方案应用信息
	 */
	public int deleteSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据方案序号查询方案信息
	 */
	public AphiSchemeConf querySchemeConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据年月查询方案序号
	 */
	public AphiSchemeConf querySchemeConfByYM(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询方案应用详细信息
	 */
	public List<Map<String,Object>> queryHpmSchemeConfForDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询方案应用详细信息
	 */
	public List<Map<String,Object>> queryHpmSchemeConfForDept(Map<String, Object> entityMap, RowBounds rowbounds) throws DataAccessException;
	
	

}
