/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccSubjNature;

/**
* @Title. @Description.
* 科目性质<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjNatureService {

	/**
	 * @Description 
	 * 科目性质<BR> 添加AccSubjNature
	 * @param AccSubjNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量添加AccSubjNature
	 * @param  AccSubjNature entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNature分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNatureByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSubjNature queryAccSubjNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 删除AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量删除AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 更新AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量更新AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 导入AccSubjNature
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
}
