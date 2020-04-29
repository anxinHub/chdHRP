package com.chd.hrp.cost.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.report.RepDefineDict;
import com.chd.hrp.acc.entity.report.RepInstance;
import com.chd.hrp.cost.entity.report.CostRepDefineDict;
import com.chd.hrp.cost.entity.report.CostRepInstance;

public interface CostSuperReportManagerMapper extends SqlMapper{
		
	 
	//报表管理页面，查询报表的所有实例 
	public List<CostRepInstance> querySuperReportInstanceList(Map<String, Object> map) throws DataAccessException;
	
	//报表管理页面，删除报表实例 
	public int deleteBatchSuperReportInstance(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("report_code") String report_code,@Param("mod_code") String mod_code,@Param("list") List<Map<String, String>> list)throws DataAccessException;
	
	//报表字典页面，查询所有报表字典
	public List<Map<String,Object>> querySuperReportDictList(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//报表字典页面，添加报表字典
	public int insertSuperReportDict(Map<String, Object> map)throws DataAccessException;
	
	//报表字典页面，修改报表字典
	public int updateSuperReport(Map<String, Object> map)throws DataAccessException;
	
	//报表字典页面，删除报表字典检查字典是否被使用
	public List<String> querySuperReportDictByUse(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("list") List<String> list) throws DataAccessException;
	
	//报表字典页面，删除报表字典
	public int deleteBatchSuperReportDict(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("list") List<String> list)throws DataAccessException;
	
	//报表管理页面，根据字典编码查询报表字典
	public CostRepDefineDict querySuperReportDictByCode(Map<String, Object> map) throws DataAccessException;

	//报表字典页面，验证字典SQL
	public List<Map<String,Object>> querySuperReportDictSqlValidate(@Param("dict_sql") String dict_sql) throws DataAccessException;

	//查询报表元素SQL
	public String querySuperReportEleSql(@Param("group_id") String group_id,@Param("hos_id") String hos_id,@Param("copy_code") String copy_code,@Param("ele_code") String ele_code) throws DataAccessException;
	
	//报表元素页面，添加报表元素  -->
	public int insertSuperReportEle(Map<String, Object> map)throws DataAccessException;

	//报表元素页面，添加报表元素参数  -->
	public int insertSuperReportElePara(List<Map<String, Object>> list)throws DataAccessException;
	
	//报表元素页面，删除报表元素  -->
	public int deleteSuperReportEle(Map<String, Object> map)throws DataAccessException;
	
	//报表元素页面，删除报表元素参数  -->
	public int deleteSuperReportElePara(Map<String, Object> map)throws DataAccessException;
	
	//报表元素页面，修改报表元素  -->
	public int updateSuperReportEle(Map<String, Object> map)throws DataAccessException;
	
	//根据报表元素删除存储过程函数视图
	public String deleteSuperVouchByProcFunView(Map<String,Object> map)throws DataAccessException;
	
	//重新加载系统存储过程并修改内置数据对应大数据对象（报表元素）  -->
	public int updateSuperReportEleSql(Map<String, Object> map)throws DataAccessException;
	
	//重新加载系统存储过程并修改内置数据对应大数据对象（数据集）  -->
	public int updateSuperReportDsSql(Map<String, Object> map)throws DataAccessException;

}
