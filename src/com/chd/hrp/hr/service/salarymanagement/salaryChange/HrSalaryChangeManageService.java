package com.chd.hrp.hr.service.salarymanagement.salaryChange;

import java.util.List;
import java.util.Map;

public interface HrSalaryChangeManageService {

	//变动类型下拉框加载
	List<Map<String, Object>> querySalaryChangeTypeOption(Map<String, Object> mapVo);

	//查询变动编号
	String querySalaryChangeManageChangecode();

	//薪资管理变动项目下拉加载
	List<Map<String, Object>> querySalaryChangeTypeChangeProjectOption(Map<String, Object> mapVo);

	//薪资管理变动项目变动后级联
	List<Map<String, Object>> queryValueaftOption(Map<String, Object> mapVo);

	//职工名称下拉加载
	List<Map<String, Object>> querySalaryManageEmpOption(Map<String, Object> mapVo);

	//薪资管理添加
	String addSalaryManage(Map<String, Object> mapVo);

	//薪资变动管理查询
	String querySalaryChangeManage(Map<String, Object> mapVo);

	//薪资变动管理删除
	String deleteSalaryChangeManage(Map<String, Object> page);

	//修改数据回显
	Map<String, Object> queryUpdateSalaryChangeManage(Map<String, Object> mapVo);

	//审核数据
	String updateSalaryChangeManageSubmit(Map<String, Object> mapVo);

	//薪资变动管理修改查询变动项目
	String queryUpdateSalaryChangeTypeSalaryProject(Map<String, Object> mapVo);

	//薪资变动管理修改
	String updateSalaryManage(Map<String, Object> mapVo);

	//薪资变动管理,修改,回显工资项目数据
	String queryUpdateSalaryChangeTypeSalaryManageProject(
			Map<String, Object> mapVo);
}
