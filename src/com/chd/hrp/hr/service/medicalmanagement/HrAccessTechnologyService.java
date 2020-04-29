package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;




/**
 * 技术准入
 * @author Administrator
 *
 */
public interface HrAccessTechnologyService {
    /**
     * 增加技术准入
     * @param mapVo
     * @return
     */
	String addAccessTechnology(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAccessTechnology queryByCodeAccessTechnology(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改技术准入
	 * @param mapVo
	 * @return
	 */
	String updateAccessTechnology(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除技术准入
	 * @param listVo
	 */
	String  deleteAccessTechnology(List<HrAccessTechnology> listVo)throws DataAccessException;
	/**
	 * 查询技术准入
	 * @param page
	 * @return
	 */
	String queryAccessTechnology(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryAccessTechnologyTree(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 提交
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String confirmHrTechRec(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	/**
	 * 撤销
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrHrTechRec(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	/**
	 * 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	HrAccessTechnology queryByCode(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	/**
	 * 添加页查询
	 * @param paramVo
	 * @return
	 * @throws DataAccessException
	 */
	HrAccessTechnology queryByCodeAdd(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 提交
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String confirmHrTechRecAdd(HrAccessTechnology accessTechnology)throws DataAccessException;
	/**
	 * 撤销
	 * @param accessTechnology
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrHrTechRecAdd(HrAccessTechnology accessTechnology)throws DataAccessException;
	String auditHrTechRec(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	String unauditHrHrTechRec(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	String dispassHrHrTechRec(HrAccessTechnology hrNursingPromotion)throws DataAccessException;
	
	
	/**
* 打印
* @param page
* @return
* @throws DataAccessException
*/
List<Map<String,Object>> queryAccessTechnologyByPrint(Map<String, Object> page)throws DataAccessException;
}
