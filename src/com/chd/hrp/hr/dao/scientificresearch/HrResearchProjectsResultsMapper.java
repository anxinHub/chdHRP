package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchProjectsResults;
/**
 * 科研项目与成果信息
 * @author Administrator
 *
 */
public interface HrResearchProjectsResultsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrResearchProjectsResults> queryResearchProjectsResultsById(Map<String, Object> entityMap);
    /**
     * 删除科研项目与成果信息
     * @param entityList
     */
	void deleteResearchProjectsResults(List<HrResearchProjectsResults> entityList);
	

}
