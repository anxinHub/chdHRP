package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HrStationSalary;

public interface HrStationSalaryMapper extends SqlMapper{
    /**
     * 增加数据查询
     * @param entityMap
     * @return
     */
	List<HrStationSalary> queryStationSalaryById(Map<String, Object> entityMap);
    /**
     * 删除岗位薪资标准维护
     * @param entityList
     */
	void deleteBatchStationSalary(List<HrStationSalary> entityList);

}
