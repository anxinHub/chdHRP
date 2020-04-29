/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.emp;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccHosEmpKind;

/**
* @Title. @Description.
* 职工分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccHosEmpKindService {

	/**
	 * @Description 
	 * 职工分类<BR> 添加AccHosEmpKind
	 * @param AccHosEmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量添加AccHosEmpKind
	 * @param  AccHosEmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccHosEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccHosEmpKindPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccHosEmpKind queryAccHosEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 删除AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量删除AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 更新AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量更新AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
