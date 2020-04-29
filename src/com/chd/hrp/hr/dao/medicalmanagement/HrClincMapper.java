package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrClinc;

public interface HrClincMapper extends SqlMapper{
    /**
     * 查询重复
     * @param hrClinc
     * @return
     */
	HrClinc queryByCodeClinc(HrClinc hrClinc);
	/**
	 * 添加
	 * @param hrClinc
	 * @return
	 */
	int add(HrClinc hrClinc);
	/**
	 * 删除勾选
	 * @param entityList
	 * @param mapVo 
	 */
	void deleteClinc(@Param(value="list") List<HrClinc> entityList, @Param(value="map") Map<String, Object> mapVo);
	/**
	 * 提交
	 * @param hrPlanit
	 */
	void confirmClinc(HrClinc hrPlanit);
	/**
	 * 撤回
	 * @param hrPlanit
	 */
	void reConfirmClinc(HrClinc hrPlanit);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryClincByPrint(Map<String, Object> entityMap);
	void addBatchClinc(@Param(value="list") List<HrClinc> alllistVo,@Param(value="map") Map<String, Object> entityMap);

}
