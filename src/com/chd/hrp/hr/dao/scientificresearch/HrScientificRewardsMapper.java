package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificRewards;
/**
 * 科研成果奖励
 * @author Administrator
 *
 */
public interface HrScientificRewardsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrScientificRewards> queryScientificRewardsById(Map<String, Object> entityMap);
    /**
     * 删除科研成果奖励
     * @param entityList
     */
	void deleteScientificRewards(List<HrScientificRewards> entityList);
	

}
