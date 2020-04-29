package com.chd.hrp.hr.dao.organize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HrCompetencyModel;

public interface HrCompetencyModelMapper extends SqlMapper {
	/**
	 * 添加查询数据是否重复
	 * 
	 * @param entityMap
	 * @return
	 */
	List<HrCompetencyModel> queryCompetencyModelById(Map<String, Object> entityMap);

	/**
	 * 删除能力素质模型
	 * 
	 * @param entityList
	 */
	void deleteBatchCompetencyModel(List<HrCompetencyModel> entityList);

}
