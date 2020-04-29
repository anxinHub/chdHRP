package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcadeHonorDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicHonors;
/**
 * 个人学术荣誉申请
 * @author Administrator
 *
 */
public interface HrAcademicHonorsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicHonors> queryAcademicHonorsById(Map<String, Object> entityMap);
    /**
     * 删除个人学术荣誉申请
     * @param entityList
     */
	void deleteAcademicHonors(List<HrAcademicHonors> entityList);
	/**
	 * 查询学术荣誉
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHonor(Map<String, Object> entityMap);
	/**
	 * 添加明细表
	 * @param alllistVo
	 * @return
	 */
	int addHonorDetail(List<HrAcadeHonorDetail> alllistVo);
	/**
	 * 更新明细表
	 * @param alllistVo
	 * @return
	 */
	int updateHonorDetail(List<HrAcadeHonorDetail> alllistVo);
	/**
	 * 查询学术荣誉明细
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrAcadeHonorDetail> queryHrAcadeHonorDetail(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询学术荣誉明细
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrAcadeHonorDetail> queryHrAcadeHonorDetail(Map<String, Object> entityMap);
	/**
	 * 删除明细表
	 * @param entityList
	 */
	void deleteHonorDetail(List<HrAcadeHonorDetail> entityList);
	/**
	 * 删除时候查询明细
	 * @param hrAcademicHonors
	 * @return
	 */
	List<HrAcadeHonorDetail> queryHrDetailByCode(HrAcademicHonors hrAcademicHonors);
	/**
	 * 提交申请
	 * @param hrAcademicHonors
	 */
	void confirmAcademicHonors(HrAcademicHonors hrAcademicHonors);
	/**
	 * 取消提交
	 * @param hrAcademicHonors
	 */
	void reConfirmAcademicHonors(HrAcademicHonors hrAcademicHonors);
	/**
	 * 审核
	 * @param hrAcademicHonors
	 */
	void auditAcademicHonors(HrAcademicHonors hrAcademicHonors);
	/**
	 * 销审
	 * @param hrAcademicHonors
	 */
	void unAuditAcademicHonors(HrAcademicHonors hrAcademicHonors);
	

}
