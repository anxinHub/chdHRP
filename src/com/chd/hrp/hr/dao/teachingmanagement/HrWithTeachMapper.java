package com.chd.hrp.hr.dao.teachingmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrWithTeach;
/**
 * 住院医带教补贴
 * @author Administrator
 *
 */
public interface HrWithTeachMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrWithTeach> queryHrWithTeachById(Map<String, Object> entityMap);
    /**
     * 删除住院医带教补贴
     * @param entityList
     */
	void deleteHrWithTeach(List<HrWithTeach> entityList);
	/**
	 * 查询轮转科室信息  下拉框
	 * @return
	 */
	List<Map<String,Object>> queryDeptComboBox(Map<String,Object> entityMap);
	/**
	 * 查询  双击工号出现下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryComboBox(Map<String,Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryWithTeachByPrint(Map<String, Object> entityMap);
	

}
