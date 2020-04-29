/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.EmpKind;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface EmpKindService {

	/**
	 * @Description 添加EmpKind
	 * @param EmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加EmpKind
	 * @param  EmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询EmpKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public EmpKind queryEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
}
