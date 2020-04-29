/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageSchemeKind;

/**
* @Title. @Description.
* 工资方案职工<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageSchemeKindService {

	/**
	 * @Description 
	 * 工资方案职工<BR> 添加AccWageSchemeKind
	 * @param AccWageSchemeKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量添加AccWageSchemeKind
	 * @param  AccWageSchemeKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageSchemeKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 查询AccWageSchemeKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageSchemeKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 查询AccWageSchemeKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageSchemeKind queryAccWageSchemeKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageSchemeKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageSchemeKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 导入AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 保存工资方案与职工分类关系
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String saveAccWageSKind(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询工资方案与职工分类关系
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccWageSKind(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除工资方案与职工分类关系
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteAccWageSKind(Map<String, Object> paramMap) throws DataAccessException;
}
