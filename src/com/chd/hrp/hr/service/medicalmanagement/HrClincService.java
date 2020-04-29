package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrClinc;

public interface HrClincService {
    /**
     * 添加门诊能力
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addClinc(Map<String, Object> mapVo)throws DataAccessException;
     /**
      * 删除门诊能力
      * @param listVo
      * @return
      * @throws DataAccessException
      */
	String deleteClinc(List<HrClinc> listVo)throws DataAccessException;
	/**
	 * 查询门诊能力
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryClinc(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入门诊能力
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importClinc(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询是否重复
	 * @param hrClinc
	 * @return
	 * @throws DataAccessException
	 */
	HrClinc queryByCode(HrClinc hrClinc)throws DataAccessException;
	/**
	 * 提交门诊能力
	 * @param hrClinc
	 * @return
	 * @throws DataAccessException
	 */
	String confirmClinc(HrClinc hrClinc)throws DataAccessException;
	/**
	 * 撤回门诊能力
	 * @param hrClinc
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmClinc(HrClinc hrClinc)throws DataAccessException;
	  /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryClincByPrint(Map<String, Object> page)throws DataAccessException;
}
