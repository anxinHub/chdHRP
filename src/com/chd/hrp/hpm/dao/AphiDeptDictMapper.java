
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.dao;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;

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
 


public interface AphiDeptDictMapper extends SqlMapper{
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
	public int addBatchAphiDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	 * @return int
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
	public List<AphiDeptDict> queryPrmDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8804 科室字典变更表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiDeptDict> queryPrmDeptDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return AphiDeptDict
	 * @throws DataAccessException
	*/
	public AphiDeptDict queryPrmDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询序列  科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAphiDeptDictSeqNextval()throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改停用状态  科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAphiDeptDictIsStopState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室字典变更表 查询当前科室最大变更号<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAphiDeptDictMaxNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室字典变更表 查询科室编码是否存在<BR> 
	 * @param  entityMap
	 * @return AphiDeptDict
	 * @throws DataAccessException
	*/
	public AphiDeptDict queryAphiDeptDictExits(Map<String,Object> entityMap)throws DataAccessException;

	//科室与收入项目关系配置
	public List<AphiDeptDict> queryDeptIncomeAdd(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptDict> queryDeptIncomeAdd(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;
}
