package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrResearchProjectsResults;


/**
 * 科研项目与成果信息
 * @author Administrator
 *
 */
public interface HrResearchProjectsResultsService {
    /**
     * 增加科研项目与成果信息
     * @param mapVo
     * @return
     */
	String addResearchProjectsResults(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrResearchProjectsResults queryByCodeResearchProjectsResults(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改科研项目与成果信息
	 * @param mapVo
	 * @return
	 */
	String updateResearchProjectsResults(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除科研项目与成果信息
	 * @param listVo
	 */
	String  deleteResearchProjectsResults(List<HrResearchProjectsResults> listVo)throws DataAccessException;
	/**
	 * 查询科研项目与成果信息
	 * @param page
	 * @return
	 */
	String queryResearchProjectsResults(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryResearchProjectsResultsTree(Map<String, Object> mapVo)throws DataAccessException;

}
