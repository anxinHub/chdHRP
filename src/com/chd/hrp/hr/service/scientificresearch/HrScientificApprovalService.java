package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrScientificApproval;


/**
 * 科研项目立项
 * @author Administrator
 *
 */
public interface HrScientificApprovalService {
    /**
     * 增加科研项目立项
     * @param mapVo
     * @return
     */
	String addScientificApproval(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrScientificApproval queryByCodeScientificApproval(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改科研项目立项
	 * @param mapVo
	 * @return
	 */
	String updateScientificApproval(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除科研项目立项
	 * @param listVo
	 */
	String  deleteScientificApproval(List<HrScientificApproval> listVo)throws DataAccessException;
	/**
	 * 查询科研项目立项
	 * @param page
	 * @return
	 */
	String queryScientificApproval(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryScientificApprovalTree(Map<String, Object> mapVo)throws DataAccessException;

}
