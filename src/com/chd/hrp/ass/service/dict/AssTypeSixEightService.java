
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

import com.chd.hrp.ass.entity.dict.AssTypeSixEight;
/**
 * 
 * @Description:
 * 050104 68分类字典
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
 

public interface AssTypeSixEightService {

	/**
	 * @Description 
	 * 添加050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssTypeSixEight(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssTypeSixEight(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssTypeSixEight queryAssTypeSixEightByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 判断编码or名称重复  zhaon
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AssTypeSixEight queryAssTypeSixEightByCodeOrName(Map<String, Object> mapVo)throws DataAccessException;
	
	
	public List<AssTypeSixEight> queryAssTypeSixEightChild(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 导入数据 050104 68分类字典
	 * @param mapVo
	 * @return
	 * @throws Exception 
	 */
	public String assTypeSixEightImport(Map<String, Object> mapVo) throws Exception;
	
}
