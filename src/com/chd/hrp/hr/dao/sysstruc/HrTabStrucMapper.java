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
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
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
 

public interface HrTabStrucMapper extends SqlMapper{
	
	/**
	 * 查询表及列信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTabStruc queryTabColsByCode(Map<String, Object> entityMap) throws DataAccessException;
	
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
	List<Map<String, Object>> queryHrTabStrucTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据名称查询数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTabStruc queryByName(Map<String, Object> entityMap) throws DataAccessException;
	
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

	
	
}
