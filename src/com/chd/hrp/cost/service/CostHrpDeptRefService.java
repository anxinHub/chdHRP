/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Dept;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostHrpDeptRefService {

	/**
	 * @Description 添加Dept
	 * @param Dept entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Dept
	 * @param  Dept entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String delete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String update(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 导入时 校验  系统科室编码是否合法
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHrpDeptDate(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 导入时 校验  自定义科室编码是否合法
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptDate(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 系统科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrpDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 自定义科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostCustomDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 停用
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String endCostHrpDeptRef(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
}
