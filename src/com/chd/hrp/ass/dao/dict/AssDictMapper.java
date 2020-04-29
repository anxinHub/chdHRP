
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.ass.entity.dict.AssDict;
/**
 * 
 * @Description:
 * 050102 资产字典
 * @Table:
 * ASS_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssDictMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加050102 资产字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050102 资产字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050102 资产字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050102 资产字典<BR> 
	 * @param  entityMap
	 * @return AssDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050102 资产字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050102 资产字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050102 资产字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDict> queryAssDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050102 资产字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDict> queryAssDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050102 资产字典<BR> 
	 * @param  entityMap
	 * @return AssDict
	 * @throws DataAccessException
	*/
	public AssDict queryAssDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AssDict queryAssDictByAssCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050102 资产字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDict
	 * @throws DataAccessException
	*/
	public AssDict queryAssDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	//通过ID查询资产字典
	public AssDict queryAssDictByAssId(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<?> queryAssDictTree(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 根据编码或名称查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryAssDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 批量导入
	 * @param batchAddList
	 */
	public int batchImportAssDict(List<Map<String, Object>> entityMapList)throws DataAccessException;

	public Map<String,Object> queryasstypeid(Map<String, Object> mapVo);
}
