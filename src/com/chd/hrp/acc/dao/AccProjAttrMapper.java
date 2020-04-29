/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.entity.AccProjAttr;

/**
* @Title. @Description.
* 项目字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccProjAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 添加AccProjAttr
	 * @param AccProjAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量添加AccProjAttr
	 * @param  AccProjAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccProjAttr>
	 * @throws DataAccessException
	*/
	public List<AccProjAttr> queryAccProjAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttr所有数据
	 * @param  entityMap
	 * @return List<AccProjAttr>
	 * @throws DataAccessException
	*/
	public List<AccProjAttr> queryAccProjAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccProjAttr queryAccProjAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改项目时<BR> 查询ProjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccProjAttr queryProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AccProjAttr queryAccProjAttrByProj(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 添加项目时<BR> 查询HosProjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccProjAttr queryHosProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 删除AccProjAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量删除AccProjAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 项目字典属性表<BR> 更新AccProjAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量更新AccProjAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int addAccProjAttrNew(Map<String, Object> entityMap);
	/**
	 * 单条修改方法，表中字段有变动
	 * @param entityMap
	 * @return
	 */
	public int  updateAccProjAttrNew(Map<String, Object> entityMap);

	public int synchronizationAddAccProjAttrNew(Map<String, Object> entityMap);

	public int cancelauditBudgProjSetUp(Map<String, Object> map);

	public int endHosProj(Map<String, Object> map);

	public int escEndProj(Map<String, Object> map);

	public int suspendHosProj(Map<String, Object> map);

	public int escSuspendHosProj(Map<String, Object> map);

	//修改当先数据为历史数据
	public int updateProDictHistory(Map<String, Object> entityMap);


}
