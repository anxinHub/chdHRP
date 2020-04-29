package com.chd.hrp.budg.dao.business.intangible;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface BudgAssCardIntangibleMapper extends SqlMapper{

	public String querynatursCode(Map<String, Object> entityMap);

	public int addBudgAssCardSource(List<Map<String, Object>> detailAddList);

	public Map<String, Object> queruBudgAssCardSource(Map<String, Object> detailMap);

	public int  deleteBudgAssCardSource(Map<String, Object> detailMap);

	public List<Map<String, Object>> queryBudgAssCardSourceBySourceId(Map<String, Object> mapVo);

	public int  deletesoure(List<Map<String, Object>> entityList);
	
	/**
	 * 校验数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 校验资产现状_资金来源 数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExistSource(Map<String, Object> mapVo)throws DataAccessException;
	

	

}
