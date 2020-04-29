package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrEmpAcadeStatusDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrStatusHonors;


/**
 * 个人学术地位申请
 * @author Administrator
 *
 */
public interface HrStatusHonorsService {
    /**
     * 增加个人学术地位申请
     * @param mapVo
     * @return
     */
	String addStatusHonors(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrStatusHonors queryByCodeStatusHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改个人学术地位申请
	 * @param mapVo
	 * @return
	 */
	String updateStatusHonors(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除个人学术地位申请
	 * @param listVo
	 */
	String  deleteStatusHonors(List<HrStatusHonors> listVo)throws DataAccessException;
	/**
	 * 查询个人学术地位申请
	 * @param page
	 * @return
	 */
	String queryStatusHonors(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询学术地位明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrEmpAcadeStatusDetail(Map<String, Object> page)throws DataAccessException;
	/**
	 * 删除时查询明细
	 * @param hrStatusHonors
	 * @return
	 */
	List<HrEmpAcadeStatusDetail> queryHrDetailByCode(HrStatusHonors hrStatusHonors)throws DataAccessException;
	/**
	 * 查询学术地位
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryStatus(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 *提交
	 * @param hrStatusHonors
	 * @return
	 * @throws DataAccessException
	 */
	String confirmStatusHonors(HrStatusHonors hrStatusHonors)throws DataAccessException;
	/**
	 * 取消提交
	 * @param hrStatusHonors
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmStatusHonors(HrStatusHonors hrStatusHonors)throws DataAccessException;
	/**
	 * 审核
	 * @param hrStatusHonors
	 * @return
	 * @throws DataAccessException
	 */
	String auditStatusHonors(HrStatusHonors hrStatusHonors)throws DataAccessException;
	/**
	 * 销审
	 * @param hrStatusHonors
	 * @return
	 * @throws DataAccessException
	 */
	String unAuditStatusHonors(HrStatusHonors hrStatusHonors)throws DataAccessException;
	/**
	 * 删除明细
	 * @param entityList
	 * @return
	 */
	String deleteAcademicHonorsDetail(List<HrEmpAcadeStatusDetail> entityList) throws DataAccessException;


}
