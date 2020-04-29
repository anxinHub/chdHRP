package com.chd.hrp.hr.dao.record;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.record.HosEmpKind;

public interface HosEmpKindMapper extends SqlMapper{

	void deleteBatchHosEmpKind(List<HosEmpKind> entityList);
    /**
     * 增加查询
     * @param entityMap
     * @return
     */
	List<HosEmpKind> queryEmpKindById(Map<String, Object> entityMap);
	
	Map<String, Object> queryEmpKindByName(Map<String, Object> entityMap);
	
	/**
	 * 查询职工分类（没有停用的）
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryEmpKind(Map<String, Object> map);

}
