package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.TableColumns;
import com.chd.hrp.hr.entity.sc.HrTableColumn;

/**
 * 
 * @ClassName: CreateTablesMapper 
 * @Description: 创建更新表结构的Mapper
 * @author zn 
 * @date 2017年10月21日 下午3:24:58 
 * 
 *
 */

public interface CreateTablesMapper extends SqlMapper{
	
	/**
	 * 创建表
	 * @param tableMap
	 */
	public void createTable(@Param("tableMap") Map<String, List<Object>> tableMap);
	
	/**
	 * 创建主键
	 * @param entityMap
	 */
	public void createPk( Map<String, Object> entityMap);

	/**
	 * 根据表名查询表在库中是否存在，存在返回1，不存在返回0
	 * @param tableName
	 * @return
	 */
	public int findTableCountByTableName(@Param("tableName") String tableName);
	
	/**
	 * 根据表名删除表
	 * @param tableName
	 */
	public void dorpTableByName(@Param("tableName") String tableName);
	
	/**
	 * 根据表名查询库中该表的字段结构等信息
	 * @param tableName
	 * @return
	 */
	public List<TableColumns> findTableEnsembleByTableName(@Param("tableName") String tableName);
	
	/**
	 * 增加字段
	 * @param tableMap
	 */
	public void addTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 删除字段
	 * @param tableMap
	 */
	public void removeTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 修改字段
	 * @param tableMap
	 */
	public void modifyTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 删除主键约束
	 * @param tableMap
	 */
	public void dropKeyTableField(@Param("tab_code") String tab_code);
	
	/**
	 * 根据表名查询库中该表的列
	 * @param tableName
	 * @return
	 */
	public List<String> findTableColumnByTableName(@Param("tableName") String tableName);

	public void createTableStruc(@Param("tableMap") Map<String, List<HrTableColumn>> tableMap);
	public int findTableDateCountByTableName(@Param("tableName") String tableName);
	
}
