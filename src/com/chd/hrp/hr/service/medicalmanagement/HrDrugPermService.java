/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM 药品权限管理
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrDrugPermService {
	/**
	 * 添加权限
	 * @param mapVo
	 * @return
	 */
	String addHrDrugPerm(Map<String, Object> mapVo)throws DataAccessException;
     /**删除权限
      * @param listVo
      * @return
      */
	String deletehrDrugPerm(List<HrDrugPerm> listVo)throws DataAccessException;
	/**
	 * 查询权限
	 * @param page
	 * @return
	 */
	String queryHrDrugPerm(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询是否提交
	 * @param hrDrugPerm
	 * @return
	 * @throws DataAccessException
	 */
	HrDrugPerm queryByCode(HrDrugPerm hrDrugPerm)throws DataAccessException;
	/**
	 * 提交
	 * @param hrDrugPerm
	 * @return
	 * @throws DataAccessException
	 */
	String confirmDrugPerm(List<HrDrugPerm> listVo )throws DataAccessException;
	/**
	 * 取消提交
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmDrugPerm(List<HrDrugPerm> listVo)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importDrugPerm(Map<String, Object> mapVo)throws DataAccessException;

	

}
