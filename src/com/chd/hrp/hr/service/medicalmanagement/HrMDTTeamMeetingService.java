/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrMeetMdt;

/**
 * 
 * @Description:
 * MDT团队会议记录
 * @Table:
 * HR_MEET_MDT MDT团队会议
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
public interface HrMDTTeamMeetingService {
    /**
     * 增加MDT团队会议记录及明细
     * @param mapVo
     * @return
     */
	String addhrMDTTeamMeeting(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 查询MDT团队会议记录
     * @param mapVo
     * @return
     */
	String queryHrMDTTeamMeeting(Map<String, Object> page) throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrMeetMdt queryByCodeHrMDTTeamMeeting(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 查询MDT团队会议明细
     * @param mapVo
     * @return
     */
	String queryMeetDetail(Map<String, Object> page) throws DataAccessException;
    /**
     * 更新MDT团队会议记录及明细
     * @param mapVo
     * @return
     */
	String updateHrMDTTeamMeeting(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 删除MDT团队会议
     * @param mapVo
     * @return
     */	
	String deleteHrMDTTeamMeeting(List<HrMeetMdt> listVo) throws DataAccessException;
	/**
	 * 查询是否存在
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	HrMeetMdt queryByCode(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 提交
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String confirmHrMDTTeamMeeting(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 撤回
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrMDTTeamMeeting(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 审批
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String auditHrMDTTeamMeeting(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 销审
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String unauditHrMDTTeamMeeting(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 未通过
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String dispassHrMDTTeamMeeting(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 添加页面查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	HrMeetMdt queryByCodeAdd(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 添加页提交
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String confirmHrMDTTeamMeetingAdd(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 添加页撤回
	 * @param hrMeetMdt
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrMDTTeamMeetingAdd(HrMeetMdt hrMeetMdt) throws DataAccessException;
	
	HrMeetMdt queryByCodeDate(HrMeetMdt hrMeetMdt) throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryMDTTeamMeetingByPrint(Map<String, Object> page)throws DataAccessException;
	Map<String, Object> queryEmp(Map<String, Object> mapVo)throws DataAccessException;
	

}
