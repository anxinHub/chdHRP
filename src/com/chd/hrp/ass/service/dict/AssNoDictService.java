
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssNoDict;
/**
 * 
 * @Description:
 * 050102 资产变更字典
 * @Table:
 * ASS_NO_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssNoDictService {

	/**
	 * @Description 
	 * 添加050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String updateAssNoDictCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssNoDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<AssNoDict> queryAssNoDictList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssNoDict queryAssNoDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050102 资产变更字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssNoDict
	 * @throws DataAccessException
	*/
	public AssNoDict queryAssNoDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAssNoDictTree(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 校验字典的准确性，各个字段是否符合要求
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDictByCheck(Map<String,Object> entityMap) throws DataAccessException;

}
