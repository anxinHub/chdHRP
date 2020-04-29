
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssFinaDict;
/**
 * 
 * @Description:
 * 050101 财务分类字典
 * @Table:
 * MAT_FINA_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssFinaDictService {

	/**
	 * @Description 
	 * 添加050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssFinaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssFinaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssFinaDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssFinaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssFinaDict(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteAssFinaDictBySuperCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssFinaDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050101 财务分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssFinaDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 获取050101 财务分类字典<BR>
	 * @param entityMap
	 *            主键
	 * @return AssFinaDict
	 * @throws DataAccessException
	 */
	public AssFinaDict queryAssFinaDictByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取050101 财务分类字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssFinaDict
	 * @throws DataAccessException
	 */
	public AssFinaDict queryAssFinaDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 获取050101 财务分类字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssFinaDict
	 * @throws DataAccessException
	 */
	public List<?> queryAssFinaDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AssFinaDict> queryAssFinaDictList(Map<String, Object> entityMap) throws DataAccessException;

	public String readAssFinaDictFiles(Map<String, Object> map) throws DataAccessException;

	/**
	 * 查询子集 zhaon
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssFinaDict> queryAssFinaDictChild(Map<String, Object> mapVo)throws DataAccessException;

}
