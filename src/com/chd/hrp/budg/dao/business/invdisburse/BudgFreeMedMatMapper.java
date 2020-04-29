/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.invdisburse;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_FREE_MED_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFreeMedMatMapper extends SqlMapper{


	public List<Map<String, Object>> queryBudgFreeMedMatCostByYear(Map<String, Object> mapVo);

	public int queryBudgMatTypeSubjByYear(Map<String, Object> mapVo);

	public int generatebudgFreeMedMat(Map<String, Object> mapVo);

	public int updatebudgFreeMedMat(Map<String, Object> mapVo);

	public void budgChargeMatUpdateAdjRate(List<Map<String, Object>> listVo);

	public List<Map<String, Object>> querydataList(Map<String, Object> mapVo);

	public int queryDataExists(Map<String, Object> map);
	
}
