/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageTaxSet;

/**
* @Title. @Description.
* 个人所得税起征点<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxSetService {

	/**
	 * @Description 
	 * 个人所得税起征点<BR> 添加AccWageTaxSet
	 * @param AccWageTaxSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量添加AccWageTaxSet
	 * @param  AccWageTaxSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 查询AccWageTaxSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageTaxSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 查询AccWageTaxSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageTaxSet queryAccWageTaxSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 删除AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量删除AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 更新AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 批量更新AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageTaxSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个人所得税起征点<BR> 导入AccWageTaxSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageTaxSet(Map<String,Object> entityMap)throws DataAccessException;

}
