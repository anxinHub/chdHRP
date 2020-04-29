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
import com.chd.hrp.mat.entity.MatInstruType;
/**
 * 
 * @Description:
 * 医疗器械分类字典
 * @Table:
 * MAT_INSTRU_TYPE
 * @Author: weixiaofeng
 * @Version: 1.0
 */

public interface MatInstruTypeMapper extends SqlMapper{

	/**
	 * @Description 
	 * 添加医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public int updateBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatInstruType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除医疗器械分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchMatInstruType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集医疗器械分类字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInstruType> queryMatInstruType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集医疗器械分类字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInstruType> queryMatInstruType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取医疗器械分类字典<BR> 
	 * @param  entityMap <BR>
	 * 参数为编码和名称用于判断是否重复
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public List<MatInstruType> queryMatInstruTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取医疗器械分类字典<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public MatInstruType queryMatInstruTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取医疗器械分类字典<BR> 
	 * @param  entityMap<BR>
	 * 参数为要检索的字段
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	public MatInstruType queryMatInstruTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取物资类别用于导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInstruTypeListForImport(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改list中的物资类别为非末级
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatInstruTypeIsLastForImport(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> list)throws DataAccessException;

	/**
	 * 查询当前医疗机械分类是否被使用
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsInvUse(Map<String, Object> entityMap)throws DataAccessException;
	
	
	public int updateLast(Map<String, Object> entityMap) throws DataAccessException;

	
	public String selectSuperCode(Map<String, Object> entityMap) throws DataAccessException;
}
