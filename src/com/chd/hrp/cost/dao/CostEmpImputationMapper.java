/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostEmpImputation;

/**
 * @Title. @Description. 科室材料归集<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostEmpImputationMapper extends SqlMapper {

	/**
	 * @Description 固定资产归集<BR>
	 *              查询queryCostEmpImputation分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostMaterialDetail>
	 * @throws DataAccessException
	 */
	public List<CostEmpImputation> queryCostEmpImputation(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 固定资产归集<BR>
	 *              查询queryCostEmpImputation所有数据
	 * @param entityMap
	 * @return List<CostMaterialDetail>
	 * @throws DataAccessException
	 */
	public List<CostEmpImputation> queryCostEmpImputation(Map<String, Object> entityMap) throws DataAccessException;

}
