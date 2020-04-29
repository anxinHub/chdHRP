package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HrStationKind;

public interface HrStationKindMapper extends SqlMapper{


	void addStationKind(Map<String, Object> entityMap);

	void updateHrStationKind(Map<String, Object> entityMap);

	void deleteBatchStationKind(List<HrStationKind> entityList);
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
	List<HrStationKind> queryStationKindById(Map<String, Object> entityMap);

}
