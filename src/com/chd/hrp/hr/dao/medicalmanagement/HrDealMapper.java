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
import com.chd.hrp.hr.entity.medicalmanagement.HrDeal;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DEAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrDealMapper extends SqlMapper{

	void deleteDeal(@Param(value="list") List<HrDeal> entityList,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 修改提交状态（单条）
	 * @param deal
	 */
	void updateIsCommit(HrDeal deal);
   /**
    * 修改提交状态（单条）
    * @param deal
    */
	void confirmDeal(HrDeal deal);
	/**
     * 修改提交状态(批量)
     * @param List<HrDeal>
     */
	void confirmDealBatch(@Param(value="list") List<HrDeal> list);
	/**
	 * 撤回
	 * @param deal
	 */
    void reConfirmDeal(HrDeal deal);
    /**
     * 提交查询
     * @param hrdeal
     * @return
     */
	HrDeal queryByCodeCon(HrDeal hrdeal);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDealByPrint(Map<String, Object> entityMap);
	/**
	 * 查询投诉纠纷调查住院号
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryResearchInHosNo(Map<String, Object> entityMap);
	/**
	 * 通过提交状态查记录数
	 * @param map
	 * @param list
	 * @return
	 */
	int queryByIsCommit(@Param(value = "map") Map<String, Object> map, @Param(value = "list") List<HrDeal> list);
	
}
