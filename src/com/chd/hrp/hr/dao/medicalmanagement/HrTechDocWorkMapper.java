/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrTechDocWork;
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
 

public interface HrTechDocWorkMapper extends SqlMapper{
     /**
      * 删除所有
      * @param entityMap
      */
	void deletehrTechDocWork(Map<String, Object> entityMap);
    /**
     * 批量删除
     * @param entityList
     */
	void deleteTechDocWorkload(List<HrTechDocWork> entityList);
	/**
	 * 查询人员是否存在
	 * @param empMap
	 * @return
	 */
	Map<String, Object> queryEmpByCode(Map<String, Object> empMap);
	/**
	 * 查询部门是否存在
	 * @param deptMap
	 * @return
	 */
	Map<String, Object> queryDeptDictByCodeOrName(Map<String, Object> deptMap);
	HrTechDocWork queryByCodeTechDocWork(HrTechDocWork hrTechDocWork);
	int add(HrTechDocWork hrTechDocWork);
	
}
