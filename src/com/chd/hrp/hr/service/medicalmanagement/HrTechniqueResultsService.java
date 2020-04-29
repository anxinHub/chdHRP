package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrTechniqueResults;





/**
 * 技术管理(结果查询)
 * @author Administrator
 *
 */
public interface HrTechniqueResultsService {
    /**
     * 增加技术管理(结果查询)
     * @param mapVo
     * @return
     */
	String addTechniqueResults(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrTechniqueResults queryByCodeTechniqueResults(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改技术管理(结果查询)
	 * @param mapVo
	 * @return
	 */
	String updateTechniqueResults(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除技术管理(结果查询)
	 * @param listVo
	 */
	String  deleteTechniqueResults(List<HrTechniqueResults> listVo)throws DataAccessException;
	/**
	 * 查询技术管理(结果查询)
	 * @param page
	 * @return
	 */
	String queryTechniqueResults(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryTechniqueResultsTree(Map<String, Object> mapVo)throws DataAccessException;

}
