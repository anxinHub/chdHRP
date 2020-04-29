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

import com.chd.hrp.hr.entity.sysstruc.HrColStruc;

/**
 * 
 * @Description:
 * 系统结构列名
 * @Colle:
 * HR_COL_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrColStrucService {
	
	/**
	 * 添加or更新
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addOrUpdateHrColStruc(Map<String,Object> entityMap) throws DataAccessException;	
	
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchHrColStruc(String tab_code,List<HrColStruc> entityList) throws DataAccessException;
	
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrColStruc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据code查询数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public HrColStruc queryHrColStrucByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHrColStrucByPrint(Map<String, Object> entityMap) throws DataAccessException;
}
