/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wage;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageEmp;
 
/**
* @Title. @Description.
* 工资套职工关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageEmpService {

	/**
	 * @Description 
	 * 工资套职工关系<BR> 添加AccWageEmp
	 * @param AccWageEmp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量添加AccWageEmp
	 * @param  AccWageEmp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmp分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageEmpList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmp分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageEmp queryAccWageEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 删除AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量删除AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 更新AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量更新AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 导入AccWageEmp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageEmpListPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWageEmp queryAccWageEmpCodeByImp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询工资套与职工没有关联
	 */
	public String queryAccWageEmpNotBind(Map<String, Object> paraMap) throws DataAccessException;
}
