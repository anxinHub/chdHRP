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
 

public interface HrHeartDocWorkMapper extends SqlMapper{
     /**
      * 删除所有
      * @param entityMap
      */
	void deletehrHeartDocWork(Map<String, Object> entityMap);
    /**
     * 批量删除
     * @param entityList
     * @param mapVo 
     */
	void deleteHeartDocWorkload(@Param(value="list") List<HrHeartDocWork> entityList,@Param(value="map") Map<String, Object> mapVo);
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
	HrHeartDocWork queryByCodeHeartDocWork(HrHeartDocWork hrHeartDocWork);
	int add(HrHeartDocWork hrHeartDocWork);
	HrHeartDocWork queryByCodeHeartDocWork(Map<String, Object> saveMap);
	void addBatchHeartDocWorkload(@Param(value="list") List<HrHeartDocWork> alllistVo,@Param(value="map") Map<String, Object> entityMap);
	
}
