package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagApplication;

public interface HrMeetDiagApplicationService {
   /**
    * 添加全院大会诊
    * @param mapVo
    * @return
    */
	String addMeetDiagApplication(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 查询全院大会诊
    * @param mapVo
    * @return
    */
   HrMeetDiagApplication queryByCodeMeetDiagApplication(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 更新全院大会诊
    * @param mapVo
    * @return
    */
   String updateMeetDiagApplication(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除全院大会诊
     * @param listVo
     * @return
     */
   String deleteMeetDiagApplication(List<HrMeetDiagApplication> listVo)throws DataAccessException;
   /**
    * 查询
    * @param page
    * @return
    */
	String queryMeetDiagApplication(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交查询
	 * @param hrNursingPromotion
	 * @return
	 */
   HrMeetDiagApplication queryByCode(HrMeetDiagApplication hrMeetDiagApplication)throws DataAccessException;
   /**
    * 提交
    * @param hrMeetDiagApplication
    * @return
    */
	String confirmMeetDiag(HrMeetDiagApplication hrMeetDiagApplication)throws DataAccessException;
	/**
	 * 撤回
	 * @param hrNursingPromotion
	 * @return
	 */
    String reConfirmMeetDiag(HrMeetDiagApplication hrNursingPromotion)throws DataAccessException;
    /**
     * 审核
     * @param hrNursingPromotion
     * @return
     */
	String auditMeetDiag(HrMeetDiagApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 销审
	 * @param hrNursingPromotion
	 * @return
	 */
	String unauditMeetDiag(HrMeetDiagApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 未通过
	 * @param hrNursingPromotion
	 * @return
	 */
	String dispassMeetDiag(HrMeetDiagApplication hrNursingPromotion)throws DataAccessException;
	/**
	 * 添加页提交查询
	 * @param mapVo
	 * @return
	 */
	HrMeetDiagApplication queryByCodeAdd(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 添加页提交
	 * @param mapVo
	 * @return
	 */
	String confirmMeetDiagAdd(HrMeetDiagApplication meetDiagApplication)throws DataAccessException;
	/**
	 * 添加页撤回
	 * @param meetDiagApplication
	 * @return
	 */
	String reConfirmMeetDiagAdd(HrMeetDiagApplication meetDiagApplication)throws DataAccessException;
	/**
	 * 查询历史记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryHistroy(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询明细数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryMeetDetail(Map<String, Object> page)throws DataAccessException;
	/**
 * 打印
 * @param page
 * @return
 * @throws DataAccessException
 */
List<Map<String,Object>> queryMeetDiagAppByPrint(Map<String, Object> page)throws DataAccessException;
}
