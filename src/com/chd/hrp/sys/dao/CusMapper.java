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
import com.chd.hrp.sys.entity.Cus;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 添加Cus
	 * @param Cus entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCus(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Cus
	 * @param  Cus entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Cus分页
	 * @param  entityMap RowBounds
	 * @return List<Cus>
	 * @throws DataAccessException
	*/
	public List<Cus> queryCus(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Cus所有数据
	 * @param  entityMap
	 * @return List<Cus>
	 * @throws DataAccessException
	*/
	public List<Cus> queryCus(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Cus queryCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Cus
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCus(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Cus
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Cus
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCus(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateCusByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Cus
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 添加、修改客户是 查询  与 输入的客户编码 、客户名字 相同的客户记录（判断客户编码、客户名称是否存在）
	 * @param entityMap
	 * @return
	 */
	public List<Cus> queryCusById(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团、医院下 最大的客户排序号
	 * @return
	 */
	public Cus queryMaxCus_sort() throws DataAccessException;
	/**
	 * 根据输入的  客户 排序号 查询 与该排序号冲突的客户记录 （判断排序号是否已经存在）
	 * @param entityMap
	 * @return
	 */
	public Cus queryCus_sort(Map<String, Object> entityMap);
}
