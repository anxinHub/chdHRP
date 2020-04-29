/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.Dept;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DeptMapper extends SqlMapper{
	
	/**
	 * @Description 添加Dept
	 * @param Dept entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Dept
	 * @param  Dept entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Dept分页
	 * @param  entityMap RowBounds
	 * @return List<Dept>
	 * @throws DataAccessException
	*/
	public List<Dept> queryDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Dept所有数据
	 * @param  entityMap
	 * @return List<Dept>
	 * @throws DataAccessException
	*/
	public List<Dept> queryDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Dept queryDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 删除Dept
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Dept
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Dept
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Dept
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 添加、修改部门时 查询  与输入 部门编码 或 部门名称 一样的部门记录 （ 判断部门编码、部门名称是否存在）
	 * @param entityMap
	 * @return
	 */
	public List<Dept> queryDeptById(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询当前集团、医院下的 部门 的最大排序号
	 * @param entityMap
	 * @return
	 */
	public Dept queryMaxDept_sort(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据输入的部门 排序号 查询与其相同的部门记录 （判断排序号是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Dept queryDept_sort(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 输入的 部门编码 查询其上级部门编码（上级部门编码不存在则不允许添加、修改）
	 * @param entityMap 
	 * @return
	 */
	public List<Map<String,Object>> qureySurp_code(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryDeptByCodeName(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 查询是否存在下级
     * @param map
     * @return
     */
	public List<Map<String, Object>> qureySurpcode(Map<String, Object> map) throws DataAccessException;
   /**
    * 部门架构图
    * @param map
    * @return
    * @throws DataAccessException
    */
	public List<Map<String, Object>> queryDeptOrg(Map<String, Object> map) throws DataAccessException;
	 /**
	    * 上级编码
	    * @param map
	    * @return
	    * @throws DataAccessException
	    */
	public List<Dept> queryBySuperCode(Map<String, Object> entityMap) throws DataAccessException;
	 /**
	    * 更新类别编码
	    * @param map
	    * @return
	    * @throws DataAccessException
	    */
	public void updateBatchSuperCode(@Param(value="map") Map<String, Object> entityMap, @Param(value="list")List<Dept> listDept) throws DataAccessException;
	 /**
	    * 更新是否末级
	    * @param map
	    * @return
	    * @throws DataAccessException
	    */
	public void updateIsLast(Map<String, Object> isLast) throws DataAccessException;
     /**
      * 修改是否停用
      * @param entityMap
      * @param listDept
      * @throws DataAccessException
      */
	public void updateBatchStop(@Param(value="map") Map<String, Object> entityMap, @Param(value="list")List<Dept> listDept)throws DataAccessException;
     /**
      * 查询上级是否存在
      * @param map
      * @return
      * @throws DataAccessException
      */
	public Dept qureySurpDept(Map<String, Object> map)throws DataAccessException;
    /**
     * 查询是否有下级
     * @param superCodeMap
     * @throws DataAccessException
     */
	public void querySuperCode(List<Dept> superCodeMap)throws DataAccessException;

	public List<Dept> queryDeptByIdName(Map<String, Object> entityMap);
}
