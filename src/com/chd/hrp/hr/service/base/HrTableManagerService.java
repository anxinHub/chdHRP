package com.chd.hrp.hr.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrTableColumn;

/**
 * 
 * @ClassName: HrTableManagerService 
 * @Description: 数据表操作 
 * @author zn 
 * @date 2017年11月13日 上午10:33:10 
 * 
 *
 */
public interface HrTableManagerService {

	/**
	 * 根据map结构创建表
	 * 
	 * @param newTableMap
	 */
	void createTableByMap(Map<String, List<Object>> newTableMap) throws DataAccessException;
	
	/**
	 * 根据表名删除表
	 * 
	 * @param tableName 表名
	 */
	void dorpTableByName(String tableName) throws DataAccessException;
	
	/**
	 * 创建或修改表结构
	 * 
	 * @param entityMap
	 */
	void createOrModifyTableConstruct(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 创建表结构
	 * @param newTableMap
	 * @throws DataAccessException
	 */
	void createTableStruc(Map<String, List<HrTableColumn>> newTableMap) throws DataAccessException;
	
	/**
	 * 修改表结构
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void alterTableStruc(Map<String, Object> entityMap) throws DataAccessException;
	
}
