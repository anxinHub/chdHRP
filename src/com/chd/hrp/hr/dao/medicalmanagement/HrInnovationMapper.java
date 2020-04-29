package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrInnovation;

public interface HrInnovationMapper  extends SqlMapper{
    /**
     * 删除所有
     * @param entityMap
     */
	void deletehrInnovation(Map<String, Object> entityMap);
    /**
     * 查询重复数据
     * @param hrInnovation
     * @return
     */
	HrInnovation queryByCodeInnovation(HrInnovation hrInnovation);
	/**
	 * 添加
	 * @param hrInnovation
	 * @return
	 */
	int add(HrInnovation hrInnovation);
	/**
	 * 批量删除
	 * @param entityList
	 * @param mapVo 
	 */
	void deleteInnovation(@Param(value="list") List<HrInnovation> entityList, @Param(value="map") Map<String, Object> mapVo);
	/**
	 * 提交
	 * @param hrPlanit
	 */
	void confirmInnovation(HrInnovation hrPlanit);
	/**
	 * 撤回
	 * @param hrPlanit
	 */
	void reConfirmInnovation(HrInnovation hrPlanit);
	List<Map<String, Object>> queryInnovationByPrint(
			Map<String, Object> entityMap);
	//添加删除
	void deleteInnovati(List<HrInnovation> alllistVo);
	void addBatchImport(List<Map<String, Object>> saveList);

}
