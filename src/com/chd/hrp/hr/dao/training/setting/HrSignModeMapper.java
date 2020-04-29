package com.chd.hrp.hr.dao.training.setting;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrSignMode;

public interface HrSignModeMapper extends SqlMapper{
	   /**
	    * 删除
	    * @param entityList
	    */
		void deleteSignMode(List<HrSignMode> entityList);
	    /**
	     * 添加查询
	     * @param entityMap
	     * @return
	     */
	    List<HrSignMode> querySignModeById(Map<String, Object> entityMap);
		List<HrSignMode> querySignModeByIdName(Map<String, Object> entityMap);
		int queryUseSignMode(HrSignMode hrSignMode);
		List<HrSignMode> queryByCodeName(Map<String, Object> saveMap);
		

}
