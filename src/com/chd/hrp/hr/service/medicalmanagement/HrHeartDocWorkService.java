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

import com.chd.hrp.hr.entity.medicalmanagement.HrHeartDocWork;
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
 

public interface HrHeartDocWorkService {
    /**
     * 添加工作量
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addHeartDocWorkload(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除工作量
     * @param listVo
     * @return
     */
	String deleteHeartDocWorkload(List<HrHeartDocWork> listVo)throws DataAccessException;
	/**
	 * 查询工作量
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryHeartDocWorkload(Map<String, Object> page)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importHeartDocWork(Map<String, Object> mapVo)throws DataAccessException;

}
