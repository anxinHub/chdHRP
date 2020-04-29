package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrAdministrationAbility;
/**
 * 行政能力
 * @author Administrator
 *
 */
public interface HrAdministrationAbilityMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAdministrationAbility> queryAdministrationAbilityById(Map<String, Object> entityMap);
    /**
     * 删除行政能力
     * @param entityList
     */
	void deleteAdministrationAbility(List<HrAdministrationAbility> entityList);
	/**
	 * 查询获奖
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPrize(Map<String, Object> entityMap);
	/**
	 * 删除所有行政能力
	 * @param entityMap
	 */
	void deleteHrAdmin(Map<String, Object> entityMap);
	
	/**
	 * 查询是否重复
	 * @param entityList
	 * @return
	 */
	List<Map<String, Object>> queryHrAdministrationAbilityByEmpId(List<HrAdministrationAbility> entityList);
	/**
	 * 查询获奖情况是否存在
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryAdmin(Map<String, Object> saveMap);
	

}
