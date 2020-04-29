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
import com.chd.hrp.cost.entity.CostMedicalTechnologyWork;


public interface CostMedicalTechnologyWorkMapper extends SqlMapper {


	public int addCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;

	public List<CostMedicalTechnologyWork> queryCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<CostMedicalTechnologyWork> queryCostMedicalTechnologyWork(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	public CostMedicalTechnologyWork queryCostMedicalTechnologyWorkByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public int deleteCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public int deleteBatchCostMedicalTechnologyWork(List<Map<String, Object>> list) throws DataAccessException;
	
	public int updateCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
}
