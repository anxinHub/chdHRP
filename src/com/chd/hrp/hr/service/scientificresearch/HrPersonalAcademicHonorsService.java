package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicHonors;


/**
 * 个人学术荣誉信息
 * @author Administrator
 *
 */
public interface HrPersonalAcademicHonorsService {
    /**
     * 增加个人学术荣誉信息
     * @param mapVo
     * @return
     */
	String addPersonalAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrPersonalAcademicHonors queryByCodePersonalAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术荣誉信息
	 * @param mapVo
	 * @return
	 */
	String updatePersonalAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术荣誉信息
	 * @param listVo
	 */
	String  deletePersonalAcademicHonors(List<HrPersonalAcademicHonors> listVo)throws DataAccessException;
	/**
	 * 查询个人学术荣誉信息
	 * @param page
	 * @return
	 */
	String queryPersonalAcademicHonors(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryPersonalAcademicHonorsTree(Map<String, Object> mapVo)throws DataAccessException;

}
