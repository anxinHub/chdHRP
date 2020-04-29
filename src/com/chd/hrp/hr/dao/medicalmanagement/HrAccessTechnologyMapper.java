package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;

/**
 * 技术准入
 * @author Administrator
 *
 */
public interface HrAccessTechnologyMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAccessTechnology> queryAccessTechnologyById(Map<String, Object> entityMap);
    /**
     * 删除技术准入
     * @param entityList
     * @param mapVo 
     */
	void deleteAccessTechnology(@Param(value="list")List<?> entityList,@Param(value="map") Map<String, Object> mapVo);
	HrAccessTechnology queryByCodeExec(Map<String, Object> entityMap);
	/**
	 * 撤回
	 * @param hrNursingPromotion
	 */
	void reConfirmHrHrTechRec(HrAccessTechnology hrNursingPromotion);
	/**
	 * 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 */
	HrAccessTechnology queryByCode(HrAccessTechnology hrNursingPromotion);
	/**
	 *添加页 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 */
	HrAccessTechnology queryByCodeAdd(Map<String, Object> entityMap);
	
	/**
	 * 修改开展例数
	 * @param accessTechnology
	 */
	void updateCaseNuM(HrAccessTechnology accessTechnology);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAccessTechnologyByPrint(Map<String, Object> entityMap);


}
