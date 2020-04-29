/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.apply;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.ass.entity.apply.AssApplyAcceptMap;
/**
 * 
 * @Description:
 * 050201 购置申请与验收关系表
 * @Table:
 * ASS_APPLY_ACCEPT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssApplyAcceptMapService {

	/**
	 * @Description 
	 * 添加050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssApplyAcceptMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssApplyAcceptMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssApplyAcceptMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssApplyAcceptMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050201 购置申请与验收关系表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssApplyAcceptMap(Map<String,Object> entityMap) throws DataAccessException;
	
}
