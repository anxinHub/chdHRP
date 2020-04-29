/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.ins;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.ins.AssInsMain;
/**
 * 
 * @Description:
 * 050601 资产安装主表
 * @Table:
 * ASS_INS_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInsMainService {
	public Long queryAssInsMainSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssInsMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssInsMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssInsMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050601 资产安装主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssInsMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssInsMain queryAssInsMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssInsMain
	 * @throws DataAccessException
	*/
	public AssInsMain queryAssInsMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产安装主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssInsMain>
	 * @throws DataAccessException
	*/
	public List<AssInsMain> queryAssInsMainExists(Map<String,Object> entityMap)throws DataAccessException;

	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	//资产打印
	Map<String, Object> queryAssInsMainDY(Map<String, Object> map)
			throws DataAccessException;
	public List<String> queryInsMainState(Map<String, Object> mapVo)throws DataAccessException;
}
