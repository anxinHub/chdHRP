/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
/**
 * 
 * @Description:
 * 系统结构列名
 * @Table:
 * HR_COL_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrColStrucMapper extends SqlMapper{
	
	/**
	 * 批量删除数据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	int deleteBatchHrColStruc(List<HrColStruc> entityList) throws DataAccessException;
	
	/**
	 * 查询MAP数据
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrColStrucMap(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询实体数据
	 * @param entityMap
	 * @return
	 */
	List<HrColStruc> queryHrColStrucEntity(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询表格要显示的列数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryColumns(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询表的所有列
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<String> queryColCodes(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询表字段类型
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryDataType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询组件（下拉框、文本框等）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHrComType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据表名查列
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<HrColStruc> queryHrColStrucEntityByTabCode(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryHrColStrucByPrint(Map<String, Object> entityMap) throws DataAccessException;

	HrColStruc queryByColCode(HrColStruc entityMap) throws DataAccessException;

	List<Map<String, Object>> queryByTabCode(Map<String, Object> entityMap) throws DataAccessException;

	void deleteTabCode(Map<String, Object> entityMap);

}
