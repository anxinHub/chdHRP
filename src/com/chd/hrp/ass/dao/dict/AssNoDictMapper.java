
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
 


public interface AssNoDictMapper extends SqlMapper{
	/** 
	 * @Description 
	 * 添加050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAssNoDictCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return AssNoDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssNoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssNoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050102 资产变更字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssNoDict> queryAssNoDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050102 资产变更字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssNoDict> queryAssNoDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050102 资产变更字典<BR> 
	 * @param  entityMap
	 * @return AssNoDict
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

	/**
	 * 导入ass_dict时批量插入变更表
	 * @param entityMapList
	 * @return
	 * @throws DataAccessException
	 */
	public int batchImportAssNoDict(List<Map<String, Object>> entityMapList)throws DataAccessException;

	/**
	 * 获取050102 资产变更字典
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public AssNoDict queryAssNoDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 校验数据，批量更新需要赋默认值的字典
	 * @param entityMapList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAssNoDictCheckData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAssNoDictSaveCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 校验数据，批量更新需要赋默认值的字典
	 * @param entityMapList
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAssNoDictIsDepre(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 校验数据，批量更新需要赋默认值的字典
	 * @param entityMapList
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAssNoDictIsManageDepre(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 校验字典中 不允许为空的字段 是否有默认值
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryAssNoDictIsCheckData(Map<String,Object> entityMap)throws DataAccessException;
	
}
