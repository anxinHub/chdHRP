package com.chd.hrp.ass.dao.repair.assRepRule;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssRepRuleMapper extends SqlMapper {

	public List<Map<String, Object>> queryRepTeamUser(Map<String, Object> mapVo);

	public int saveRepRule(@Param(value="map")Map<String, Object> mapVo, @Param(value="list")List<String> list);
	/**
	 * 资产卡片tree
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAsscardTree(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 资产分了tree
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssTypeTree(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资产分类tree
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAssFaultTypeTree(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryTreeDataByUserId(Map<String, Object> mapVo) throws DataAccessException;

	public int deleteRepRule(Map<String, Object> mapVo)  throws DataAccessException;

}
