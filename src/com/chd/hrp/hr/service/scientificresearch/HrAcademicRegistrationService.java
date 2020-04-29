package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcademicRegistration;


/**
 * 个人学术荣誉登记
 * @author Administrator
 *
 */
public interface HrAcademicRegistrationService {
    /**
     * 增加个人学术荣誉登记
     * @param mapVo
     * @return
     */
	String addAcademicRegistration(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAcademicRegistration queryByCodeAcademicRegistration(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术荣誉登记
	 * @param mapVo
	 * @return
	 */
	String updateAcademicRegistration(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术荣誉登记
	 * @param listVo
	 */
	String  deleteAcademicRegistration(List<HrAcademicRegistration> listVo)throws DataAccessException;
	/**
	 * 查询个人学术荣誉登记
	 * @param page
	 * @return
	 */
	String queryAcademicRegistration(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryAcademicRegistrationTree(Map<String, Object> mapVo)throws DataAccessException;

}
