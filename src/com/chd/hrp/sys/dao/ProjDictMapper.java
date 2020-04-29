/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.ProjDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加ProjDict
	 * @param ProjDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjDict
	 * @param  ProjDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjDict分页
	 * @param  entityMap RowBounds
	 * @return List<ProjDict>
	 * @throws DataAccessException
	*/
	public List<ProjDict> queryProjDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询ProjDict所有数据
	 * @param  entityMap
	 * @return List<ProjDict>
	 * @throws DataAccessException
	*/
	public List<ProjDict> queryProjDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryProjDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询queryProjDictBySelector
	 * @param  entityMap
	 * @return List<ProjDict>
	 * @throws DataAccessException
	*/
	public List<ProjDict> queryProjDictBySelector(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ProjDict queryProjDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新ProjDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjDictState(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 修改项目编码 不保留变更历史 修改对应项目变更记录
	 * @param entityMap
	 */
	public void updateProjDict_Proj(Map<String, Object> entityMap);

	/**
	 * 编码或名称查询  期初卡片导入使用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public ProjDict queryProjDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 用于集团化   项目选择器中  项目字典
	 * @Description 查询queryGroupProjDictBySelector
	 * @param  entityMap
	 * @return List<ProjDict>
	 * @throws DataAccessException
	*/
	public List<ProjDict> queryGroupProjDictBySelector(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryProjDictList(Map<String, Object> paramMap) throws DataAccessException;

	public List<Map<String, Object>> queryProjDictList(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;


}
