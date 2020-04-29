package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrCertificate;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;




/**
 * 证件管理
 * @author Administrator
 *
 */
public interface HrCertificateService {
    /**
     * 增加证件管理
     * @param mapVo
     * @return
     */
	String addCertificate(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrCertificate queryByCodeCertificate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改证件管理
	 * @param mapVo
	 * @return
	 */
	String updateCertificate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除证件管理
	 * @param listVo
	 */
	String  deleteCertificate(List<HrCertificate> listVo)throws DataAccessException;
	/**
	 * 查询证件管理
	 * @param page
	 * @return
	 */
	String queryCertificate(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 查询是否存在
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	HrCertificate queryByCode(HrCertificate hrNursingPromotion)throws DataAccessException;
	/**
	 * 添加页查询
	 * @param paramVo
	 * @return
	 * @throws DataAccessException
	 */
	HrCertificate queryByCodeAdd(Map<String, Object> mapVo)throws DataAccessException;
	
	String auditHrTechRec(HrCertificate hrNursingPromotion)throws DataAccessException;
	String unauditHrHrTechRec(HrCertificate hrNursingPromotion)throws DataAccessException;
	String dispassHrHrTechRec(HrCertificate hrNursingPromotion)throws DataAccessException;
	
	String importCertificate(Map<String, Object> mapVo)throws DataAccessException;
	/**
* 打印
* @param page
* @return
* @throws DataAccessException
*/
List<Map<String,Object>> queryCertificateByPrint(Map<String, Object> page)throws DataAccessException;
}
