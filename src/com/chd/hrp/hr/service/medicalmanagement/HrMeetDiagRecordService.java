package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagRecord;

public interface HrMeetDiagRecordService {
     /**
      * 添加大会诊记录
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String addMeetDiagRecord(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除大会诊记录
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String deleteMeetDiagRecord(List<HrMeetDiagRecord> listVo)throws DataAccessException;
	/**
	 * 查询大会诊记录
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryMeetDiagRecord(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询大会诊
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryMeetDiagApp(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交查询
	 * @param hrMeetDiagRecord
	 * @return
	 * @throws DataAccessException
	 *//*
	HrMeetDiagRecord queryByCode(HrMeetDiagRecord hrMeetDiagRecord)throws DataAccessException;*/
	/**
	 * 提交
	 * @param hrMeetDiagRecord
	 * @return
	 * @throws DataAccessException
	 */
	String confirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord)throws DataAccessException;
	/**
	 * 撤回
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryRecordByPrint(Map<String, Object> page)throws DataAccessException;
}
