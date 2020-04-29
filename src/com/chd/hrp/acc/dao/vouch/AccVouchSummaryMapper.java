/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.acc.entity.AccVouchSummary;

/**
 * @Title. @Description. 凭证摘要主表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccVouchSummaryMapper extends SqlMapper {

	/**
	 * @Description 凭证摘要主表<BR>
	 *              添加AccVouchSummary
	 * @param AccVouchSummary
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              批量添加AccVouchSummary
	 * @param AccVouchSummary
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchAccVouchSummary(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              查询AccVouchSummary分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<AccVouchSummary>
	 * @throws DataAccessException
	 */
	public List<AccVouchSummary> queryAccVouchSummary(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              查询AccVouchSummary所有数据
	 * @param entityMap
	 * @return List<AccVouchSummary>
	 * @throws DataAccessException
	 */
	public List<AccVouchSummary> queryAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              查询AccVouchSummaryByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public AccVouchSummary queryAccVouchSummaryByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              删除AccVouchSummary
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              批量删除AccVouchSummary
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchAccVouchSummary(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              更新AccVouchSummary
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateAccVouchSummary(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 凭证摘要主表<BR>
	 *              批量更新AccVouchSummary
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchAccVouchSummary(List<Map<String, Object>> entityMap) throws DataAccessException;
	
}
