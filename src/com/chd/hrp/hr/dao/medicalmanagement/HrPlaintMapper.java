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
import com.chd.hrp.hr.entity.medicalmanagement.HrPlaint;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ADVERSE EVENT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrPlaintMapper extends SqlMapper{
	/**
	 * 添加数据
	 * @param alllistVo
	 * @param entityMap 
	 * @return
	 */
	int addBatchPlaint(@Param(value="list") List<HrPlaint> entityList, @Param(value="map")Map<String, Object> mapVo);
     /**
      * 删除所有数据
      * @param entityMap
      */
	void deletehrPlaint(Map<String, Object> entityMap);
     /**
      * 删除不良事件
      * @param entityList
     * @param mapVo 
      */
	void deletePlaint(@Param(value="list") List<HrPlaint> entityList, @Param(value="map")Map<String, Object> mapVo);
	/**
	 * 修改提交状态(批量)
	 * @param hrPlanit
	 */
	void updateIsCommit(HrPlaint hrPlanit);
	/**
	 * 修改提交状态(批量)
	 * @param List<HrPlaint>
	 */
	void updateIsCommitBatch(@Param(value = "list") List<HrPlaint> list);
	/**
	 * 查询数据
	 * @param entityMap
	 * @return
	 */
	HrPlaint queryByCodePlaint(HrPlaint entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPlaintByPrint(Map<String, Object> entityMap);
	/**
	 * 通过提交状态查记录数
	 * @param map
	 * @param listVo
	 * @return
	 */
	int queryByIsCommit(@Param(value = "map") Map<String, Object> map, @Param(value = "list") List<HrPlaint> list);
}
