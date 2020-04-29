package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmp;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiEmpMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addAphiEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int initBatchAphiEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmp> queryAphiEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 * */

	public List<AphiEmp> queryAphiEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * */

	public List<AphiEmp> queryAphiEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * */
	
	public List<Map<String,Object>> querySysEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * */
	
	public List<Map<String,Object>> querySysEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 * */

	public List<AphiEmp> queryAphiEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public AphiEmp queryAphiEmpByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public AphiEmp queryAphiEmpByCodeEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 更新8801 职工字典表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAphiEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除职工
	 * 
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchAphiEmp(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAphiEmp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询EmpByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchAphiEmp(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 查询 序列
	 * 
	 * @param
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryAphiEmpSeqNextVal() throws DataAccessException;

	/**
	 * 查询 全部职工
	 * 
	 * @param entityMap
	 * @return List<AphiEmp>
	 * @throws DataAccessException
	 */
	public List<AphiEmp> queryAphiEmpAll(Map<String, Object> entityMap) throws DataAccessException;
}
