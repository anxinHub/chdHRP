package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.entity.nursing.HrInserviceEducation;

/**
 * 年度在职教育培训
 * @author Administrator
 *
 */
public interface HrInserviceEducationService {
	/**
	 * 增加年度在职教育
	 * @param mapVo
	 * @return
	 */

	String addInserviceEducation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询年度在职教育
     * @param mapVo
     * @return
     */
	HrInserviceEducation queryByCodeInserviceEducation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改年度在职教育
	 * @param mapVo
	 * @return
	 */
	String updateInserviceEducation(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除年度在职教育
	 * @param listVo
	 */
	String deleteInserviceEducation(List<HrInserviceEducation> listVo)throws DataAccessException;
	/**
	 * 查询年度在职教育
	 * @param page
	 * @return
	 */
	String queryInserviceEducation(Map<String, Object> page)throws DataAccessException;
	/**
	 * 审核年度在职教育
	 * @param listVo
	 * @return
	 */
	String auditInserviceEducation(List<Map<String, Object>> listVo)throws DataAccessException;
	/**
	 * 销审年度在职教育
	 * @param listVo
	 * @return
	 */
	String reAuditInserviceEducation(List<Map<String, Object>> listVo)throws DataAccessException;
	/**
	 * 提交年度在职教育
	 * @param listVo
	 * @return
	 */
	String confirmInserviceEducation(HrInserviceEducation hrInserviceEducation)throws DataAccessException;
	/**
	 * 取消提交年度在职教育
	 * @param listVo
	 * @return
	 */
	String reConfirmInserviceEducation(HrInserviceEducation hrInserviceEducation)throws DataAccessException;
	
	 String addEducationStudent(List<HrEducationStudent> entityMap) throws DataAccessException ;
	 /**
	  * 查询学员
	  * @param page
	  * @return
	  * @throws DataAccessException
	  */
	String queryEducationStudent(Map<String, Object> page) throws DataAccessException ;
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryInserviceEducationByPrint(Map<String, Object> entityMap) throws DataAccessException ;
	/**
	 * 提交
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String confirmBatchInserviceEducation(List<HrInserviceEducation> entityList)  throws DataAccessException ;
	/**
	 * 批量撤回
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmBatchInserviceEducation(List<HrInserviceEducation> entityList)  throws DataAccessException ;
	/**
	 * 修改页面删除学员
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteEducationStudentBatc(List<HrEducationStudent> listVo)  throws DataAccessException ;
	/**
	 * 查询人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryEmp(Map<String, Object> mapVo) throws DataAccessException ;
	
	
}
