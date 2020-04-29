package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificAcceptance;
/**
 * 科研项目验收
 * @author Administrator
 *
 */
public interface HrScientificAcceptanceMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrScientificAcceptance> queryScientificAcceptanceById(Map<String, Object> entityMap);
    /**
     * 删除科研项目验收
     * @param entityList
     */
	void deleteScientificAcceptance(List<HrScientificAcceptance> entityList);
	

}
