package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attendresult.HrAttendResult;
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.entity.nursing.HrInserviceEducation;
/**
 * 年度在职教育
 * @author Administrator
 *
 */
public interface HrInserviceEducationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrInserviceEducation> queryInserviceEducationById(Map<String, Object> entityMap);
    /**
     * 删除年度在职教育
     * @param entityList
     */
	void deleteInserviceEducation(List<HrInserviceEducation> entityList);
	/**
	 * 审核年度在职教育
	 * @param entityList
	 * @return
	 */
	String auditInserviceEducation(List<Map<String, Object>> entityList);
	/**
	 * 取消年度在职教育
	 * @param entityList
	 * @return
	 */
	String reAuditInserviceEducation(List<Map<String, Object>> entityList);
	/**
	 * 提交年度在职教育
	 * @param hrInserviceEducation
	 * @return
	 */
	void confirmInserviceEducation(HrInserviceEducation hrInserviceEducation);
	/**
	 * 取消提交晋级申请
	 * @param hrEducationStudent
	 * @return
	 */
	void reConfirmInserviceEducation(HrEducationStudent hrEducationStudent);
	/**
	 * 增加学员
	 * @param entityMap
	 * @return
	 */
	 int addEducationStudent(List<HrEducationStudent> entityMap);
	 /**
	  * 查询学员不带分页
	  * @param entityMap
	  * @return
	  */
	List<HrEducationStudent> queryEducationStudent(Map<String, Object> entityMap);
	/**
	 * 查询学员带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrEducationStudent> queryEducationStudent(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 删除学员
	 * @param entityMap
	 */
	void deleteEducationStudent(Map<String, Object> entityMap);
	/**
	 * 批量删除学员
	 * @param entityList
	 */
	void deleteEducationStudentBatch(List<HrInserviceEducation> entityList);
	/**
	 * 查询年度在职教育培训
	 * @param entityMap
	 * @return
	 */
	List<HrInserviceEducation> queryInserviceEducation(Map<String, Object> entityMap);
	/**
	 * 查询年度在职教育培训带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrInserviceEducation> queryInserviceEducation(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 增加学员到审批表
	 * @param hrEducation
	 */
	void addConfirmInserviceEducation(HrEducationStudent hrEducation);
	/**
	 * 撤回学员审批表
	 * @param hrEducationStudent
	 */
	void reConfirmInserviceEducationEvaluate(HrEducationStudent hrEducationStudent);
	/**
	 * 查询晋级表中是否存在
	 * @param hrEducationStudent
	 * @return
	 */
	HrEducationStudent queryEducation(HrEducationStudent hrEducationStudent);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryInserviceEducationByPrint(Map<String, Object> entityMap);
	/**
	 * 批量确认
	 * @param entityList
	 * @param entityMap
	 */
	void confirmBatchInserviceEducation(@Param(value="list") List<HrInserviceEducation> entityList,@Param(value="map")  Map<String, Object> entityMap);
	/**
	 * 批量撤回
	 * @param entityList
	 * @param entityMap
	 */
	void reConfirmBatchInserviceEducation(@Param(value="list") List<HrInserviceEducation> entityList,@Param(value="map")  Map<String, Object> entityMap);
	/**
	 * 删除修改页面学员
	 * @param listVo
	 */
	void deleteEducationStudentBatc(@Param(value="list") List<HrEducationStudent> listVo,@Param(value="map")  Map<String, Object> entityMap);
	Map<String, Object> queryEmp(Map<String, Object> mapVo);
	
}
