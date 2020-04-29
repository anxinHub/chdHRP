/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageTax;

/**
* @Title. @Description.
* 个人所得税税率<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxService {

	/**
	 * @Description 
	 * 个人所得税税率<BR> 添加AccWageTax
	 * @param AccWageTax entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量添加AccWageTax
	 * @param  AccWageTax entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询AccWageTax分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageTax(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 查询AccWageTaxByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageTax queryAccWageTaxByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 删除AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量删除AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 更新AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageTax(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 批量更新AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageTax(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税税率<BR> 导入AccWageTax
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageTax(Map<String,Object> entityMap)throws DataAccessException;

	public List<AccWageTax> queryAccWageTaxEnds(Map<String,Object> entityMap)throws DataAccessException;

}
