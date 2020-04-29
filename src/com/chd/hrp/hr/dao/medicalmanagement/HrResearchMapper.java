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
import com.chd.hrp.hr.entity.medicalmanagement.HrResearch;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_RESEARCH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrResearchMapper extends SqlMapper{
    /**
     * 删除
     * @param entityList
     * @param mapVo 
     */
	void deleteResearch(@Param(value="list") List<HrResearch> entityList,@Param(value="map") Map<String, Object> mapVo);
    /**
     * 提交查询
     * @param hrNursingPromotion
     * @return
     */
	HrResearch queryByCode(HrResearch hrNursingPromotion);
	/**
	 * 审核
	 * @param hrNursingPromotion
	 */
	void auditHrTechRec(HrResearch hrNursingPromotion);
	/**
	 * 销审
	 * @param hrNursingPromotion
	 */
	void unauditHrHrTechRec(HrResearch hrNursingPromotion);
	/**
	 * 添加页查询
	 * @param entityMap
	 * @return
	 */
	HrResearch queryByCodeAdd(Map<String, Object> entityMap);
	/**
	 * 住院号下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryinHosNo(Map<String, Object> entityMap);
	/**
	 * 查询投诉明显
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryinHosNoDetail(Map<String, Object> entityMap);
	/**
	 * 投诉纠纷处理查询
	 * @param entityMap
	 * @return
	 */
	Object queryByCodeResearchD(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryResearchByPrint(Map<String, Object> entityMap);
	HrResearch queryByCodeResearch(Map<String, Object> entityMap);
	/**
	 * 通过提交状态查记录数
	 * @param map
	 * @param list
	 * @return
	 */
	int queryByIsCommit(@Param(value="map") Map<String, Object> map, @Param(value="list") List<HrResearch> list);
	/**
	 * 修改提交状态（单条）
	 * @param hrResearch
	 */
	void updateIsCommit(HrResearch hrResearch);
	/**
	 * 修改提交状态（批量）
	 * @param List<HrResearch>
	 */
	void updateIsCommitBatch(List<HrResearch> list);
	
}
