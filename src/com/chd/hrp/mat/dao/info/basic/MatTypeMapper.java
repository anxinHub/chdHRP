/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatType;
/**
 * 
 * @Description:
 * 04103 物资分类字典
 * @Table:
 * MAT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatTypeMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取自增序列
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int queryMatTypeSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatType(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeByDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return MatType
	 * @throws DataAccessException
	*/
	public int updateBatchMatType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatType> queryMatType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatType> queryMatType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为编码和名称用于判断是否重复
	 * @return MatType
	 * @throws DataAccessException
	*/
	public List<MatType> queryMatTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatType
	 * @throws DataAccessException
	*/
	public MatType queryMatTypeById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatType
	 * @throws DataAccessException
	*/
	public MatType queryMatTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改上级类别末级标识<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeIsLast(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除或变更类别修改其上级类别无下级的修改为末级<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatTypeSuperIsLastByIds(Map<String,Object> entityMap)throws DataAccessException;
	
	//获取物资类别用于导入
	public List<Map<String, Object>> queryMatTypeListForImport(Map<String, Object> entityMap)throws DataAccessException;
	//获取物资财务分类用于导入
	public List<Map<String, Object>> queryMatFimTypeListForImport(Map<String, Object> entityMap)throws DataAccessException;
	//修改list中的物资类别为非末级
	public int updateMatTypeIsLastForImport(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> list)throws DataAccessException;
}
