package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
/**
 * 年度在职教育学时统计
 * @author Administrator
 *
 */
public interface HrInserviceStatisticsMapper  extends SqlMapper{
  
	/**
	 * 审核年度在职教育
	 * @param entityList
	 * @return
	 */
	String auditInserviceStatistics(List<Map<String, Object>> entityList);
	/**
	 * 取消年度在职教育
	 * @param entityList
	 * @return
	 */
	String reAuditInserviceStatistics(List<Map<String, Object>> entityList);
	/**
	 * 提交年度在职教育
	 * @param entityList
	 * @return
	 */
	String confirmInserviceStatistics(List<Map<String, Object>> entityList);
	/**
	 * 取消提交
	 * @param entityList
	 * @return
	 */
	String reConfirmInserviceStatistics(List<Map<String, Object>> entityList);
	
	List<HrEducationStudent> queryStudent(Map<String, Object> entityMap);
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryByPrint(Map<String, Object> entityMap);
	
}
