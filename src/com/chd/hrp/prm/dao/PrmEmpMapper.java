package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.prm.entity.PrmEmp;

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

public interface PrmEmpMapper  extends SqlMapper{

	/**
	 * 
	 */
	
	
	public List<PrmEmp> queryPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	public List<PrmEmp> queryPrmEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int addPrmEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int initBatchPrmEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<PrmEmp> queryPrmEmp(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 * */

	public List<PrmEmp> queryPrmEmp(Map<String, Object> entityMap)
			throws DataAccessException;
	/**
	 * @Description 查询EmpByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public PrmEmp queryPrmEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public PrmEmp queryPrmEmpByCodeEmp(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新8801 职工字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmp(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 *  删除职工
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteBatchPrmEmp(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public int addBatchPrmEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateBatchPrmEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 查询 序列
	 * 
	 * @param
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryPrmEmpSeqNextVal() throws DataAccessException;

	public List<PrmEmp> queryPrmEmpAll(Map<String, Object> entityMap) throws DataAccessException;
}
