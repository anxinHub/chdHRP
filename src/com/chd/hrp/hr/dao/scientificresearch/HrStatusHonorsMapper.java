package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrEmpAcadeStatusDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrStatusHonors;
/**
 * 个人学术地位申请
 * @author Administrator
 *
 */
public interface HrStatusHonorsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrStatusHonors> queryStatusHonorsById(Map<String, Object> entityMap);
    /**
     * 删除个人学术地位申请
     * @param entityList
     */
	void deleteStatusHonors(List<HrStatusHonors> entityList);
	/**
	 * 添加学术明细
	 * @param alllistVo
	 * @return
	 */
	int addStatusDetail(List<HrEmpAcadeStatusDetail> alllistVo);
	/**
	 * 更新学术明细
	 * @param alllistVo
	 * @return
	 */
	int updateStatusDetail(List<HrEmpAcadeStatusDetail> alllistVo);
	/**
	 * 删除学术地位明细
	 * @param entityList
	 */
	void deleteStatusDetail(List<HrEmpAcadeStatusDetail> entityList);
	/**
	 * 查询学术地位下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStatus(Map<String, Object> entityMap);
	/**
	 * 查询学术地位明细
	 * @param entityMap
	 * @return
	 */
	List<HrEmpAcadeStatusDetail> queryHrEmpAcadeStatusDetail(Map<String, Object> entityMap);
	/**
	 * 查询学术地位明细带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrEmpAcadeStatusDetail> queryHrEmpAcadeStatusDetail(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询明细
	 * @param hrStatusHonors
	 * @return
	 */
	List<HrEmpAcadeStatusDetail> queryHrDetailByCode(HrStatusHonors hrStatusHonors);
	/**
	 * 提交
	 * @param hrStatusHonors
	 */
	void confirmStatusHonors(HrStatusHonors hrStatusHonors);
	/**
	 * 取消提交
	 * @param hrStatusHonors
	 */
	void reConfirmStatusHonors(HrStatusHonors hrStatusHonors);
	/**
	 * 审核
	 * @param hrStatusHonors
	 */
	void auditStatusHonors(HrStatusHonors hrStatusHonors);
	/**
	 * 销审
	 * @param hrStatusHonors
	 */
	void unAuditStatusHonors(HrStatusHonors hrStatusHonors);
	

}
