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
import com.chd.hrp.sys.entity.Fac;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface FacMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 添加Fac
	 * @param Fac entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addFac(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Fac
	 * @param  Fac entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Fac分页
	 * @param  entityMap RowBounds
	 * @return List<Fac>
	 * @throws DataAccessException
	*/
	public List<Fac> queryFac(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Fac所有数据
	 * @param  entityMap
	 * @return List<Fac>
	 * @throws DataAccessException
	*/
	public List<Fac> queryFac(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Fac queryFacByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Fac
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteFac(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Fac
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Fac
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateFac(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateFacByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateFacByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Fac
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 添加、修改生产厂商时 查询 与输入的生产厂商编码 、生产厂商名称一样的记录（ 判断 输入的 生产厂商编码 、生产厂商名称 是否已经存在）
	 * @param entityMap
	 * @return
	 */
	public List<Fac> queryFacById(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 根据修改输入时的生产厂商排序号 查询 与该排序号的生产厂商记录 （判断 输入的 生产厂商排序号 是否已经存在）
	 * @param entityMap
	 * @return
	 */
	public Fac queryFac_sort(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团/医院下的 生产 厂商的最大排序号
	 * @param entityMap
	 * @return
	 */
	public Fac queryMaxFac_sort(Map<String, Object> entityMap);
	
	
    public Fac  queryFacMaxTypeCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public Fac  queryFacMaxTypePy(Map<String, Object> entityMap)throws DataAccessException;
	
	public int queryFacInv(Map<String, Object> entityMap)throws DataAccessException;
	
	public int existsGroupFacByAdd(Map<String, Object> entityMap);
	public int existsHosIdByFac(Map<String, Object> entityMap);
	public int addBatchGroupFacByCode(Map<String, Object> entityMap);
	public int addBatchGroupFacDictByCode(Map<String, Object> entityMap);
	
	public String queryMaxFacCode(Map<String, Object> entityMap);
}
