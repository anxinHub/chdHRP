/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;

/**
 * 
 * @Description:
 * 数据表构建
 * @Table:
 * HR_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTabStrucService {
	/**
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addHrTabStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateHrTabStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrTabStruc(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrTabStruc(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrTabStruc queryHrTabStrucByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询数据(左侧树型菜单)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabStrucTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询tab_code序列
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrTabCodeSeq() throws DataAccessException;
}
