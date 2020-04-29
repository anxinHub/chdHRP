/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatTypeDict;
/**
 * 
 * @Description:
 * 04104 物资分类变更表
 * @Table:
 * MAT_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatTypeDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增变更表删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatTypeDictForDelete(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeDictIsStop(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeDictSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新是否末级标志<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeDictIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public int updateBatchMatTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量删除04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatTypeDict> queryMatTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatTypeDict> queryMatTypeDictTwo(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatTypeDict> queryMatTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public MatTypeDict queryMatTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public MatTypeDict queryMatTypeDictById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	public MatTypeDict queryMatTypeDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
