package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrTechniqueResults;

/**
 * 技术管理(结果查询)
 * @author Administrator
 *
 */
public interface HrTechniqueResultsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrTechniqueResults> queryTechniqueResultsById(Map<String, Object> entityMap);
    /**
     * 删除技术管理(结果查询)
     * @param entityList
     */
	void deleteTechniqueResults(List<HrTechniqueResults> entityList);
	

}
