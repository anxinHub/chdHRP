package com.chd.hrp.med.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.med.entity.MedType;
/**
 * 
 * @Description:药品分类
 * @Copyright: Copyright (c) 2016-11-28 上午10:46:39
 * @Author: xuyongwei
 * @Version: 6.0
 */
public interface MedTypeMapper extends SqlMapper {

	
	/**
	 * @Description 
	 * 获取 药品分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedType
	 * @throws DataAccessException
	*/
	public MedType queryMedTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08102 药品分类字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedType> queryMedType(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取08102 药品分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为编码和名称用于判断是否重复
	 * @return MedType
	 * @throws DataAccessException
	*/
	public List<MedType> queryMedTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08102 药品分类字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedType> queryMedType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改上级类别末级标识<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedTypeIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedTypeSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateMedTypeByDict(Map<String,Object> entityMap)throws DataAccessException;
	

	
	
}
