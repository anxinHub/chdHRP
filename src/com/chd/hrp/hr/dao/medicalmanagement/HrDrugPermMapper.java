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
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrDrugPermMapper extends SqlMapper{
   
    /**
     * 先删除所有数据
     * @param entityMap
     */
	void deletehrDrugPerm(Map<String, Object> entityMap);
     /**
      * 删除权限
      * @param entityList
      */
	void deleteBatchDrugPerm(@Param(value="list") List<HrDrugPerm> entityList,@Param(value="map") Map<String,Object> map);
	
	void addBatch(@Param(value="list") List<HrDrugPerm> entityList,@Param(value="map") Map<String,Object> map);
	
	/**
	 * 增加权限
	 * @param hrDrugPerm
	 * @return
	 */
	int add(HrDrugPerm hrDrugPerm);
	/**
	 * 查询是否提交
	 * @param hrDrugPerm
	 * @return
	 */
	HrDrugPerm queryByCode(HrDrugPerm hrDrugPerm);
	/**
	 * 提交
	 * @param hrDrugPerm
	 */
	void confirmDrugPerm(HrDrugPerm hrDrugPerm);
	/**
	 * 撤回
	 * @param hrDrugPerm
	 */
	void reConfirmDrugPerm(HrDrugPerm hrDrugPerm);
	
	public int selectDrugPermByState(@Param(value="list") List<HrDrugPerm> entityList,@Param(value="map") Map<String,Object> map);
	void addBatchimport(@Param(value="list") List<Map<String, Object>> saveList, @Param(value="map") Map<String, Object> permMap);
	//查询相同时间段内是否存在相同权限
	Map<String, Object> queryEmp(Map<String, Object> saveMap);
	
  
	
}
