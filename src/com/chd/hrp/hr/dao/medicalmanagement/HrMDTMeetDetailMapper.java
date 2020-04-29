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
 * HR_MEET_MDT MDT MDT团队会议明细
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
public interface HrMDTMeetDetailMapper extends SqlMapper{
    /**
     * 批量删除
     * @param entityList
     * @param mapVo 
     */
	void deleteHrMDTMeetDetail(@Param(value="list") List<HrMeetMdt> entityList, @Param(value="map") Map<String, Object> mapVo);

}
