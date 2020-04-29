package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HrCompetencyTarget;

public interface HrCompetencyTargetMapper extends SqlMapper{
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
	List<HrCompetencyTarget> queryCompetencyTargetById(Map<String, Object> entityMap);
    /**
     * 删除能力素质指标
     * @param entityList
     */
	void deleteBatchCompetencyTarget(List<HrCompetencyTarget> entityList);

}
