package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificRegister;
/**
 * 专利登记
 * @author Administrator
 *
 */
public interface HrScientificRegisterMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrScientificRegister> queryScientificRegisterById(Map<String, Object> entityMap);
    /**
     * 删除专利登记
     * @param entityList
     */
	void deleteScientificRegister(List<HrScientificRegister> entityList);
	

}
