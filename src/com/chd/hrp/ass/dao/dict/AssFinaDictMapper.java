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

import com.chd.hrp.ass.entity.dict.AssFinaDict;

/**
 * @Description: 050101 财务分类字典
 * @Table: MAT_FINA_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AssFinaDictMapper extends SqlMapper {

	/**
	 * @Description 添加050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAssFinaDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAssFinaDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAssFinaDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新050101 财务分类字典<BR>
	 * @param entityMap
	 * @return AssFinaDict
	 * @throws DataAccessException
	 */
	public int updateBatchAssFinaDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 删除050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAssFinaDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 删除050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAssFinaDictBySuperCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除050101 财务分类字典<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchAssFinaDict(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集050101 财务分类字典<BR>
	 *              全部
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AssFinaDict> queryAssFinaDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集050101 财务分类字典<BR>
	 *              带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AssFinaDict> queryAssFinaDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

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

	/**
	 * 查询子集
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssFinaDict> queryAssFinaDictChild(Map<String, Object> mapVo) throws DataAccessException;

}
