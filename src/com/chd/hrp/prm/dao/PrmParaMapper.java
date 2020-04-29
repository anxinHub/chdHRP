/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmPara;

/**
 * @Title. @Description. 系统参数<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmParaMapper extends SqlMapper {

	/**
	 * @Description 系统参数<BR>
	 *              添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addPrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmPara分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<PrmPara>
	 * @throws DataAccessException
	 */
	public List<PrmPara> queryPrmPara(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmPara所有数据
	 * @param entityMap
	 * @return List<PrmPara>
	 * @throws DataAccessException
	 */
	public List<PrmPara> queryPrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmParaByCode
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public PrmPara queryPrmParaByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              删除PrmPara
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deletePrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量删除PrmPara
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              更新PrmPara
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updatePrmPara(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 系统参数<BR>
	 *              批量更新PrmPara
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchPrmPara(List<Map<String, Object>> entityMap) throws DataAccessException;
}
