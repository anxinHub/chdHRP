package com.chd.hrp.ass.service.repair.assRepRule;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssRepRuleService extends SqlService {

	public String queryRepTeamUser(Map<String, Object> mapVo) throws DataAccessException;

	public String saveRepRule(Map<String, Object> mapVo);
	/**
	 * 资产卡片的tree
	 * @param mapVo
	 * @return
	 */
	public String queryAsscardTree(Map<String, Object> mapVo);
	/**
	 * 资产分类tree
	 * @param mapVo
	 * @return
	 */
	public String queryAssTypeTree(Map<String, Object> mapVo);

	public String queryAssFaultTypeTree(Map<String, Object> mapVo);

	public String queryTreeDataByUserId(Map<String, Object> mapVo);

}
