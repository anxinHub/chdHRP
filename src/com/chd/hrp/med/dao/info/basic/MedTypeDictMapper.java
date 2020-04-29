package com.chd.hrp.med.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.med.entity.MedTypeDict;
import com.chd.hrp.med.entity.MedTypeDict;

public interface MedTypeDictMapper extends SqlMapper {

	/**
	 * @Description 
	 * 查询结果集 药品分类变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedTypeDict> queryMedTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08103药品分类变更表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedTypeDict> queryMedTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedTypeDict> queryMedTypeDictTwo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加08103药品分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新是否末级标志<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedTypeDictIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedTypeDictSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增变更表删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedTypeDictForDelete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新药品分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedTypeDictIsStop(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateMedTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取药品分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedTypeDict
	 * @throws DataAccessException
	*/
	public MedTypeDict queryMedTypeDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
