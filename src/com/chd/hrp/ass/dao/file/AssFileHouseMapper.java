﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.file;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产文档_房屋及建筑物
 * @Table:
 * ASS_FILE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssFileHouseMapper extends SqlMapper{

	List<Map<String, Object>> queryByAssCardNo(Map<String, Object> map);
	
}
