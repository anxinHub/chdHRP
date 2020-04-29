package com.chd.hrp.hr.dao.salarymanagement.accumulationfund;

import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrProvidentfundratioMapper extends SqlMapper{

	//删除
	void deleteProvidentfundratio(Map<String, Object> mapVo);

	//添加
	int saveProvidentfundratio(Map<String, Object> mapVo);

	//查询
	Map<String, Object> queryProvidentfundratio(Map<String, Object> mapVo);

}
