package com.chd.hrp.hr.dao.teachingmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.teachingmanagement.HrZyyNtrainTec;
/**
 * 住院医规培轮转成绩表（医技）
 * @author Administrator
 *
 */
public interface HrZyyNtrainTecMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrZyyNtrainTec> queryHrZyyNtrainTecById(Map<String, Object> entityMap);
    /**
     * 删除住院医规培轮转成绩表（医技）
     * @param entityList
     */
	void deleteHrZyyNtrainTec(List<HrZyyNtrainTec> entityList);
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
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryZyyNtrainTecByPrint(Map<String, Object> entityMap);

}
