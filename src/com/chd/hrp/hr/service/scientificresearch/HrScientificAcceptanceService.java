package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrScientificAcceptance;


/**
 * 科研项目验收
 * @author Administrator
 *
 */
public interface HrScientificAcceptanceService {
    /**
     * 增加科研项目验收
     * @param mapVo
     * @return
     */
	String addScientificAcceptance(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrScientificAcceptance queryByCodeScientificAcceptance(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改科研项目验收
	 * @param mapVo
	 * @return
	 */
	String updateScientificAcceptance(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除科研项目验收
	 * @param listVo
	 */
	String  deleteScientificAcceptance(List<HrScientificAcceptance> listVo)throws DataAccessException;
	/**
	 * 查询科研项目验收
	 * @param page
	 * @return
	 */
	String queryScientificAcceptance(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryScientificAcceptanceTree(Map<String, Object> mapVo)throws DataAccessException;

}
