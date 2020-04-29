package com.chd.hrp.hr.dao.salarymanagement.salaryChange;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrSalaryChangeTypeMapper extends SqlMapper{

	//工资项目下拉加载
	List<Map<String, Object>> queryItemOption(
			Map<String, Object> mapVo);

	//工资项编码下拉加载
	List<Map<String, Object>> queryPlancodeOption(Map<String, Object> mapVo);

	//查询新增时是否存在重复的编码
	int queryAddChangeTypeCount(Map<String, Object> mapVo);

	//薪资变动类型添加
	int addSalaryChangeType(Map<String, Object> mapVo);

	//新增之添加变动项目
	int addChangeProject(@Param("vo")Map<String, Object> mapVo,@Param("list")List<Map> list);

	//新增之添加工资项目
	int addSalaryProject(@Param("vo")Map<String, Object> mapVo,@Param("list")List<Map> list);

	//薪资变动类型查询
	List<Map<String, Object>> querySalaryChangeType(Map<String, Object> mapVo);
	List<Map<String, Object>> querySalaryChangeType(Map<String, Object> mapVo,
			RowBounds rowBounds);

	//删除薪资变动前删除变动项目
	int deleteSalaryChangeTypeChangeProject(@Param("vo") Map<String, Object> mapVo, @Param("list")String [] array);

	//删除薪资变动前删除工资项目
	int deleteSalaryChangeTypeProject(@Param("vo") Map<String, Object> mapVo, @Param("list")String [] array);

	//删除薪资变动类型
	int deleteSalaryChangeType(@Param("vo") Map<String, Object> mapVo, @Param("list")String [] array);

	//修改查询回显数据
	Map<String, Object> queryUpdateSalaryChangeType(Map<String, Object> mapVo);

	//薪资变动类型,变动项目表格回显
	List<Map<String, Object>> querySalaryChangeTypeChangeProject(
			Map<String, Object> mapVo);
	List<Map<String, Object>> querySalaryChangeTypeChangeProject(
			Map<String, Object> mapVo, RowBounds rowBounds);

	//薪资变动类型,工资项目回显
	List<Map<String, Object>> querySalaryChangeTypeSalaryProject(
			Map<String, Object> mapVo);
	List<Map<String, Object>> querySalaryChangeTypeSalaryProject(
			Map<String, Object> mapVo, RowBounds rowBounds);

	//薪资变动类型添加查询是否存在重复的名称
	int queryAddChangeTypeNameCount(Map<String, Object> mapVo);

	//修改薪资变动类型
	int updateSalaryChangeType(Map<String, Object> mapVo);

	//删除变动项目数据
	void deleteChangeProject(Map<String, Object> mapVo);
	void deleteSalaryProject(Map<String, Object> mapVo);
	
}
