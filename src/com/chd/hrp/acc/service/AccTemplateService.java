/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccTemplate;

/**
* @Title. @Description.
* 凭证模板主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTemplateService {

	/**
	 * @Description 
	 * 凭证模板主表<BR> 添加AccTemplate
	 * @param AccTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量添加AccTemplate
	 * @param  AccTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplate分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccTemplate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplateByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccTemplate queryAccTemplateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 删除AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量删除AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 更新AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量更新AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 导入AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccVouchSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	public String extendAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
}
