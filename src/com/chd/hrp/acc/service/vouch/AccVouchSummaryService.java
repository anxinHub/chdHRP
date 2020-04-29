/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.vouch;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccVouchSummary;

/**
* @Title. @Description.
* 凭证摘要主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchSummaryService {

	/**
	 * @Description 
	 * 凭证摘要主表<BR> 添加AccVouchSummary
	 * @param AccVouchSummary entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccVouchSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 批量添加AccVouchSummary
	 * @param  AccVouchSummary entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccVouchSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 查询AccVouchSummary分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 查询AccVouchSummaryByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccVouchSummary queryAccVouchSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 删除AccVouchSummary
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccVouchSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 批量删除AccVouchSummary
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccVouchSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 更新AccVouchSummary
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccVouchSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 批量更新AccVouchSummary
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccVouchSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证摘要主表<BR> 导入AccVouchSummary
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccVouchSummary(Map<String,Object> entityMap)throws DataAccessException;

}
