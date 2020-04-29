package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrGrowthRecord;
/**
 * 成长记录
 * @author Administrator
 *
 */
public interface HrGrowthRecordMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrGrowthRecord> queryGrowthRecordById(Map<String, Object> entityMap);
    /**
     * 删除成长记录
     * @param entityList
     */
	void deleteGrowthRecord(List<HrGrowthRecord> entityList);
	

}
