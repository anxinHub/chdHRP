/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.accept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
/**
 * 
 * @Description:
 * 050601 资产验收明细
 * @Table:
 * ASS_ACCEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssAcceptDetailService {

	/**
	 * @Description 
	 * 添加050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssAcceptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssAcceptDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050601 资产验收明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssAcceptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050601 资产验收明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssAcceptDetail queryAssAcceptDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAcceptDetail
	 * @throws DataAccessException
	*/
	public AssAcceptDetail queryAssAcceptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryAssAcceptDetailByUpdate(Map<String,Object> entityMap)throws DataAccessException;
	public String queryAssAcceptDetailIn(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssAcceptDetail> queryAssAcceptDetailExists(
			Map<String, Object> entityMap)throws DataAccessException;

	public String initAssAcceptDetail(Map<String, Object> entityMap)throws DataAccessException;

	public String initInstallAssAcceptDetail(Map<String, Object> entityMap)throws DataAccessException;

	public String initAssAcceptDetailBid(Map<String, Object> entityMap)throws DataAccessException;

	public String initInstallAssAcceptDetailBid(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAssAcceptInsMap(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String deleteBatchAssAcceptContractMap(List<Map<String, Object>> entityMap)throws DataAccessException;

}
