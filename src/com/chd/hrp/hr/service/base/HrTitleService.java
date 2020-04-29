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
import com.chd.hrp.hr.entity.base.HrTitle;

/**
 * 
 * @Description: 职称信息
 * @Table: HR_TITLE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrTitleService {
	/**
	 * 删除职称信息
	 * 
	 * @param listVo
	 * @return
	 */
	String deleteBatchTitle(List<HrTitle> listVo)throws DataAccessException;

	/**
	 * 左侧树形
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryTitleInfoTree(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询上级部门
	 * 
	 * @param mapVo
	 * @return
	 */
	String querySupCode(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 添加职称信息
	 * 
	 * @param mapVo
	 * @return
	 */
	String addHrTitle(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 修改跳转查询
	 * 
	 * @param mapVo
	 * @return
	 */
	HrTitle queryByCode(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 更新职称信息
	 * 
	 * @param mapVo
	 * @return
	 */
	String updateHrTitle(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询所有
	 * 
	 * @param page
	 * @return
	 */
	String queryHrTitle(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

}
