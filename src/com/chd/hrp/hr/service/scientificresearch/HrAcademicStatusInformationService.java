package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusInformation;


/**
 * 个人学术地位信息
 * @author Administrator
 *
 */
public interface HrAcademicStatusInformationService {
    /**
     * 增加个人学术地位信息
     * @param mapVo
     * @return
     */
	String addAcademicStatusInformation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAcademicStatusInformation queryByCodeAcademicStatusInformation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术地位信息
	 * @param mapVo
	 * @return
	 */
	String updateAcademicStatusInformation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术地位信息
	 * @param listVo
	 */
	String  deleteAcademicStatusInformation(List<HrAcademicStatusInformation> listVo)throws DataAccessException;
	/**
	 * 查询个人学术地位信息
	 * @param page
	 * @return
	 */
	String queryAcademicStatusInformation(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryAcademicStatusInformationTree(Map<String, Object> mapVo)throws DataAccessException;

}
