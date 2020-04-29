package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssSupAttr;

/**
 * 
 * @Description:
 * 050113 供应商附属表
 * @Table:
 * ASS_Sup_ATTR
 * @Author: linuxxu
 * @email:  linuxxu@s-chd.com
 * @Version: 1.0
 *  com.chd.hrp.mat.dao.infomaint.basicset.MatSupAttrMapper
 */
public interface MatSupAttrMapper extends SqlMapper{

	/**
	 * @Description 
	 * 添加050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return AssSupAttr
	 * @throws DataAccessException
	*/
	public int updateBatchMatSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050113 供应商附属表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssSupAttr> queryMatSupAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050113 供应商附属表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssSupAttr> queryMatSupAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050113 供应商附属表<BR> 
	 * @param  entityMap
	 * @return AssSupAttr
	 * @throws DataAccessException
	*/
	public AssSupAttr queryMatSupAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 根据 供应商编码 查询 供应商ID(导入使用)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long querySupIdByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据 科室编码查询科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryDeptIdByCode(Map<String, Object> mapVo) throws DataAccessException;
	/***
	 * 加 供应商归属系统表 
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void addHosSupMod(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 供应商 系统模块对应关系是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryHosSupModExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新 供应商 系统模块对应关系
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateHosSupMod(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除 供应商与系统模块对应关系
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteBatchHosSupMod(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * 根据供应商类别编码 查询该供应商类别是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySupTypeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据供应商ID 查询该供应商附属表是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySupAttrExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 所属系统模块编码是否存在 
	 * @param mapVo
	 * @return
	 */
	public int queryModExist(Map<String, Object> mapVo) throws DataAccessException;
	
	public int addMatSupAttrUser(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addMatSupAttrRole(Map<String, Object> entityMap)throws DataAccessException;
	
}
