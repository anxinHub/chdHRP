/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.dict;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssLandSourceDict;
import com.chd.hrp.ass.entity.dict.AssStructDict;
/**
 * 
 * @Description:
 * 资产建筑结构
 * @Table:
 * ASS_STRUCT_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssStructDictMapper extends SqlMapper{

	AssStructDict queryAssStructDictExists(Map<String, Object> map_code);

	AssStructDict queryByName(Map<String, Object> entityMap);
	
}
