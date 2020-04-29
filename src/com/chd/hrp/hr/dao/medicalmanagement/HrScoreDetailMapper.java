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
import com.chd.hrp.hr.entity.medicalmanagement.HrScoreDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_SCORE_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrScoreDetailMapper extends SqlMapper{
    /**
     * 查询下拉表格
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryHrResearch(Map<String, Object> entityMap);
    /**
     * 删除所有
     * @param entityMap
     */
	void deletehrScoreDetail(Map<String, Object> entityMap);
	/**
	 * 添加
	 * @param alllistVo
	 * @param entityMap 
	 * @return
	 */
	int addBatchScoreDetail(@Param(value="list") List<HrScoreDetail> alllistVo,@Param(value="map") Map<String, Object> entityMap);
	/**
	 * 删除选择的
	 * @param entityList
	 * @param mapVo 
	 */
	void deleteScoreDetail(@Param(value="list") List<HrScoreDetail> alllistVo,@Param(value="map") Map<String, Object> entityMap);
	/**
	 * 提交查询
	 * @param hrScoreDetail
	 * @return
	 */
	HrScoreDetail queryByCodeScoreDetail(HrScoreDetail hrScoreDetail);
	/**
	 * 提交
	 * @param hrScoreDetail
	 */
	void confirmScoreDetail(HrScoreDetail hrScoreDetail);
	/**
	 * 撤回
	 * @param hrScoreDetail
	 */
	void reConfirmScoreDetail(HrScoreDetail hrScoreDetail);
	int add(HrScoreDetail hrScoreDetail);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryScoreDetailByPrint(Map<String, Object> entityMap);
	
	
	int confirmScoreDetailBatch(@Param(value="map") Map<String, Object> entityMap,@Param(value="list") List<HrScoreDetail> list);
	
	/**
	 * 查询投诉记录已经提交了的记录数
	 * @param entityMap
	 * @param list
	 * @return
	 */
	int queryScoreDetailIsComit(@Param(value="map") Map<String, Object> entityMap, 
			@Param(value="list") List<HrScoreDetail> list);
	
}
