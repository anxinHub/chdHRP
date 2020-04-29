package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrDissertation;
/**
 * 论文发表
 * @author Administrator
 *
 */
public interface HrDissertationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrDissertation> queryDissertationById(Map<String, Object> entityMap);
    /**
     * 删除论文发表
     * @param entityList
     */
	void deleteDissertation(List<HrDissertation> entityList);
	

}
