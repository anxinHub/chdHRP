/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
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
public interface HrMDTTeamMeetingMapper extends SqlMapper{
    /**
     * 添加MDT团队会议
     * @param entityMap
     * @return
     */
	int addHrMeetMdt(Map<String, Object> entityMap);

	String queryMeetDetail(Map<String, Object> entityMap);

	void deleteHrMDTTeamMeeting(@Param(value="list") List<HrMeetMdt> entityList,@Param(value="map") Map<String, Object> mapVo);

	HrMeetMdt queryByCodeAdd(Map<String, Object> entityMap);

	HrMeetMdt queryByCode(HrMeetMdt hrMeetMdt);
    /**
     * 改变MDT团队会议状态
     * @param entityMap
     * @return
     */
	void changeStateHrMDTTeamMeeting(HrMeetMdt hrMeetMdt);

	void confirmHrMDTTeamMeetingAdd(HrMeetMdt hrMeetMdt);

	HrMeetMdt queryByCodeDate(HrMeetMdt hrMeetMdt);
    /**
     * 打印
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryMDTTeamMeetingByPrint(
			Map<String, Object> entityMap);

	Map<String, Object> queryEmp(Map<String, Object> entityMap);
	


}
