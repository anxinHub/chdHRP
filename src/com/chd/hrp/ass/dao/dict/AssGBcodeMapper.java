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

import com.chd.hrp.ass.entity.dict.AssGBcode;



public interface AssGBcodeMapper extends SqlMapper {

	/**
	 * @Description 添加050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAssGBcode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAssGBcode(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAssGBcode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新050101<BR>
	 * @param entityMap
	 * @return AssGBcode
	 * @throws DataAccessException
	 */
	public int updateBatchAssGBcode(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 删除050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAssGBcode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 删除050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAssGBcodeBySuperCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除050101<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchAssGBcode(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集050101<BR>
	 *              全部
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AssGBcode> queryAssGBcode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集050101<BR>
	 *              带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<AssGBcode> queryAssGBcode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 获取050101<BR>
	 * @param entityMap
	 *            主键
	 * @return AssGBcode
	 * @throws DataAccessException
	 */
	public AssGBcode queryAssGBcodeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取050101<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssGBcode
	 * @throws DataAccessException
	 */
	public AssGBcode queryAssGBcodeByUniqueness(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 获取050101树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssGBcode
	 * @throws DataAccessException
	 */
	public List<?> queryAssGBcodeByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询子集
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssGBcode> queryAssGBcodeChild(Map<String, Object> mapVo) throws DataAccessException;

	public AssGBcode queryAssGBcodeByUniquenessname(Map<String, Object> map_name) throws DataAccessException;

}
