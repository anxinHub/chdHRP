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
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.DeptDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DeptDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加DeptDict
	 * @param DeptDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加DeptDict
	 * @param  DeptDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptDict分页
	 * @param  entityMap RowBounds
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<DeptDict> queryDeptDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询DeptDict所有数据
	 * @param  entityMap
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<DeptDict> queryDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 查询DeptDict所有数据 成本系统用
	 * @param  entityMap
	 * @return List<DeptDict>
	 * @throws DataAccessException
	 */
	public List<DeptDict> queryDeptDictCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptDict所有数据 成本系统用 DeptDict分页
	 * @param  entityMap RowBounds
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<DeptDict> queryDeptDictCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 查询DeptDict分页
	 * @param  entityMap RowBounds
	 * @return List<DeptDict>
	 * @throws DataAccessException
	 */
	public List<DeptDict> queryDeptDictLike(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 查询DeptDict所有数据
	 * @param  entityMap
	 * @return List<DeptDict>
	 * @throws DataAccessException
	 */
	public List<DeptDict> queryDeptDictLike(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDeptDictLikePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<DeptDict> queryDeptDictByType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public DeptDict queryDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	public DeptDict queryDeptDictByCodeList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 和queryDeptDictByCode 不同的地方 is_stop 是从前台传过来
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public DeptDict queryDeptDictByDeptCodeMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public DeptDict queryDeptDictByDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptDictByDeptNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public DeptDict queryDeptDictByDeptNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除DeptDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除DeptDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新DeptDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新DeptDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 联合查询DeptDict和DeptNo的所有数据
	 * @param  entityMap
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<DeptDict> queryDeptDictNo(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 修改部门不保留历史记录时 修改部门变更记录
	 * @param entityMap
	 */
	public void updateDeptDict_dept(Map<String, Object> entityMap);
	
	/**
	 * @Description 获取部门字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public List<?> queryDeptDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<DeptDict> queryDeptDictByPrmDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<DeptDict> queryDeptDictByPrmDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<DeptDict> queryDeptByEmpid(Map<String, Object> mapVo);

	/**
	 * 编码或名称查询  期初卡片导入使用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public DeptDict queryDeptDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;
     //成本导入查询
	public DeptDict queryDeptDictByCodeDept(Map<String, Object> entityMap)throws DataAccessException;

	public void hrdeleteBatchDeptDict(List<Dept> entityList);
	/**
	 * 用于查询 集团账簿中的部门选择器
	 * @Description 查询GroupDeptDict分页
	 * @param  entityMap RowBounds
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<DeptDict> queryGroupDeptDict(Map<String,Object> entityMap) throws DataAccessException;

	public List<DeptDict> queryBySuperCode(
			Map<String, Object> entityMap);
   /**
    * 更新类别编码
    * @param entityMap
    * @param listDept
    */
	public void updateBatchSuperCode(@Param(value="map") Map<String, Object> entityMap, @Param(value="list")List<DeptDict> listDept);
    /**
     * 更新是否末级
     * @param isLast
     */
	public void updateIsLast(Map<String, Object> isLast);
     /**
      * 修改是否停用
      * @param entityMap
      * @param listDept
      */
	public void updateBatchStop(@Param(value="map") Map<String, Object> entityMap, @Param(value="list")List<DeptDict> listDept);
}
