package com.chd.hrp.budg.service.business.intangible;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgAssCardIntangibleService extends SqlService{

	public String queryBudgAssCardSourceBySourceId(Map<String, Object> mapVo);
	
	/**
	 * 校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 校验资产现状_资金来源 数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExistSource(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 批量添加 资产现状——资金来源数据
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String addBudgAssCardSource(List<Map<String, Object>> addList) throws DataAccessException;

}
