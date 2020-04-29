package com.chd.hrp.sys.dao.notGeneral;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/*动态非预编译SQL，返回一列*/
public interface SysFunUtilMapper extends SqlMapper {

	// 取最大的排序号
	public int querySysMaxSort(Map<String, Object> entityMap);

	// 添加判断编码名称重复
	public int existsSysCodeNameByAdd(Map<String, Object> entityMap);

	// 修改判断编码名称重复
	public int existsSysCodeNameByUpdate(Map<String, Object> entityMap);

	// 删除字典，判断业务表是否使用，调用存储过程
	public String querySysDictDelCheck(Map<String, Object> map) throws DataAccessException;

	// 删除字典，判断业务表是否使用，调用存储过程
	public String queryStoreDictDelCheck(Map<String, Object> map) throws DataAccessException;

	// 执行DDL语句
	public int execDDLSql(@Param("ddl_sql") String ddl_sql) throws DataAccessException;

	public int existsSuperCodeByVouch(Map<String, Object> entityMap);

	public int existsSuperCodeByLeder(Map<String, Object> entityMap);

	public List<Map<String, Object>> querySysDictUseByCode(String dict_code);

}
