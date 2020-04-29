package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusIntegral;


/**
 * 个人学术地位积分
 * @author Administrator
 *
 */
public interface HrAcademicStatusIntegralService {
    /**
     * 增加个人学术地位积分
     * @param mapVo
     * @return
     */
	String addAcademicStatusIntegral(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术地位积分
	 * @param listVo
	 */
	String  deleteAcademicStatusIntegral(List<HrAcademicStatusIntegral> listVo)throws DataAccessException;
	/**
	 * 查询个人学术地位积分
	 * @param page
	 * @return
	 */
	String queryAcademicStatusIntegral(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String copyStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询满分按年份
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAcadeStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存最高分
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String savePersonalAcadeStatus(Map<String, Object> entityMap) throws DataAccessException;

}
