package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrHospitalOS;
/**
 * 院外学习记录
 * @author Administrator
 *
 */
public interface HrHospitalOSMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrHospitalOS> queryHospitalOSById(Map<String, Object> entityMap);
    /**
     * 删除院外学习记录
     * @param entityList
     */
	void deleteHospitalOS(List<HrHospitalOS> entityList);
	

}
