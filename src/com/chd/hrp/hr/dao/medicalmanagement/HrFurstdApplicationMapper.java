package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdApplication;

public interface HrFurstdApplicationMapper  extends SqlMapper{
    /**
     * 删除
     * @param entityList
     * @param mapVo 
     */
	void deleteFurstdApplication(@Param(value="list") List<HrFurstdApplication> entityList,@Param(value="map") Map<String, Object> mapVo);
    /**
     * 提交
     * @param hrNursingPromotion
     */
	void confirmHrFurstdApplication(HrFurstdApplication hrNursingPromotion);
	/**
	 * 查询人员信息
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryHosEmp(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryFurstdApplicationByPrint(Map<String, Object> entityMap);

}
