/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
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
 

public interface HrClinicDocWorkMapper extends SqlMapper{
     /**
      * 删除所有
      * @param entityMap
      */
	void deletehrClinicDocWork(Map<String, Object> entityMap);
    /**
     * 批量删除
     * @param entityList
     * @param mapVo 
     */
	void deleteClinicalWorkload(@Param(value="list") List<HrClinicDocWork> entityList,@Param(value="map") Map<String, Object> mapVo);
	HrClinicDocWork queryByCodeClinicDocWork(HrClinicDocWork hrClinicDocWork);
	int add(HrClinicDocWork hrClinicDocWork);
	void addBatchClinicalWorkload(@Param(value="list") List<HrClinicDocWork> alllistVo,@Param(value="map") Map<String, Object> entityMap);
	
}
