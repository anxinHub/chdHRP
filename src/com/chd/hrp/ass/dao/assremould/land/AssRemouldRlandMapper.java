﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.land;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产改造记录(土地)
 * @Table:
 * ASS_REMOULD_R_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldRlandMapper extends SqlMapper{

	void updateAssRemouldRlandConfirmState(List<Map<String, Object>> listVo)throws DataAccessException;

	void updateConfirmAssRemouldRLand(List<Map<String, Object>> listVo);

	void collectAssRemoildRland(Map<String, Object> entityMap);

	void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);
	
}