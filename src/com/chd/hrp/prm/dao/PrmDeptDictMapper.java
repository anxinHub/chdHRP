
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.prm.entity.PrmDeptDict;
/**
 * 
 * @Description:
 * 8804 科室字典变更表
 * @Table:
 * Prm_DEPT_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return PrmDeptDict
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8804 科室字典变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptDict> queryPrmDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8804 科室字典变更表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptDict> queryPrmDeptDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return PrmDeptDict
	 * @throws DataAccessException
	*/
	public PrmDeptDict queryPrmDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询序列  科室字典变更表<BR> 
	 * @param  entityMap
	 * @return PrmDeptDict
	 * @throws DataAccessException
	*/
	public int queryPrmDeptDictSeqNextval()throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改停用状态  科室字典变更表<BR> 
	 * @param  entityMap
	 * @return PrmDeptDict
	 * @throws DataAccessException
	*/
	public int updatePrmDeptDictIsStopState(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 科室字典变更表 查询当前科室最大变更号<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryPrmDeptDictMaxNo(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
