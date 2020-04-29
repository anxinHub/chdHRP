/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.information;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.HosProj;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * HOS_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HosProjMapper extends SqlMapper{

	Map<String, Object> queryMaxProjId(Map<String, Object> entityMap);


	HosProj queryhosProjByCode(Map<String, Object> entityMap);


	int synchronizationAdd(Map<String, Object> entityMap);


	int cancelauditBudgProjSetUp(Map<String, Object> map);


	int endHosProj(Map<String, Object> map);


	HosProj queryProjByCodeNotThis(Map<String, Object> entityMap);


	
}
