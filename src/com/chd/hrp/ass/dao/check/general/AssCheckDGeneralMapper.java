/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.general;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.general.AssCheckDGeneral;
/**
 * 
 * @Description:
 * 051101 科室盘点单(专用设备)
 * @Table:
 * ASS_CHECK_D_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssCheckDGeneralMapper extends SqlMapper{

	AssCheckDGeneral queryState(Map<String, Object> mapVo);
	
}
