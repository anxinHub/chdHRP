package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrRegistrationStatus;


/**
 *个人学术地位登记
 * @author Administrator
 *
 */
public interface HrRegistrationStatusService {
    /**
     * 增加个人学术荣誉申请
     * @param mapVo
     * @return
     */
	String addRegistrationStatus(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrRegistrationStatus queryByCodeRegistrationStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术荣誉申请
	 * @param mapVo
	 * @return
	 */
	String updateRegistrationStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术荣誉申请
	 * @param listVo
	 */
	String  deleteRegistrationStatus(List<HrRegistrationStatus> listVo)throws DataAccessException;
	/**
	 * 查询个人学术荣誉申请
	 * @param page
	 * @return
	 */
	String queryRegistrationStatus(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryRegistrationStatusTree(Map<String, Object> mapVo)throws DataAccessException;

}
