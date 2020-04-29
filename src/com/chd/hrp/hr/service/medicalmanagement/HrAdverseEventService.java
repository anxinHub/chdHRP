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
import com.chd.hrp.hr.entity.medicalmanagement.HrAdverseEvent;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ADVERSE EVENT 不良事件
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrAdverseEventService {
    /**
     * 添加不良事件登记
     * @param mapVo
     * @return
     */
	String addAdverseEvent(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除不良事件
     * @param listVo
     * @return
     */
	String deleteAdverseEvent(List<HrAdverseEvent> listVo)throws DataAccessException;
	/**
	 * 查询不良事件
	 * @param page
	 * @return
	 */
	String queryAdverseEvent(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询不良事件
	 * @param mapVo
	 * @return
	 */
	String importAdverseEvent(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAdverseEventByPrint(Map<String, Object> page)throws DataAccessException;
}
