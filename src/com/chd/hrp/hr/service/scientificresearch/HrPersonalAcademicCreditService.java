package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicCredit;


/**
 * 个人学术荣誉积分
 * @author Administrator
 *
 */
public interface HrPersonalAcademicCreditService {
    /**
     * 增加个人学术荣誉积分
     * @param mapVo
     * @return
     */
	String addPersonalAcademicCredit(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrPersonalAcademicCredit queryByCodePersonalAcademicCredit(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术荣誉积分
	 * @param mapVo
	 * @return
	 */
	String updatePersonalAcademicCredit(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 保存个人学术荣誉积分
	 * @param mapVo
	 * @return
	 */
	String savePersonalAcademicCredit(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术荣誉积分
	 * @param listVo
	 */
	String  deletePersonalAcademicCredit(List<HrPersonalAcademicCredit> listVo)throws DataAccessException;
	/**
	 * 查询个人学术荣誉积分
	 * @param page
	 * @return
	 */
	String queryPersonalAcademicCredit(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryPersonalAcademicCreditTree(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询学术荣誉
	 * @param mapVo 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHonorsName(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String copyAcademicCredit(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 主页面跳转查询满分标准
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryAcadeHonor(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 日期切换查询满分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAcadeHonorValue(Map<String, Object> entityMap)throws DataAccessException;
	String savePersonalAcadeHonor(Map<String, Object> entityMap) throws DataAccessException;
}
