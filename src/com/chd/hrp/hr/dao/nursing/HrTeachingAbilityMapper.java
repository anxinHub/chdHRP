package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrTeachingAbility;
/**
 * 教学能力
 * @author Administrator
 *
 */
public interface HrTeachingAbilityMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrTeachingAbility> queryTeachingAbilityById(Map<String, Object> entityMap);
    /**
     * 删除教学能力
     * @param entityList
     */
	void deleteTeachingAbility(List<HrTeachingAbility> entityList);
	/**
	 * 查询教学种类下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryTeachType(Map<String, Object> entityMap);
	/**
	 * 查询下拉表格教学种类
	 * @param entityMap
	 * @return
	 */
	List<HrTeachingAbility> queryHrTeachType(Map<String, Object> entityMap);
	/**
	 * 查询下拉表格教学种类带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrTeachingAbility> queryHrTeachType(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 删除全部教学能力
	 * @param entityMap
	 */
	void deleteTeaching(Map<String, Object> entityMap);
	
	List<Map<String, Object>> queryHrTeachingAbilityByEmpId(List<HrTeachingAbility> entityList);
	/**
	 * 查询教学种类
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryTeach(Map<String, Object> saveMap);
	
}
