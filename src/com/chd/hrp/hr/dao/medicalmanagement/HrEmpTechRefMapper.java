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
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechRef;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_REF
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpTechRefMapper extends SqlMapper{
	/**
	 * 添加技术准入相关联手术
	 * @param alllistVo
	 * @param entityMap 
	 * @return
	 */
	int addTechRef(@Param(value="list") List<HrEmpTechRef> alllistVo, @Param(value="map") Map<String, Object> entityMap);
	/**
	 * 删除技术准入相关联手术
	 * @param entityList
	 * @param mapVo 
	 */
	void deleteHrTechRef(@Param(value="list") List<?> entityList ,@Param(value="map") Map<String, Object> mapVo);
	int updateBatchRef(@Param(value="list") List<HrEmpTechRef> alllistVo,@Param(value="map") Map<String, Object> mapVo);
	HrEmpTechRef queryByCodeRef(HrEmpTechRef hrEmpTechRef);
	
}
