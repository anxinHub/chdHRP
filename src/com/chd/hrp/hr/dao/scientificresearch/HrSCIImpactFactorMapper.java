package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrSCIImpactFactor;
/**
 * SCI论文影响因子
 * @author Administrator
 *
 */
public interface HrSCIImpactFactorMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrSCIImpactFactor> querySCIImpactFactorById(Map<String, Object> entityMap);
    /**
     * 删除SCI论文影响因子
     * @param entityList
     */
	void deleteSCIImpactFactor(List<HrSCIImpactFactor> entityList);
	

}
