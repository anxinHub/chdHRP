package com.chd.hrp.hr.dao.training.report;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrExamineMode;

public interface HrTrainReportMapper extends SqlMapper{
	 
	/**
	 * 签到汇总表
	 * @param saveMap
	 * @return
	 */
	public	List<Map<String, Object>> queryTrainSignReport(Map<String, Object> saveMap);
	
	/**
	 * 考核评价表
	 * @param saveMap
	 * @return
	 */
	public	List<Map<String, Object>> queryExaminReport(Map<String, Object> saveMap);
	
	/**
	 * 补考评价表
	 * @param saveMap
	 * @return
	 */
	public	List<Map<String, Object>> queryBukaoreport(Map<String, Object> saveMap);
	
	/**
	 * 综合评价表
	 * @param saveMap
	 * @return
	 */
	public	List<Map<String, Object>> queryTrainByPerson(Map<String, Object> saveMap);

	/**
	 * 培训考核汇总表
	 * @param saveMap
	 * @return
	 */
	public	List<Map<String, Object>> queryTrainAllReport(Map<String, Object> saveMap);
    /**
     * 查询职工
     * @param mapVo
     * @return
     */
	public Map<String, Object> queryUser(Map<String, Object> mapVo);
    /**
     * 查询是否生成过综合评价表
     * @param entityMap
     * @return
     */
	public List<HrExamineMode> queryReportEvaluateById(Map<String, Object> entityMap);
    /**
     * 查询已经生过的综合评价
     * @param map
     * @return
     */
	public List<Map<String, Object>> queryReportEvaluateTable(Map<String, Object> map);

		

}
