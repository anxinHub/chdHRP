package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdApplication;

public interface HrFurstdApplicationService {
   /**
    * 添加进修申请
    * @param mapVo
    * @return
    */
	String addFurstdApplication(Map<String, Object> mapVo)throws DataAccessException;
     /**
      * 查询进修申请
      * @param mapVo
      * @return
      */
    HrFurstdApplication queryByCodeFurstdApplication(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 更新进修申请
     * @param mapVo
     * @return
     */
	String updateFurstdApplication(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除进修申请
	 * @param listVo
	 * @return
	 */
	String deleteFurstdApplication(List<HrFurstdApplication> listVo)throws DataAccessException;
	/**
	 * 查询进修申请
	 * @param page
	 * @return
	 */
	String queryFurstdApplication(Map<String, Object> page)throws DataAccessException;

	/**
	 * 提交
	 * @param hrNursingPromotion
	 * @return
	 */
	String confirmHrFurstdApplication(HrFurstdApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 撤回
	 * @param hrNursingPromotion
	 * @return
	 */
	String reConfirmHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 审核
	 * @param hrNursingPromotion
	 * @return
	 */
	String auditHrFurstdApplication(HrFurstdApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 销审
	 * @param hrNursingPromotion
	 * @return
	 */
	String unauditHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 未通过
	 * @param hrNursingPromotion
	 * @return
	 */
	String dispassHrHrFurstdApplication(HrFurstdApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 查询人员信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosEmp(Map<String, Object> mapVo)throws DataAccessException;
	   /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryFurstdApplicationByPrint(Map<String, Object> page)throws DataAccessException;
}
