/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.house;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.house.AssCheckHouse;
import com.chd.hrp.ass.entity.check.house.AssCheckPlanHouse;
/**
 * 
 * @Description:
 * 051101 盘点单(土地)
 * @Table:
 * ASS_CHECK_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckHouseMapper extends SqlMapper{

	AssCheckHouse queryState(Map<String, Object> mapVo);
	
}
