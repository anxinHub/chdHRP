package com.chd.hrp.cost.dao.analysis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface CostAnalysisProcMapper extends SqlMapper{
	/**
	 * @Description 生成成本分析报表<BR>
	 *              查询带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws 错误信息
	 */
	public List<?> saveCostAnalysisProc(Map<String, Object> entityMap) throws DataAccessException;
	
}
