package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmpScheme;

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

public interface AphiEmpSchemeMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpScheme> queryEmpScheme(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpScheme> queryEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 * */

	public List<AphiEmpScheme> queryEmpSchemeByDuty(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpScheme queryEmpSchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteEmpScheme(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteBatchEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
     * 
     */
	public int deleteEmpSchemeById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int fastEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpScheme> queryEmpSchemeFast(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印-查询
	 */
	public List<Map<String, Object>> queryEmpSchemePrint(Map<String, Object> entityMap) throws DataAccessException;

}
