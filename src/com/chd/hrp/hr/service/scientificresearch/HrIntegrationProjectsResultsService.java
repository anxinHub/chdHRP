package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrIntegrationProjectsResults;


/**
 * 科研项目与成果积分
 * @author Administrator
 *
 */
public interface HrIntegrationProjectsResultsService {
    /**
     * 增加科研项目与成果积分
     * @param mapVo
     * @return
     */
	String addIntegrationProjectsResults(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除科研项目与成果积分
	 * @param listVo
	 */
	String  deleteIntegrationProjectsResults(List<HrIntegrationProjectsResults> listVo)throws DataAccessException;
	/**
	 * 查询科研项目与成果积分
	 * @param page
	 * @return
	 */
	String queryIntegrationProjectsResults(Map<String, Object> page)throws DataAccessException;
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String copyIntegration(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询满分按年份
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryProjByYear(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存满分
	 * @param entityMap
	 * @return
	 */
	String savePersonalAcadeProj(Map<String, Object> entityMap) throws DataAccessException;

}
