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
import com.chd.hrp.sys.entity.Proj;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

 
public interface ProjMapper extends SqlMapper{
	
	/**
	 * @Description 添加Proj
	 * @param Proj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Proj
	 * @param  Proj entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Proj分页
	 * @param  entityMap RowBounds
	 * @return List<Proj>
	 * @throws DataAccessException
	*/
	public List<Proj> queryProj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Proj所有数据
	 * @param  entityMap
	 * @return List<Proj>
	 * @throws DataAccessException
	*/
	public List<Proj> queryProj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Proj queryProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Proj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Proj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Proj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Proj
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询proj序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryProjCurrentSequence()throws DataAccessException;
	
	/**
	 * 修改时查询 项目编码 、项目名是否存在
	 * @param entityMap
	 * @return 
	 */
	public List<Proj> queryProjById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询当前集团、医院下的项目最大排序号
	 * @param entityMap
	 * @return
	 */
	public Proj queryMaxProj_sort(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询 当前集团、医院下 与输入的项目排序号相同 的 项目记录 （判断排序号是否已存在）
	 * @param entityMap
	 * @return
	 */
	public Proj queryProj_sort(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 用于判断  该项目是否可以进行停用 （如果没有发生业务数据 则可以停用）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryProjByStop(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询带有相关负责人的项目信息
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryAccProj(Map<String, Object> entityMap);
	public List<Map<String, Object>> queryAccProj(Map<String, Object> entityMap, RowBounds rowBounds);
}
