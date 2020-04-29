/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
/**
 * 
 * @Description:
 * 系统结构表
 * @Table:
 * HR_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTableStrucMapper extends SqlMapper{
	
	/**
	 * 查询表及列信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableStruc queryTabColsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据表SQL
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableStruc queryTabSqlsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据表列设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableStruc queryColSetByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询tab_code序列
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabCodeSeq() throws DataAccessException;

	/**
	 * 查询数据(左侧树型菜单)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrTableStrucTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据名称查询数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTableStruc queryByName(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据表名或名称查询数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int queryByCodeOrName(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加系统权限
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int addSysPermData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加系统权限
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int deleteSysPermData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code更新数据表字段
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int updateTabColByTabCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code更新数据表SQL
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int updateTabSqlByTabCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据code更新数据表列设置
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	int updateColSetByTabCode(Map<String, Object> entityMap) throws DataAccessException;

	int updateHrTableStrucIsStop(Map<String, Object> entityMap) throws DataAccessException;

	List<HrTableStruc> queryHrTableStrucExport(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryOldTableStruc(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryOldColumnByTabCode(Map<String, Object> entityMap) throws DataAccessException;

	int copyTableTypeByOld(Map<String, Object> entityMap) throws DataAccessException;

	List<HrTableStruc> queryTabStrucByCodes(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询代码表（字典下拉框）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrFiledTableStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据表名列表查询对应的列
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<HrTableColumn> queryDBTableColByCodes(Map<String, Object> entityMap) throws DataAccessException;

	
	
}
