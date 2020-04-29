package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrOfficialHoliday;

/**
 * 法定节假日设置
 * @author Administrator
 *
 */
public interface HrOfficialHolidayMapper extends SqlMapper{
    /**
     * 添加查询数据重复
     * @param entityMap
     * @return
     */
	List<HrOfficialHoliday> queryOfficialHolidayById(Map<String, Object> entityMap);
    /**
     * 删除法定节假日
     * @param entityList
     */
	void deleteOfficialHoliday(List<HrOfficialHoliday> entityList);
	/**
	 * 通过集团、医院、时间区域查非工作日
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryOfficialHolidayByDateDrea(Map<String, Object> map);

}
