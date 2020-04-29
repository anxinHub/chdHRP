package com.chd.hrp.hr.dao.salarymanagement.salaryChange;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrSalaryChangeManageMapper extends SqlMapper{

	//薪资变动管理变动类型下拉框加载
	List<Map<String, Object>> querySalaryChangeTypeOption(
			Map<String, Object> mapVo);

	//查询变动编号
	String querySalaryChangeManageChangecode();

	//薪资管理变动项目下拉加载
	List<Map<String, Object>> querySalaryChangeTypeChangeProjectOption(
			Map<String, Object> mapVo);

	//薪资管理变动项目级联变动后
	List<Map<String, Object>> queryValueaftOption(Map<String, Object> mapVo);

	//薪资变动管理职工下拉加载
	List<Map<String, Object>> querySalaryManageEmpOption(
			Map<String, Object> mapVo);

	//薪资变动管理添加
	int addSalaryManage(Map<String, Object> mapVo);
	
	//添加薪资管理变动项目
	int addSalaryChange(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Map> list);

	//太黏薪资管理工资项目
	int addSalaryProject(@Param("vo")Map<String, Object> mapVo, @Param("list")List<Map> list);

	//薪资变动管理职工添加
	int addSalaryEmp(@Param("vo")Map<String, Object> mapVo, @Param("list")String[] idarr);

	//薪资变动管理查询
	List<Map<String, Object>> querySalaryChangeManage(Map<String, Object> mapVo);
	List<Map<String, Object>> querySalaryChangeManage(Map<String, Object> mapVo, RowBounds rowBounds);

	//删除职工数据
	void deleteSalaryChangeManageCode(@Param("vo")Map<String, Object> mapVo,@Param("list")List<String> listId);
	
	//删除薪资变动管理数据
	int deleteSalaryChangeManage(@Param("vo")Map<String, Object> mapVo,@Param("list")List<String> listId);

	//薪资变动管理修改会显数据
	Map<String, Object> queryUpdateSalaryChangeManage(Map<String, Object> mapVo);

	//审核数据
	int updateSalaryChangeManageSubmit(@Param("vo")Map<String, Object> mapVo,@Param("list")List<String> listId);

	//薪资变动管理修改回显变动项目
	List<Map<String, Object>> queryUpdateSalaryChangeTypeSalaryProject(
			Map<String, Object> mapVo);

	//修改薪资变动管理数据
	int updateSalaryManage(Map<String, Object> mapVo);

	//薪资变动管理修改回显工资数据
	List<Map<String, Object>> queryUpdateSalaryChangeTypeSalaryManageProject(
			Map<String, Object> mapVo);

	//查询数据是否被审核已审核不能修改
	int updateSalaryManageCount(Map<String, Object> mapVo);
}
