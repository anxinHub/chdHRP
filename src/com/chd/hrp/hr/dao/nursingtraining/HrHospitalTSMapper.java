package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrHospitalTS;
/**
 * 院内学习记录
 * @author Administrator
 *
 */
public interface HrHospitalTSMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrHospitalTS> queryHospitalTSById(Map<String, Object> entityMap);
    /**
     * 删除院内学习记录
     * @param entityList
     */
	void deleteHospitalTS(List<HrHospitalTS> entityList);
	

}
