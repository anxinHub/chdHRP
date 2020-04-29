package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrIntensiveCare;
/**
 * 重症护理能力
 * @author Administrator
 *
 */
public interface HrIntensiveCareMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrIntensiveCare> queryIntensiveCareById(Map<String, Object> entityMap);
    /**
     * 删除重症护理能力
     * @param entityList
     */
	void deleteIntensiveCare(List<HrIntensiveCare> entityList);
	/**
	 * 删除所有重症护理能力
	 * @param entityMap
	 */
	void deletehrAcademicAbility(Map<String, Object> entityMap);
	/**
	 * 查询部门
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryDept(Map<String, Object> saveMap);
	

}
