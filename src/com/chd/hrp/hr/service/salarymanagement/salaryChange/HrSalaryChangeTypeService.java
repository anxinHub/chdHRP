package com.chd.hrp.hr.service.salarymanagement.salaryChange;

import java.util.List;
import java.util.Map;

public interface HrSalaryChangeTypeService {

	//工资项目下拉加载
	List<Map<String,Object>> queryItemOption(Map<String, Object> mapVo);

	//工资项编码下拉加载
	List<Map<String,Object>> queryPlancodeOption(Map<String, Object> mapVo);

	//薪资变动类型添加
	String addSalaryChangeType(Map<String, Object> mapVo);

	//薪资变动类型查询
	String querySalaryChangeType(Map<String, Object> mapVo);

	//薪资变动类型删除
	String deleteSalaryChangeType(Map<String, Object> mapVo);

	//薪资修改回显数据
	Map<String, Object> queryUpdateSalaryChangeType(Map<String, Object> mapVo);

	//薪资变动类型,变动项目表格回显
	String querySalaryChangeTypeChangeProject(Map<String, Object> mapVo);

	//薪资变动类型,工资项目回显
	String querySalaryChangeTypeSalaryProject(Map<String, Object> page);

	//薪资变动类型修改
	String updateSalaryChangeType(Map<String, Object> mapVo);

}
