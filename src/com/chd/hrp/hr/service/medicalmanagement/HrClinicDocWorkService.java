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

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.medicalmanagement.HrClinicDocWork;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_CLINIC_DOC_WORK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrClinicDocWorkService {
    /**
     * 添加工作量
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addClinicalWorkload(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除工作量
     * @param listVo
     * @return
     */
	String deleteClinicalWorkload(List<HrClinicDocWork> listVo)throws DataAccessException;
	/**
	 * 查询工作量
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryClinicalWorkload(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importClinicalDocWork(Map<String, Object> mapVo)throws DataAccessException;

}
