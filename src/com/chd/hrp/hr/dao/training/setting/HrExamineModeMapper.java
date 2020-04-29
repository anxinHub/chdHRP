package com.chd.hrp.hr.dao.training.setting;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrExamineMode;

public interface HrExamineModeMapper extends SqlMapper{
	   /**
	    * 删除
	    * @param entityList
	    */
		void deleteExamineMode(List<HrExamineMode> entityList);
	    /**
	     * 添加查询
	     * @param entityMap
	     * @return
	     */
	    List<HrExamineMode> queryExamineModeById(Map<String, Object> entityMap);
		List<HrExamineMode> queryExamineModeByIdName(Map<String, Object> entityMap);
		/**
		 *  查询是否使用
		 * @param hrExamineMode
		 * @return
		 */
		int queryUseExamineMode(HrExamineMode hrExamineMode);
		List<HrExamineMode> queryByCodeName(Map<String, Object> saveMap);
		

}
