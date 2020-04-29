/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.ins;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.ins.AssInsDetail;
/**
 * 
 * @Description:
 * 050601 资产安装明细
 * @Table:
 * ASS_INS_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInsDetailService {

	/**
	 * @Description 
	 * 添加050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssInsDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssInsDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050601 资产安装明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssInsDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050601 资产安装明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssInsDetail queryAssInsDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsDetail
	 * @throws DataAccessException
	*/
	public AssInsDetail queryAssInsDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsDetail>
	 * @throws DataAccessException
	*/
	public List<AssInsDetail> queryAssInsDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssInsDetail> queryAssInsDetailByAccept(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAssInsDetailByUpdate(Map<String,Object> entityMap)throws DataAccessException;

	public List<?> queryExists(Map<String, Object> entityMap)throws DataAccessException;

	public String initAssInsDetailtBid(Map<String, Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAssInsContractMap(List<Map<String, Object>> entityList)throws DataAccessException;
}
