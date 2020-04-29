package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmEmpDict;

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

public interface PrmEmpDictMapper  extends SqlMapper{

	/**
	 * 
	 */
	public int addPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8801 职工字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 *  批量添加职工
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int addBatchPrmEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 *  查询序列
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int queryPrmEmpDictSeqNextVal()throws DataAccessException;
	
	/**
	 *  修改停用状态
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updatePrmEmpDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  查询所有集合
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<PrmEmpDict> queryPrmEmpDictList(Map<String,Object> entityMap)throws DataAccessException;
	
}
