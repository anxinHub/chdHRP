package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HrDeptDict;


public interface HrDeptDictService {
	/**
	 * @Description 添加DeptDict
	 * @param DeptDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加DeptDict
	 * @param  DeptDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDeptDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public HrDeptDict queryDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	public HrDeptDict queryDeptDictByDeptCodeMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public HrDeptDict queryDeptDictByDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HrDeptDict> queryDeptDictByType(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 查询DeptDictByDeptNo
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrDeptDict queryDeptDictByDeptNo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除DeptDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除DeptDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新DeptDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入DeptDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptDictByMenu
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDeptDictByMenu(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询queryDeptDictBySelector
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDeptDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 联合查询DeptDict和DeptNo的所有数据
	 * @param  entityMap
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	public List<HrDeptDict> queryDeptDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 获取部门字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public List<?> queryDeptDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 部门字典 成本系统用<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public String queryDeptDictCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryDeptDictByPrmDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 用于查询 集团账簿中的部门选择器
	 * @Description 查询queryGroupDeptDictBySelector
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroupDeptDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
}

