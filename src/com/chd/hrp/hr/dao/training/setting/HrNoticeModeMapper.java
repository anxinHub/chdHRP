package com.chd.hrp.hr.dao.training.setting;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrNoticeMode;

public interface HrNoticeModeMapper extends SqlMapper{
	   /**
	    * 删除
	    * @param entityList
	    */
		void deleteNoticeMode(List<HrNoticeMode> entityList);
	    /**
	     * 添加查询
	     * @param entityMap
	     * @return
	     */
	    List<HrNoticeMode> queryNoticeModeById(Map<String, Object> entityMap);
		List<HrNoticeMode> queryNoticeModeByIdName(Map<String, Object> entityMap);
		int queryUseNoticeMode(HrNoticeMode hrNoticeMode);
		List<HrNoticeMode> queryByCodeName(Map<String, Object> saveMap);
		
}
