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
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPermDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM_DETAIL 药品权限管理明细
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrDrugPermDetailService {
    /**
     * 查询人员
     * @param mapVo
     * @return
     */
	Map<String, Object> queryEmp(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 添加数据 
     * @param mapVo
     * @return
     */
	String addDrugPermDetail(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除数据
	 * @param listVo
	 * @return
	 */
	String deleteBatch(List<HrDrugPermDetail> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 */
	String queryHrDrugPermDetail(Map<String, Object> page)throws DataAccessException;

}
