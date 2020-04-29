package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrAcadeHonorDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicHonors;


/**
 * 个人学术荣誉申请
 * @author Administrator
 *
 */
public interface HrAcademicHonorsService {
    /**
     * 增加个人学术荣誉申请
     * @param mapVo
     * @return
     */
	String addAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAcademicHonors queryByCodeAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术荣誉申请
	 * @param mapVo
	 * @return
	 */
	String updateAcademicHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术荣誉申请
	 * @param listVo
	 */
	String  deleteAcademicHonors(List<HrAcademicHonors> listVo)throws DataAccessException;
	/**
	 * 查询个人学术荣誉申请
	 * @param page
	 * @return
	 */
	String queryAcademicHonors(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询学术荣誉
	 * @param mapVo
	 * @return
	 */
	String queryHonor(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询学术荣誉明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrAcadeHonorDetail(Map<String, Object> page)throws DataAccessException;
	/**
	 * 删除是查询明细
	 * @param hrAcademicHonors
	 * @return
	 * @throws DataAccessException
	 */
	List<HrAcadeHonorDetail> queryHrDetailByCode(HrAcademicHonors hrAcademicHonors)throws DataAccessException;
	/**
	 * 提交
	 * @param hrAcademicHonors
	 * @return
	 * @throws DataAccessException
	 */
	String confirmAcademicHonors(HrAcademicHonors hrAcademicHonors)throws DataAccessException;
	/**
	 * 取消提交
	 * @param hrAcademicHonors
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmAcademicHonors(HrAcademicHonors hrAcademicHonors)throws DataAccessException;
	/**
	 * 审核
	 * @param hrAcademicHonors
	 * @return
	 * @throws DataAccessException
	 */
	String auditAcademicHonors(HrAcademicHonors hrAcademicHonors)throws DataAccessException;
	/**
	 * 销审
	 * @param hrAcademicHonors
	 * @return
	 * @throws DataAccessException
	 */
	String unAuditAcademicHonors(HrAcademicHonors hrAcademicHonors)throws DataAccessException;
	//删除明细
	String deleteAcademicHonorsDetail(List<HrAcadeHonorDetail> listVo)throws DataAccessException;

}
