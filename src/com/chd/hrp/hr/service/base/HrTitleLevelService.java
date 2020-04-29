/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.base.HrTitleLevel;

/**
 * 
 * @Description: 职称等级
 * @Table: HR_TITLE_LEVEL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrTitleLevelService {
	/**
	 * 删除等级
	 * 
	 * @param listVo
	 * @return
	 */
	String deleteTitleLevel(List<HrTitleLevel> listVo)throws DataAccessException;

	/**
	 * 增加职称等级
	 * 
	 * @param mapVo
	 * @return
	 */
	String addTitleLevel(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 跳转修改页面查询
	 * 
	 * @param mapVo
	 * @return
	 */
	HrTitleLevel queryByCode(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 修改保存职称等级
	 * 
	 * @param mapVo
	 * @return
	 */
	String updateTitleLevel(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询职称等级
	 * 
	 * @param page
	 * @return
	 */
	String queryTitleLevel(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

}
