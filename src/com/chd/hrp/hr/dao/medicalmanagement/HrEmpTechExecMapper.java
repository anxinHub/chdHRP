/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrAccessTechnology;
import com.chd.hrp.hr.entity.medicalmanagement.HrEmpTechExec;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_TECH_EXEC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpTechExecMapper extends SqlMapper{
	/**
	 * 添加技术准入开展情况
	 * @param alllistVo
	 * @param entityMap 
	 * @return
	 */
	int addTechExec(@Param(value="list") List<HrEmpTechExec> alllistVo,@Param(value="map") Map<String, Object> entityMap);
	
	HrAccessTechnology queryByCodeExec(Map<String, Object> entityMap);
	/**
	 * 删除技术准入开展情况
	 * @param entityList
	 * @param mapVo 
	 */
	void deleteHrTechExec(@Param(value="list") List<?> entityList ,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 查询是否存在
	 * @param entityMap
	 * @return
	 */
	List<HrEmpTechExec> queryHrEmpTechExec(Map<String, Object> entityMap);

	void deleteHrTechExe(@Param(value="list") List<?> entityList ,@Param(value="map") Map<String, Object> mapVo);

	HrEmpTechExec queryHrEmpTechEx(HrEmpTechExec hrEmpTechExec);

	void updateBatchTechExec(@Param(value="list") List<HrEmpTechExec> alllistVo, @Param(value="map") Map<String, Object> entityMap);
	
	
}
