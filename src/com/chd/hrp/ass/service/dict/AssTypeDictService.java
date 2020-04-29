
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssTypeDict;
/**
 * 
 * @Description:
 * 050101 资产分类字典
 * @Table:
 * ASS_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssTypeDictService {

	/**
	 * @Description 
	 * 添加050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteAssTypeDictBySuperCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050101 资产分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 获取050101 资产分类字典<BR>
	 * @param entityMap
	 *            主键
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public AssTypeDict queryAssTypeDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取050101 资产分类字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public AssTypeDict queryAssTypeDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 获取050101 资产分类字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public List<?> queryAssTypeDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AssTypeDict> queryAssTypeDictList(Map<String, Object> entityMap) throws DataAccessException;

	public String readAssTypeDictFiles(Map<String, Object> map) throws DataAccessException;

	public List<AssTypeDict> querycount(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询子集 zhaon
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssTypeDict> queryAssTypeDiceChild(Map<String, Object> mapVo)throws DataAccessException;
}
