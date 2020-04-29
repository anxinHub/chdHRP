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
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPermDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrDrugPermDetailMapper extends SqlMapper{
    /**
     * 查询人员信息
     * @param mapVo
     * @return
     */
	Map<String, Object> queryEmp(Map<String, Object> mapVo);
    /**
     * 删除所有
     * @param alllistVo
     * @param entityMap 
     */
	void deletehrDrugPermDetail(@Param(value="list")List<?> entityList,@Param(value="map")Map<String, Object> map);
	/**
	 * 删除
	 * @param entityList
	 */
	void deleteBatchDrugPermDetail(@Param(value="list")List<?> entityList,@Param(value="map")Map<String, Object> map);
	/**
	 * 提交
	 * @param map
	 */
	void confirmDrugPermDetail(Map<String, Object> map);
	/**
	 * 撤回
	 * @param map
	 */
	void reConfirmDrugPermDetail(Map<String, Object> map);
	
}
