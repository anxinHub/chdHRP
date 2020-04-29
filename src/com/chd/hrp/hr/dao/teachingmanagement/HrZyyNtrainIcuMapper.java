package com.chd.hrp.hr.dao.teachingmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainIcu;
/**
 * 住院医规培轮转成绩表（急诊ICU）
 * @author Administrator
 *
 */
public interface HrZyyNtrainIcuMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrZyyNtrainIcu> queryHrZyyNtrainIcuById(Map<String, Object> entityMap);
    /**
     * 删除住院医规培轮转成绩表（急诊ICU）
     * @param entityList
     */
	void deleteHrZyyNtrainIcu(List<HrZyyNtrainIcu> entityList);
	/**
	 *  查询专业信息 下拉框
	 * @return
	 */
	List<Map<String,Object>> queryProfessionalComboBox(Map<String,Object> entityMap);
	/**
	 * 查询学历信息 下拉框
	 * @return
	 */
	List<Map<String,Object>> queryEducationComboBox(Map<String,Object> entityMap);
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
	/***
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryZyyNtrainIcuByPrint(Map<String, Object> entityMap);

}
